package com.personal.demo.rule.registry;

import com.personal.demo.rule.flow.UnifiedExternalServiceFlowProcessor;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 抽象外部服务流程注册
 * @date 2025/7/9 10:55
 */
public abstract class AbstractExternalServiceFlowRegistration<T extends Enum<T>> implements ExternalServiceFlowRegistration {

    private final T apiNode;
    private final UnifiedExternalServiceFlowProcessor<?> flow;

    protected AbstractExternalServiceFlowRegistration(T apiNode, UnifiedExternalServiceFlowProcessor<?> flow) {
        this.apiNode = apiNode;
        this.flow = flow;
    }

    @Override
    public String getFlowType() {
        return apiNode.name();
    }

    @Override
    public UnifiedExternalServiceFlowProcessor<?> getSearchFlow() {
        return flow;
    }
}
