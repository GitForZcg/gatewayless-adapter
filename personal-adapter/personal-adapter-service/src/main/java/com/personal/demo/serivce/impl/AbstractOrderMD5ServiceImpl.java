package com.personal.demo.serivce.impl;

import com.personal.demo.conf.BaseUrlConfig;
import com.personal.demo.conf.GsonFactory;
import com.personal.demo.consts.Separator;
import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.pojo.base.AccessKeyConfig;
import com.personal.demo.pojo.base.DemoOrderMd5Param;
import com.personal.demo.serivce.AbstractOrderMD5Service;
import com.personal.demo.serivce.AppSecretService;
import com.personal.demo.util.TypeHelperUtil;
import com.personal.demo.utils.ApiParamUtil;
import com.personal.demo.utils.HttpUtil;
import com.common.base.exception.BizException;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;

import static com.personal.demo.consts.AdapterConstant.DB_SUFFIX;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/7/24 13:42
 */
@Service
@Slf4j
public class AbstractOrderMD5ServiceImpl implements AbstractOrderMD5Service {

    private final BaseUrlConfig config;

    private final AppSecretService appSecretService;
    private final static String APPID = "appId";
    private final static String SIGN = "sign";
    private final static String CONTENT = "content";
    private final static String MID = "mid";
    private final static String BID = "bid";
    private final static String SID = "sid";
    private final static String FAIL = "100";
    private final static String SUCCESS = "200";
    private final static String NOTFOUND = "999";
    private final static String CODE = "code";
    private final static String MSG = "msg";
    private final Gson gson = GsonFactory.getInstance();

    public AbstractOrderMD5ServiceImpl(BaseUrlConfig config, AppSecretService appSecretService) {
        this.config = config;
        this.appSecretService = appSecretService;
    }


    @Override
    public <T extends DemoOrderMd5Param> Boolean execute(T reqDto, BaseNode node, String orderId, String sid) {
        String serviceName = node.name().split(Separator.UNDERSCORE)[0].concat(DB_SUFFIX).toLowerCase();
        AccessKeyConfig accessKeyConfig = appSecretService.getSecretByServiceName(serviceName);
        if (accessKeyConfig == null) {
            throw new BizException("123", "MD5加签:获取密钥配置失败");
        }
        String extra = accessKeyConfig.getExtra();
        Map<String, Object> extraConfig = gson.fromJson(extra, TypeHelperUtil.mapOf(String.class, Object.class));
        String json = gson.toJson(reqDto);
//        String json = TestJson.orderJson;
        log.info("MD5组装签名前原始数据json:{}", json);
        Map<String, Object> content = gson.fromJson(json, TypeHelperUtil.mapOf(String.class, Object.class));
        log.info("MD5组装签名前的转化数据json:{}", gson.toJson(content));
        MultiValueMap<String, Object> formData = handleRequestParam(accessKeyConfig, extraConfig, accessKeyConfig.getSecret(), content, sid);
        Map<String, Object> response = HttpUtil.postForEntity(config.getOrderBaseUrl() + node.url(), formData);
        return checkState(response, orderId);
    }

    @NotNull
    private static MultiValueMap<String, Object> handleRequestParam(AccessKeyConfig accessKeyConfig, Map<String, Object> extraConfig, String secret, Map<String, Object> content, String sid) {
        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
        formData.add(APPID, accessKeyConfig.getAppid());
        formData.add(MID, extraConfig.get(MID));
        formData.add(SID, sid);
        formData.add(BID, extraConfig.get(BID));
        formData.add(CONTENT, content);
        Map<String, Object> dataMap = formData.toSingleValueMap();
        String signStr = ApiParamUtil.buildQueryString(dataMap, secret);
        log.info("计算签名字符串:{}", signStr);
        formData.add(SIGN, DigestUtils.md5Hex(signStr));
        return formData;
    }

    private boolean checkState(Map<String, Object> response, String orderId) {
        if (ObjectUtils.isEmpty(response)) {
            log.error("同步订单响应数据为空");
            return Boolean.FALSE;
        }
        String code = response.get(CODE).toString();
        if (FAIL.equals(code) || NOTFOUND.equals(code)) {
            String errorMsg = response.getOrDefault(MSG, "").toString();
            log.error("同步订单响应响应失败,订单号:{},失败信息:{}", orderId, errorMsg);
            return Boolean.FALSE;
        }
        if (SUCCESS.equals(code)) {
            log.info("同步订单响应结果成功");
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
