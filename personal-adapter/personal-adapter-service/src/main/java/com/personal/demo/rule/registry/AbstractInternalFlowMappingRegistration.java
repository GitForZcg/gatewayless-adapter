package com.personal.demo.rule.registry;

import com.personal.demo.enu.ServiceType;
import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.rule.db.InternalFlowMappingDbProcessor;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 抽象内部流程映射注册
 * @date 2025/7/9 10:55
 */
public abstract class AbstractInternalFlowMappingRegistration<T extends Enum<T>> implements InternalFlowNodeMappingRegistry {

    private final T serviceType;
    private final InternalFlowMappingDbProcessor dbProcessor;

    protected AbstractInternalFlowMappingRegistration(T serviceType, InternalFlowMappingDbProcessor dbProcessor) {
        this.serviceType = serviceType;
        this.dbProcessor = dbProcessor;
    }

    /**
     * 获取流程映射
     */
    @Override
    public Map<ServiceType, Map<BaseNode, Class<?>>> getFlowMappingConfig() {
        Map<ServiceType, Map<BaseNode, Class<?>>> flowNodeMap = new EnumMap<>(ServiceType.class);
        Map<BaseNode, Class<?>> mapping = createMapping();
        flowNodeMap.put((ServiceType) serviceType, Collections.unmodifiableMap(mapping));
        return flowNodeMap;
    }

    private Map<BaseNode, Class<?>> createMapping() {
        return dbProcessor.getFlowMapping(serviceType.name());
    }
}
