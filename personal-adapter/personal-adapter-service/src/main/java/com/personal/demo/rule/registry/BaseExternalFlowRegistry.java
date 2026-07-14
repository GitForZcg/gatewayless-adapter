package com.personal.demo.rule.registry;


import com.personal.demo.rule.flow.UnifiedExternalServiceFlowProcessor;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 调用流程注册表
 * @date 2025/7/7 10:56
 */
public interface BaseExternalFlowRegistry {

    /**
     * 注册调用流程
     */
    void registerFlow(String node, UnifiedExternalServiceFlowProcessor<?> flow);

    /**
     * 获取特定类型的流程
     */
    UnifiedExternalServiceFlowProcessor<?> getFlow(String node);
}
