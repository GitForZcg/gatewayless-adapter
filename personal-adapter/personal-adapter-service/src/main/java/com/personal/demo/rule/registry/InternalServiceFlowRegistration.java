package com.personal.demo.rule.registry;


import com.personal.demo.enu.ServiceType;
import com.personal.demo.rule.flow.UnifiedInternalServiceFlowProcessor;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 流程顶级规则
 * @date 2025/7/2 10:57
 */
public interface InternalServiceFlowRegistration {

    /**
     * 获取流程类型
     *
     * @return 流程类型
     */
    ServiceType getFlowType();

    /**
     * 获取流程实现
     *
     * @return 流程实现
     */
    UnifiedInternalServiceFlowProcessor<?> getSearchFlow();
}
