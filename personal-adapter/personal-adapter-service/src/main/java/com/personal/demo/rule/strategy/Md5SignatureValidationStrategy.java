package com.personal.demo.rule.strategy;

import com.personal.demo.pojo.base.AccessKeyConfig;
import com.personal.demo.pojo.base.RequestContext;
import com.personal.demo.pojo.base.SignatureValidationParam;
import com.personal.demo.serivce.AppSecretService;
import com.personal.demo.utils.ApiParamUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: MD5验签策略
 * @date 2025/7/8 10:58
 */
@Component
@Slf4j
public class Md5SignatureValidationStrategy implements SignatureValidationStrategy {

    @Resource
    private AppSecretService appSecretService;

    /**
     * 验证方法主体
     */
    @Override
    public boolean validate(SignatureValidationParam params, RequestContext context) {
        try {
            String expectedSign = calculateMd5Sign(params);
            return Objects.equals(params.getSignature(), expectedSign);
        } catch (Exception e) {
            log.error("MD5验签异常", e);
            return false;
        }

    }

    @Override
    public String getStrategyType() {
        return "MD5";
    }

    private String calculateMd5Sign(SignatureValidationParam params) {
        // 获取密钥
        AccessKeyConfig dto = appSecretService.getSecret(params.getAppId());

        // 构建签名字符串
        Map<String, Object> signParams = params.getSignatureParams();

        String signString = ApiParamUtil.buildQueryString(signParams, dto.getSecret());
        // 计算MD5
        return DigestUtils.md5Hex(signString);
    }
}