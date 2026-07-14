package com.personal.demo.serivce.impl;

import com.personal.demo.conf.BaseUrlConfig;
import com.personal.demo.conf.GsonFactory;
import com.personal.demo.consts.Separator;
import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.pojo.base.AccessKeyConfig;
import com.personal.demo.pojo.base.BaseSM2PublicParam;
import com.personal.demo.pojo.wrapper.AdapterRespCode;
import com.personal.demo.rule.exception.AdapterSignException;
import com.personal.demo.serivce.AbstractSM2Service;
import com.personal.demo.serivce.AppSecretService;
import com.personal.demo.util.TypeHelperUtil;
import com.personal.demo.utils.HttpUtil;
import com.personal.demo.utils.MD5Utils;
import com.personal.demo.utils.SM2Util;
import com.personal.demo.utils.SignatureUtil;
import com.common.base.exception.BizException;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.personal.demo.consts.AdapterConstant.TIMEZONE;
import static com.personal.demo.consts.HttpConstant.*;

/**
 * @Author: fxs
 * @Date: 2025/8/13 15:18
 */
@Service
@Slf4j
public class AbstractSM2ServiceImpl implements AbstractSM2Service {

    private final AppSecretService appSecretService;
    private final BaseUrlConfig config;

    private final static Gson gson = GsonFactory.getInstance();

    public AbstractSM2ServiceImpl(AppSecretService appSecretService, BaseUrlConfig config) {
        this.appSecretService = appSecretService;
        this.config = config;
    }

    @Override
    public <T extends BaseSM2PublicParam> Map<String, Object> executeSign(T reqDto, BaseNode node) {
        AccessKeyConfig accessKeyConfig = appSecretService.getSM2Secret(node.channel());
        if (accessKeyConfig == null) {
            throw new BizException("123", "SM2加签:获取密钥配置失败");
        }
        String signContent = SignatureUtil.getSignContent(reqDto, TypeHelperUtil.mapOf(String.class, Object.class));
        log.info("节点:{},加签前的报文字符串:{}", node.name(), signContent);
        //加签
        String sign = SM2Util.sm2Sign(signContent, accessKeyConfig.getPrivateKey());
        log.info("节点:{},生成的签名: {}", node.name(), sign);
        Map<String, Object> map = Maps.newHashMap();
        map.put(HEADER_SIGN, sign);
        map.put(BODY_DATA, gson.toJson(reqDto));
        return map;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends BaseSM2PublicParam> T executeResult(Map<String, Object> map, BaseNode node, Class<T> resClazz) {
        String serviceName = node.name().split(Separator.UNDERSCORE)[0].toLowerCase();
        Map<String, Object> response;
        try {
            response = HttpUtil.postForEntity(config.getServiceUrl(serviceName) + node.url(), map);
        } catch (Exception e) {
            throw new BizException("999", "调用三方公司获取请求异常");
        }
        log.info("SM2验签处理包含请求头，请求体的结果:{}", gson.toJson(response));
        Map<String, String> headers = (Map<String, String>) response.remove(HEADER);
        if (headers.isEmpty()) {
            throw new AdapterSignException(AdapterRespCode.SIGN_PARAM_MISSING);
        }
        String appid = headers.remove(HEADER_APPID);
        String timestamp = headers.remove(HEADER_TIMESTAMP);
        String receivedApiSign = headers.remove(HEADER_API_SIGN);
        String sign = headers.remove(HEADER_SIGN);
        // 验证必要参数
        if (appid == null || timestamp == null || receivedApiSign == null || sign == null) {
            throw new AdapterSignException(AdapterRespCode.SIGN_PARAM_MISSING);
        }
        AccessKeyConfig accessKeyConfig = appSecretService.getSecret(appid);
        // 验证appid
        if (accessKeyConfig == null) {
            throw new AdapterSignException(AdapterRespCode.APPID_NOT_MATCH);
        }

        long currentTime = ZonedDateTime.now(ZoneId.of(TIMEZONE)).toEpochSecond();
        long requestTime = Long.parseLong(timestamp);
        long absRequestTime = Math.abs(currentTime - requestTime);
        if (absRequestTime > accessKeyConfig.getTimestampTolerance()) {
            log.error("响应时间戳超出容忍范围,差值为:[{}]", absRequestTime);
            throw new AdapterSignException(AdapterRespCode.REQUEST_TIMESTAMP_ERROR);
        }
        String calculatedApiSign = calculateApiSignature(appid, timestamp, sign, accessKeyConfig.getSecret());
        boolean apiSignValid = calculatedApiSign.equals(receivedApiSign);
        log.info("计算的API签名比对验证结果:[{}]", apiSignValid);
        if (!apiSignValid) {
            throw new AdapterSignException(AdapterRespCode.API_SIGN_VERIFY_FAIL);
        }

        String contentStr = SignatureUtil.getSignContent(response);
        boolean result = SM2Util.sm2Check(contentStr, sign, accessKeyConfig.getPublicKey());
        log.info("验证签名结果: {}", (result ? "成功" : "失败"));
        if (!result) {
            throw new AdapterSignException(AdapterRespCode.SIGN_VERIFY_FAIL);
        }
        return gson.fromJson(gson.toJson(response), resClazz);
    }

    private String calculateApiSignature(String appid, String timestamp, String businessSign, String secret) {
        Map<String, Object> apiSignParams = new HashMap<>();
        apiSignParams.put(HEADER_APPID, appid);
        apiSignParams.put(SECRET, secret);
        apiSignParams.put(HEADER_SIGN, businessSign);
        apiSignParams.put(HEADER_TIMESTAMP, timestamp);
        String signContent = SignatureUtil.getSignContent(apiSignParams);
        log.info("API签名内容: {}", GsonFactory.getInstance().toJson(apiSignParams));
        return Objects.requireNonNull(MD5Utils.getMD5Content(signContent)).toLowerCase();
    }
}
