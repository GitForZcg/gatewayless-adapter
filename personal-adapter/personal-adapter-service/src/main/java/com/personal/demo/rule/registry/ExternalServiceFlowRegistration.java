package com.personal.demo.rule.registry;


import com.personal.demo.rule.flow.UnifiedExternalServiceFlowProcessor;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 外部服务注册顶级规则
 * @date 2025/7/4 10:57
 */
public interface ExternalServiceFlowRegistration {

    /**
     * 获取流程类型
     *
     * @return 流程类型
     */
    String getFlowType();

    /**
     * 获取流程实现
     *
     * @return 流程实现
     */
    UnifiedExternalServiceFlowProcessor<?> getSearchFlow();
}
