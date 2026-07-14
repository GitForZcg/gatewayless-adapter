package com.personal.demo.conf;

import com.personal.demo.anno.Signature;
import com.personal.demo.rule.strategy.SignatureValidationStrategy;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 签名验证配置类
 * @date 2025/7/8 10:47
 */
@Data
@Getter
@Setter
public class SignatureValidationConfig {

    /**
     * 签名注解
     */
    private final Signature annotation;
    /**
     * 签名验证策略
     */
    private final SignatureValidationStrategy strategy;
    /**
     * 跳过签名
     */
    private final boolean skip;

    public SignatureValidationConfig(Signature annotation, SignatureValidationStrategy strategy) {
        this.annotation = annotation;
        this.strategy = strategy;
        this.skip = false;
    }

    private SignatureValidationConfig(boolean skip) {
        this.annotation = null;
        this.strategy = null;
        this.skip = skip;
    }

    public static SignatureValidationConfig skip() {
        return new SignatureValidationConfig(true);
    }

    public boolean isRequired() {
        return annotation != null && annotation.required();
    }

    public String getStrategyType() {
        return strategy != null ? strategy.getStrategyType() : "NONE";
    }

}
