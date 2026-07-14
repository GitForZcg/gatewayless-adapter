package com.personal.demo.rule.initalizer;

import com.personal.demo.enu.ServiceType;
import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.rule.registry.InternalFlowNodeMappingRegistry;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 流程节点映射初始化
 * @date 2025/7/2 10:53
 */
public class InternalFlowNodeMappingInitializer {

    private InternalFlowNodeMappingInitializer() {
        throw new UnsupportedOperationException("禁止实例化工具类");
    }

    /**
     * 初始化映射
     */
    public static Map<ServiceType, Map<BaseNode, Class<?>>> initMappings(List<InternalFlowNodeMappingRegistry> configs) {
        Map<ServiceType, Map<BaseNode, Class<?>>> allNodeMap = new EnumMap<>(ServiceType.class);
        configs.stream().map(InternalFlowNodeMappingRegistry::getFlowMappingConfig).forEach(allNodeMap::putAll);
        return allNodeMap;
    }
}
