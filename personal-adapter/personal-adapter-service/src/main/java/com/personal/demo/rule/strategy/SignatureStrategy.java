package com.personal.demo.rule.strategy;

import com.personal.demo.pojo.base.RequestContext;
import com.personal.demo.pojo.base.SignatureParam;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 签名策略
 * @date 2025/7/18 10:06
 */
public interface SignatureStrategy {

    /**
     * 计算签名
     */
    default String calculate(SignatureParam params, RequestContext context) {
        return "";
    }

    default String calculate(SignatureParam params) {
        return "";
    }

    /**
     * 获取策略类型标识
     */
    String getStrategyType();
}
