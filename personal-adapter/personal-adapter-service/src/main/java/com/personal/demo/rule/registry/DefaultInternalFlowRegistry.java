package com.personal.demo.rule.registry;

import com.personal.demo.enu.ServiceType;
import com.personal.demo.rule.flow.UnifiedInternalServiceFlowProcessor;
import com.common.base.exception.BizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.personal.demo.consts.BizCode.TYPE_NOT_FOUND;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 默认调用流程注册器实现
 * @date 2025/7/2 10:56
 */
@Component
public class DefaultInternalFlowRegistry implements BaseInternalFlowRegistry {
    /**
     * 存储调用流程的映射
     */
    private final Map<ServiceType, UnifiedInternalServiceFlowProcessor<?>> flows = new HashMap<>();

    /**
     * 注入所有调用流程
     */
    @Autowired
    public DefaultInternalFlowRegistry(List<InternalServiceFlowRegistration> registrations) {
        registrations.forEach(registration ->
                registerFlow(registration.getFlowType(), registration.getSearchFlow())
        );
    }

    /**
     * 注册流程
     * @param flowType 服务类型
     * @param flow 服务流程实现
     */
    @Override
    public void registerFlow(ServiceType flowType, UnifiedInternalServiceFlowProcessor<?> flow) {
        flows.put(flowType, flow);
    }

    /**
     * 获取流程
     * @param flowType 流程类型
     */
    @Override
    public UnifiedInternalServiceFlowProcessor<?> getFlow(ServiceType flowType) {
        UnifiedInternalServiceFlowProcessor<?> flow = flows.get(flowType);
        if (flow == null) {
            throw new BizException(TYPE_NOT_FOUND, "不支持的调用流程类型：" + flowType);
        }
        return flow;
    }
}