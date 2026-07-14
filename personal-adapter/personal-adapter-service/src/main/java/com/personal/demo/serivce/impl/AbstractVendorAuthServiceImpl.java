package com.personal.demo.serivce.impl;

import com.personal.demo.conf.BaseUrlConfig;
import com.personal.demo.conf.GsonFactory;
import com.personal.demo.conf.RedisFactory;
import com.personal.demo.consts.Separator;
import com.personal.demo.enu.RedisVendorKey;
import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.enu.payment.VendorLogin;
import com.personal.demo.pojo.base.AccessKeyConfig;
import com.personal.demo.pojo.base.VendorBaseResponse;
import com.personal.demo.serivce.AbstractVendorAuthService;
import com.personal.demo.serivce.AppSecretService;
import com.personal.demo.util.TypeHelperUtil;
import com.personal.demo.utils.HttpUtil;
import com.common.base.exception.BizException;
import com.common.redis.sdk.RedissionClient;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.personal.demo.consts.AdapterConstant.DB_SUFFIX;
import static com.personal.demo.consts.AdapterConstant.TIMEZONE;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/9/26 09:55
 */

@Service
@Slf4j
public class AbstractVendorAuthServiceImpl implements AbstractVendorAuthService {


    private final static long expiredTime = 30 * 60L;
    private final static String ACK = "ack";
    private final BaseUrlConfig config;
    private final AppSecretService appSecretService;
    private final Gson gson = GsonFactory.getInstance();
    private final RedissionClient redissionClient = RedisFactory.getClient(RedisFactory.RedisClient.DEFAULT);

    public AbstractVendorAuthServiceImpl(BaseUrlConfig config, AppSecretService appSecretService) {
        this.config = config;
        this.appSecretService = appSecretService;
    }

    @Override
    public Map<String, Object> executeAuthParam(BaseNode node) {
        String serviceName = node.name().split(Separator.UNDERSCORE)[0].concat(DB_SUFFIX).toLowerCase();
        AccessKeyConfig accessKeyConfig = appSecretService.getSecretByServiceName(serviceName);
        if (accessKeyConfig == null) {
            throw new BizException("A500", "供应商认证:获取密钥配置失败");
        }
        String authKey = RedisVendorKey.LOGIN_AUTH.generateKey();
        boolean hasKey = redissionClient.hasKey(authKey);
        log.info("check vendor login auth param is existed:{}", hasKey ? "存在" : "过期");
        return handleAuthParam(hasKey, accessKeyConfig, authKey);
    }

    @Override
    public VendorBaseResponse<?> executeResult(Map<String, Object> authRespMap, String requestJson, BaseNode node) {
        Map<String, Object> respMap = HttpUtil.vendorRequestForEntity(
                config.getPaymentBaseUrl() + node.url(),
                requestJson,
                authRespMap,
                HttpMethod.valueOf(node.method())
        );
        return gson.fromJson(gson.toJson(respMap), VendorBaseResponse.class);
    }

    private Map<String, Object> handleAuthParam(boolean hasKey, AccessKeyConfig accessKeyConfig, String authKey) {
        Map<String, Object> authRespMap;
        if (!hasKey) {
            long currentTime = ZonedDateTime.now(ZoneId.of(TIMEZONE)).toInstant().toEpochMilli();
            String requestSecret = DigestUtils.sha256Hex(String.format("%s:%s:%s",
                    accessKeyConfig.getSecret(),
                    accessKeyConfig.getAppid(),
                    currentTime));
            Map<String, Object> authParamMap = Maps.newHashMapWithExpectedSize(3);
            authParamMap.put("secret", requestSecret);
            authParamMap.put("appCode", accessKeyConfig.getAppid());
            authParamMap.put("timestamp", currentTime);
            Map<String, Object>   respMap = HttpUtil.vendorPostForEntity(config.getPaymentBaseUrl() + VendorLogin.LOGIN.url, gson.toJson(authParamMap));
            log.info("vendor login auth response{}", respMap);
            String code = respMap.get("code").toString();
            Boolean success = (Boolean) respMap.get("success");
            if (!StringUtils.equalsIgnoreCase(ACK, code) || !success) {
                String message = respMap.get("message").toString();
                throw new BizException("L500",message);
            }
            String data = respMap.get("data").toString();
            log.info("vendor login auth  ata{}", data);
            Type type = TypeHelperUtil.mapOf(String.class, Object.class);
            authRespMap= gson.fromJson(data, type);
            redissionClient.putAllEx(authKey, authRespMap, expiredTime, TimeUnit.SECONDS);
            log.info("cache vendor login auth param success");
        } else {
            authRespMap = redissionClient.readAllMap(authKey);
        }
        return authRespMap;
    }

}
