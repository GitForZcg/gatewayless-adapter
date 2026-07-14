package com.personal.demo.serivce.impl;

import com.personal.demo.conf.BaseUrlConfig;
import com.personal.demo.conf.GsonFactory;
import com.personal.demo.consts.Separator;
import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.pojo.base.AccessKeyConfig;
import com.personal.demo.pojo.base.BasePayPublicParams;
import com.personal.demo.pojo.base.BasePayRequest;
import com.personal.demo.serivce.AbstractPayMD5Service;
import com.personal.demo.serivce.AppSecretService;
import com.personal.demo.util.TypeHelperUtil;
import com.personal.demo.utils.HttpUtil;
import com.personal.demo.utils.SignatureUtil;
import com.common.base.exception.BizException;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Objects;

import static com.personal.demo.consts.AdapterConstant.DB_SUFFIX;
import static com.personal.demo.consts.AdapterConstant.TIMEZONE;
import static com.personal.demo.consts.PayConstant.*;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/11/6 10:39
 */

@Service
@Slf4j
public class AbstractPayMD5ServiceImpl implements AbstractPayMD5Service {

    private final BaseUrlConfig config;

    private final AppSecretService appSecretService;

    private final Gson gson = GsonFactory.getInstance();

    public AbstractPayMD5ServiceImpl(BaseUrlConfig config, AppSecretService appSecretService) {
        this.config = config;
        this.appSecretService = appSecretService;
    }

    @Override
    public <T extends BasePayPublicParams> BasePayRequest executeSign(T reqDto, BaseNode node, String baid) {
        try {
            String serviceName = node.name().split(Separator.UNDERSCORE)[0].concat(DB_SUFFIX).toLowerCase();
            AccessKeyConfig accessKeyConfig = appSecretService.getSecretByServiceName(serviceName);
            if (accessKeyConfig == null) {
                throw new BizException("P123", "MD5加签:获取PAY密钥配置失败");
            }
            BasePayRequest baseRequest = BasePayRequest.buildParams();
            baseRequest.setBaId(baid)
                    .setBizData(gson.toJson(reqDto))
                    .setAppId(accessKeyConfig.getAppid())
                    .setTimestamp(ZonedDateTime.now(ZoneId.of(TIMEZONE)).toEpochSecond());
            log.info("加签前的报文内容:{}", GsonFactory.getInstance().toJson(baseRequest));
            String signContent = SignatureUtil.getSignContent(baseRequest, TypeHelperUtil.mapOf(String.class, String.class));
            String sign = DigestUtils.md5Hex(signContent + accessKeyConfig.getSecret());
            log.info("生成签名:{}", sign);
            baseRequest.setSign(sign);
            return baseRequest;
        } catch (Exception e) {
            log.error("订单号:{}-{}-executeSign error:{}", reqDto.orderId(), node.url(), e.getMessage());
            throw new RuntimeException("支付系统异常:message[" + e.getMessage() + "]");
        }
    }

    @Override
    public <T extends BasePayPublicParams> T execute(BasePayRequest baseRequest, BaseNode node, String orderId, Class<T> clazz) {

        try {
            String requestContent = SignatureUtil.getSignContent(baseRequest, TypeHelperUtil.mapOf(String.class, String.class));
            Map<String, Object> response = HttpUtil.postRawFormUrlEncoded(config.getPayBaseUrl() + node.url(), requestContent);
            log.info("验签前的报文内容:{}", gson.toJson(response));
            checkSign(node, response);
            String respCode = (String) response.getOrDefault(RESPONSE_CODE, "");
            if (!SUCCESS.equals(respCode)) {
                throw new BizException("P500", String.format("支付节点【 %s 】--- %s", node.nodeDesc(), response.getOrDefault(RESPONSE_MSG, "")));
            }
            String bizData = (String) response.get(RESPONSE_DATA);
            log.info("响应报文内容:{}", bizData);
            return gson.fromJson(bizData, TypeHelperUtil.getType(clazz));
        } catch (Exception e) {
            checkBizException(orderId, node, e);
            throw new BizException("交易系统异常:message[" + e.getMessage() + "]");
        }
    }

    private void checkBizException(String orderId, BaseNode node, Exception e) {
        if (e instanceof BizException) {
            log.error("订单号:{}-execute-{}-executeResult error:{}", orderId, node.nodeDesc(), e.getMessage());
            throw new BizException(((BizException) e).getCode(), "支付系统异常:[" + e.getMessage() + "]");
        }
    }

    /**
     * 半屏支付脚本
     * {\"appId\":\"wx1d595b2fa4620e99\",\"path\":\"pages/third/pay?s=504822c874db3b269694c655babc0b28ca8e819fdbf8aae686af201540ec482e08dce8bd0b5874ef9efc39dfc6ab1122c662003461fc33e41f5ecfb0216947ff\"}","receiptAmount":-1}
     */

    private void checkSign(BaseNode node, Map<String, Object> response) {
        String sign = (String) response.remove(RESPONSE_SIGN);
        String requestId = (String) response.remove(RESPONSE_REQUEST_ID);
        String responseContent = SignatureUtil.getSortedExclusiveBlankStr(response);
        log.info("当前返回的requestId:{},生成验签签名字符串:{}", requestId, responseContent);
        String serviceName = node.name().split(Separator.UNDERSCORE)[0].concat(DB_SUFFIX).toLowerCase();
        AccessKeyConfig accessKeyConfig = appSecretService.getSecretByServiceName(serviceName);
        String confirmSign = DigestUtils.md5Hex(responseContent + accessKeyConfig.getSecret());
        log.info("生成验证签名:{}", confirmSign);
        boolean verify = Objects.equals(sign, confirmSign);
        if (!verify) {
            throw new BizException("响应数据验证签名失败");
        }
    }

}
