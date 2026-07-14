package com.personal.demo.rule.handler;

import com.personal.demo.conf.SignatureConfig;
import com.personal.demo.pojo.base.RequestContext;
import com.personal.demo.pojo.base.SignatureParam;
import com.personal.demo.pojo.wrapper.AdapterRespCode;
import com.personal.demo.rule.exception.AdapterException;
import com.personal.demo.rule.exception.AdapterSignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.personal.demo.pojo.wrapper.AdapterErrorCode.SIGN_RULE_ERROR;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 签名计算器
 * @date 2025/7/8 10:53
 */
@Component
@Slf4j
public class SignatureProcessor {

    private final SignatureStrategySelector signatureSelector;

    public SignatureProcessor(SignatureStrategySelector signatureSelector) {
        this.signatureSelector = signatureSelector;
    }

    /**
     * 基于注解的加签方法
     */
    public String calculateSignature(Object params, RequestContext context) throws Exception {

        // 获取验签配置
        SignatureConfig config = signatureSelector.getCalculateConfig(params);

        // 如果跳过验签
        if (config.isSkip()) {
            log.debug("跳过加签 - API: [{}]/[{}], 参数类型: [{}]",
                    context.getApiType(),
                    context.getApiSubType(),
                    params.getClass().getSimpleName()
            );
            return "";
        }

        // 如果验签非必须
        if (!config.isRequired()) {
            log.debug("加签非必须 - API: [{}]/[{}], 参数类型: [{}]",
                    context.getApiType(),
                    context.getApiSubType(),
                    params.getClass().getSimpleName()
            );
            return "";
        }

        // 执行验签核心逻辑
        return performSignatureCalculate(params, context, config);
    }

    /**
     * 执行加签核心逻辑
     */
    private String performSignatureCalculate(Object params, RequestContext context, SignatureConfig config) throws Exception {

        String sign = null;
        // 检查参数类型
        if (!(params instanceof SignatureParam signatureParam)) {
            throw new AdapterException(SIGN_RULE_ERROR.code,
                    String.format(SIGN_RULE_ERROR.message, SignatureParam.class.getSimpleName()),
                    SIGN_RULE_ERROR.type
            );
        }

        try {
            // 执行加签
            sign = config.getStrategy().calculate(signatureParam, context);
            handleCalculateSuccess(signatureParam, context, config);
            return sign;
        } catch (AdapterSignException | AdapterException e) {
            log.error("执行签名计算异常");
            throw e;
        } catch (Exception e) {
            handleCalculateException(signatureParam, context, config, e);
        }
        return sign;
    }

    /**
     * 处理加签异常
     */
    private void handleCalculateException(SignatureParam signatureParam, RequestContext context, SignatureConfig config, Exception e) {
        log.error("加签系统异常 - API: [{}]/[{}], API_PATH:[{}], Strategy: [{}], ParamType: [{}]",
                context.getApiType(), context.getApiSubType(), context.getApiPath(),
                config.getStrategyType(),
                signatureParam.getClass().getSimpleName(), e);
        throw new AdapterSignException(AdapterRespCode.SIGN_CALCULATE_ERROR);
    }


    /**
     * 处理加签成功SIGNATURE_ERROR
     */
    private void handleCalculateSuccess(SignatureParam signatureParam, RequestContext context, SignatureConfig config) {
        log.info("加签成功 - API_TYPE: [{}]/[{}], API_PATH:[{}], Strategy: [{}], ParamType: [{}]",
                context.getApiType(), context.getApiSubType(), context.getApiPath(),
                config.getStrategyType(),
                signatureParam.getClass().getSimpleName()
        );
    }
}
