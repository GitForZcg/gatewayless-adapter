package com.personal.demo.rule.strategy;

import com.personal.demo.pojo.base.RequestContext;
import com.personal.demo.pojo.base.SignatureValidationParam;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 签名策略
 * @date 2025/7/8 10:59
 */
public interface SignatureValidationStrategy {

    /**
     * 验证签名
     */
    boolean validate(SignatureValidationParam params, RequestContext context);

    /**
     * 获取策略类型标识
     *
     * @return 策略标识
     */
    String getStrategyType();
}
