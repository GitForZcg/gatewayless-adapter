package com.personal.demo.rule.strategy;

import com.personal.demo.conf.GsonFactory;
import com.personal.demo.pojo.base.AccessKeyConfig;
import com.personal.demo.pojo.base.RequestContext;
import com.personal.demo.pojo.base.SignatureParam;
import com.personal.demo.pojo.base.SignatureValidationParam;
import com.personal.demo.pojo.wrapper.AdapterRespCode;
import com.personal.demo.rule.exception.AdapterException;
import com.personal.demo.rule.exception.AdapterSignException;
import com.personal.demo.serivce.AppSecretService;
import com.personal.demo.util.TypeHelperUtil;
import com.personal.demo.utils.MD5Utils;
import com.personal.demo.utils.SM2Util;
import com.personal.demo.utils.SecureStringPair;
import com.personal.demo.utils.SignatureUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.personal.demo.consts.HttpConstant.*;
import static com.personal.demo.pojo.wrapper.AdapterErrorCode.PRIVATE_KEY_NOT_FOUND;
import static com.personal.demo.utils.StringDecryptUtil.verifySecureStringPair;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: Sm2签名
 * @date 2025/7/16 09:50
 */
@Slf4j
@Component
public class BaseSm2SignatureStrategy implements SignatureValidationStrategy, SignatureStrategy {


    private final AppSecretService appSecretService;


    public BaseSm2SignatureStrategy(AppSecretService appSecretService) {
        this.appSecretService = appSecretService;
    }

    /**
     * 验证方法主题
     */
    @Override
    public boolean validate(SignatureValidationParam params, RequestContext context) {

        try {
            return validationSM2Sign(params, context);
        } catch (Exception e) {
            log.error("SM2验签异常", e);
            if (e instanceof AdapterSignException error) {
                throw new AdapterSignException(error.getCode(), error.getMessage());
            }

            if (e instanceof AdapterException error) {
                throw new AdapterSignException(AdapterRespCode.UNKNOWN_SYSTEM_ERROR.code,
                        AdapterRespCode.UNKNOWN_SYSTEM_ERROR.message + error.getType());
            }
            return false;
        }
    }

    @Override
    public String getStrategyType() {
        return "SM2";
    }

    private Boolean validationSM2Sign(SignatureValidationParam params, RequestContext context) {
        // 获取密钥
        AccessKeyConfig accessKey = appSecretService.getSecret(params.getAppId(), context.getAccessKeyId());
        if (accessKey == null) {
            throw new AdapterSignException(AdapterRespCode.APPID_ERROR);
        }
        // 构建签名字符串
        Map<String, Object> signParams = params.getSignatureParams();

        log.info("开始验证签名，服务[{}]---节点[{}]---请求参数:[{}]", context.getApiType(), context.getApiSubType(), params.getOriginalParams());

        //第一步：验证api secret有效性
        if (!verifyApiSecret(accessKey)) {
            throw new AdapterSignException(AdapterRespCode.APPID_ERROR);
        }

        // 第二步：验证API签名
        if (!verifyApiSignature(signParams, accessKey.getAppid(), accessKey.getSecret(), accessKey.getTimestampTolerance())) {
            throw new AdapterSignException(AdapterRespCode.API_SIGN_VERIFY_FAIL);
        }

        // 第三步：验证业务数据签名
        if (!verifyBusinessSignature(signParams, accessKey.getPublicKey())) {
            throw new AdapterSignException(AdapterRespCode.SIGN_VERIFY_FAIL);
        }
        return Boolean.TRUE;
    }

    private boolean verifyApiSecret(AccessKeyConfig accessKey) {
        SecureStringPair pair = new SecureStringPair(accessKey.getAppid(), accessKey.getSecret(), accessKey.getSalt(), accessKey.getExpireTime());
        boolean result = verifySecureStringPair(pair);
        log.info("访问密钥[appid]-[secret]验证结果:[{}]", result);
        return result;
    }


    /**
     * 验证API签名
     */
    private boolean verifyApiSignature(Map<String, Object> signParams, String appId, String secret, int timestampTolerance) {
        // 获取请求头信息
        String appid = (String) signParams.get(HEADER_APPID);
        String timestamp = (String) signParams.get(HEADER_TIMESTAMP);
        String receivedApiSign = (String) signParams.get(HEADER_API_SIGN);
        String receivedSign = (String) signParams.get(HEADER_SIGN);
        // 验证必要参数
        if (appid == null || timestamp == null || receivedApiSign == null || receivedSign == null) {
            throw new AdapterSignException(AdapterRespCode.SIGN_PARAM_MISSING);
        }

        // 验证appid
        if (!appId.equals(appid)) {
            throw new AdapterSignException(AdapterRespCode.APPID_NOT_MATCH);
        }

        // 验证时间戳（防重放攻击）
        long requestTime = Long.parseLong(timestamp);
        long currentTime = ZonedDateTime.now(ZoneId.of("Asia/Shanghai")).toEpochSecond();
        long absRequestTime = Math.abs(currentTime - requestTime);
        if (absRequestTime > timestampTolerance) {
            log.error("请求时间戳超出容忍范围,差值为:{}", absRequestTime);
            throw new AdapterSignException(AdapterRespCode.REQUEST_TIMESTAMP_ERROR);
        }

        // 重新计算API签名
        String calculatedApiSign = calculateApiSignature(appid, timestamp, receivedSign, secret);

        boolean apiSignValid = calculatedApiSign.equals(receivedApiSign);
        log.info("计算的API签名比对验证结果:[{}]", apiSignValid);
        return apiSignValid;

    }

    /**
     * 计算API签名
     */
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


    /**
     * 验证业务数据SM2签名
     */
    private boolean verifyBusinessSignature(Map<String, Object> requestMap, String publicKey) {
        // 提取签名
        String receivedSign = (String) requestMap.remove("sign");
        requestMap.remove(HEADER_TIMESTAMP);
        requestMap.remove(HEADER_API_SIGN);
        requestMap.remove(HEADER_APPID);
        if (receivedSign == null || receivedSign.isEmpty()) {
            log.warn("缺少签名sign字段 ");
            throw new AdapterSignException(AdapterRespCode.SIGN_MISSING);
        }
        Boolean result = checkSign(GsonFactory.getInstance().toJson(requestMap), receivedSign, publicKey);
        log.info("签名验证结果: {}", result ? "成功" : "失败");
        return result;
    }


    private static Boolean checkSign(String responseStr, String sign, String publicKey) {
        try {
            Map<String, Object> responseBodyMap = GsonFactory.getInstance().fromJson(responseStr, TypeHelperUtil.mapOf(String.class, Object.class));
            String contentStr = SignatureUtil.getSignContent(responseBodyMap);
            return SM2Util.sm2Check(contentStr, sign, publicKey);
        } catch (Exception e) {
            log.error("验证签名异常");
            return Boolean.FALSE;
        }
    }


    @Override
    public String calculate(SignatureParam params, RequestContext context) {
        try {
            return calculateSM2Sign(params, context);
        } catch (Exception e) {
            log.error("SM2验签异常", e);
            if (e instanceof AdapterSignException error) {
                throw new AdapterSignException(error.getCode(), error.getMessage());
            }
            if (e instanceof AdapterException error) {
                throw new AdapterSignException(AdapterRespCode.UNKNOWN_SYSTEM_ERROR.code,
                        AdapterRespCode.UNKNOWN_SYSTEM_ERROR.message + error.getType());
            }
            return "";
        }
    }

    private String calculateSM2Sign(SignatureParam params, RequestContext context) {
        AccessKeyConfig accessKeyConfig = appSecretService.getSecret(context.getAccessKeyId());
        if (accessKeyConfig == null) {
            throw new AdapterException(PRIVATE_KEY_NOT_FOUND);
        }
        Map<String, Object> sortedParams = GsonFactory.getInstance().fromJson(GsonFactory.getInstance().toJson(params), TypeHelperUtil.mapOf(String.class, Object.class));
        String signStr = SignatureUtil.calculateSign(sortedParams, params.needSignature());
        String sign = SM2Util.sm2Sign(signStr, accessKeyConfig.getPrivateKey());
        log.info("SM2 计算签名完成,签名:[{}]", sign);
        return sign;
    }

}
