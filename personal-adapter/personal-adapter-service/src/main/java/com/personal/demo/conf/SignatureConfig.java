package com.personal.demo.conf;

import com.personal.demo.anno.Signature;
import com.personal.demo.rule.strategy.SignatureStrategy;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 签名配置类
 * @date 2025/7/8 10:47
 */
@Data
@Getter
@Setter
public class SignatureConfig {
    /**
     * 签名类型
     */
    private final Signature annotation;
    /**
     * 签名策略
     */
    private final SignatureStrategy strategy;
    /**
     * 跳过签名
     */
    private final boolean skip;

    public SignatureConfig(Signature annotation, SignatureStrategy strategy) {
        this.annotation = annotation;
        this.strategy = strategy;
        this.skip = false;
    }

    private SignatureConfig(boolean skip) {
        this.annotation = null;
        this.strategy = null;
        this.skip = skip;
    }

    public static SignatureConfig skip() {
        return new SignatureConfig(true);
    }

    public boolean isRequired() {
        return annotation != null && annotation.required();
    }

    public String getStrategyType() {
        return strategy != null ? strategy.getStrategyType() : "NONE";
    }

}
