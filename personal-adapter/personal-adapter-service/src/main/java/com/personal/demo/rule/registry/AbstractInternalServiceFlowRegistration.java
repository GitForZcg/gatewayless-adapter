package com.personal.demo.rule.registry;


import com.personal.demo.enu.ServiceType;
import com.personal.demo.rule.flow.UnifiedInternalServiceFlowProcessor;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 抽象内部流程注册
 * @date 2025/7/9 10:56
 */
public abstract class AbstractInternalServiceFlowRegistration<T extends Enum<T>> implements InternalServiceFlowRegistration {

    private final T serviceType;
    private final UnifiedInternalServiceFlowProcessor<?> flow;

    protected AbstractInternalServiceFlowRegistration(T serviceType, UnifiedInternalServiceFlowProcessor<?> flow) {
        this.serviceType = serviceType;
        this.flow = flow;
    }

    /**
     * 抽象服务类型
     */
    @Override
    public ServiceType getFlowType() {
        return (ServiceType) serviceType;
    }

    /**
     * 搜寻服务流程
     */
    @Override
    public UnifiedInternalServiceFlowProcessor<?> getSearchFlow() {
        return flow;
    }
}
