package com.personal.demo.rule.handler;

import com.personal.demo.conf.SignatureValidationConfig;
import com.personal.demo.pojo.base.RequestContext;
import com.personal.demo.pojo.base.SignatureValidationParam;
import com.personal.demo.pojo.wrapper.AdapterRespCode;
import com.personal.demo.rule.exception.AdapterException;
import com.personal.demo.rule.exception.AdapterSignException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

import static com.personal.demo.pojo.wrapper.AdapterErrorCode.SIGN_RULE_ERROR;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 签名验证器
 * @date 2025/7/8 10:53
 */
@Component
@Slf4j
public class SignatureValidationProcessor {


    private final SignatureValidationStrategySelector signatureSelector;

    public SignatureValidationProcessor(SignatureValidationStrategySelector signatureSelector) {
        this.signatureSelector = signatureSelector;
    }

    /**
     * 基于注解的验签方法
     */
    public void validateSignature(Object params, RequestContext context) {

        // 获取验签配置
        SignatureValidationConfig config = signatureSelector.getValidationConfig(params);

        // 如果跳过验签
        if (config.isSkip()) {
            log.debug("跳过验签 - API: [{}]/[{}], 参数类型: [{}]",
                    context.getApiType(),
                    context.getApiSubType(),
                    params.getClass().getSimpleName()
            );
            return;
        }

        // 如果验签非必须
        if (!config.isRequired()) {
            log.debug("验签非必须 - API: [{}]/[{}], 参数类型: [{}]",
                    context.getApiType(),
                    context.getApiSubType(),
                    params.getClass().getSimpleName()
            );
            return;
        }

        // 执行验签核心逻辑
        performSignatureValidation(params, context, config);
    }

    /**
     * 执行验签核心逻辑
     */
    private void performSignatureValidation(Object params, RequestContext context, SignatureValidationConfig config) {

        // 检查参数类型
        if (!(params instanceof SignatureValidationParam signatureParam)) {
            String errorMsg = String.format(SIGN_RULE_ERROR.message, SignatureValidationParam.class.getSimpleName());
            throw new AdapterException(SIGN_RULE_ERROR.code,
                    errorMsg + params.getClass().getSimpleName(),
                    SIGN_RULE_ERROR.type
            );
        }

        // 验证必要字段
        validateRequiredFields(signatureParam);

        try {
            // 执行验签
            boolean isValid = config.getStrategy().validate(signatureParam, context);
            if (isValid) {
                handleValidationSuccess(signatureParam, context, config);
            }
        } catch (AdapterSignException e) {
            log.error("执行签名验证异常");
            throw e;
        } catch (Exception e) {
            handleValidationException(signatureParam, context, config, e);
        }
    }

    /**
     * 验证必要字段
     */
    private void validateRequiredFields(SignatureValidationParam signatureParam) {
        if (StringUtils.isBlank(signatureParam.getSignature())) {
            throw new AdapterSignException(AdapterRespCode.SIGN_MISSING);
        }

        if (StringUtils.isBlank(signatureParam.getAppId())) {
            throw new AdapterSignException(AdapterRespCode.APPID_MISSING);
        }
    }

    /**
     * 处理验签成功SIGNATURE_ERROR
     */
    private void handleValidationSuccess(SignatureValidationParam signatureParam, RequestContext context, SignatureValidationConfig config) {
        log.info("验签成功 - API_TYPE: [{}]/[{}], API_PATH:[{}],APP_ID: [{}], Strategy: [{}], ParamType: [{}]",
                context.getApiType(), context.getApiSubType(), context.getApiPath(),
                signatureParam.getAppId(), config.getStrategyType(),
                signatureParam.getClass().getSimpleName()
        );

    }

    /**
     * 处理验签异常
     */
    private void handleValidationException(SignatureValidationParam signatureParam, RequestContext context, SignatureValidationConfig config, Exception e) {
        log.error("验签系统异常 - API: [{}]/[{}], API_PATH:[{}], APP_ID: [{}], Strategy: [{}], ParamType: [{}]",
                context.getApiType(), context.getApiSubType(), context.getApiPath(),
                signatureParam.getAppId(), config.getStrategyType(),
                signatureParam.getClass().getSimpleName(), e);

        throw new AdapterSignException(AdapterRespCode.SIGN_VERIFY_ERROR);
    }


    /**
     * 异步验签（如果需要）
     */
    @Async
    public CompletableFuture<Boolean> validateSignatureAsync(Object params, RequestContext context) {
        try {
            validateSignature(params, context);
            return CompletableFuture.completedFuture(Boolean.TRUE);
        } catch (Exception e) {
            return CompletableFuture.completedFuture(Boolean.FALSE);
        }
    }
}
