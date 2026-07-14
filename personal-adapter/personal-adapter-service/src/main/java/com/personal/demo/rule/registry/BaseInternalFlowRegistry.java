package com.personal.demo.rule.registry;


import com.personal.demo.enu.ServiceType;
import com.personal.demo.rule.flow.UnifiedInternalServiceFlowProcessor;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 调用流程注册表
 * @date 2025/7/2 10:56
 */
public interface BaseInternalFlowRegistry {

    /**
     * 注册调用流程
     *
     * @param type 服务类型
     * @param flow 服务流程实现
     */
    void registerFlow(ServiceType type, UnifiedInternalServiceFlowProcessor<?> flow);

    /**
     * 获取特定类型的流程
     *
     * @param type 流程类型
     * @return 对应的流程
     */
    UnifiedInternalServiceFlowProcessor<?> getFlow(ServiceType type);
}
