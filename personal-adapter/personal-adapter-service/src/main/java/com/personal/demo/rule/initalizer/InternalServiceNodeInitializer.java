package com.personal.demo.rule.initalizer;

import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.rule.registry.InternalServiceNodeRegistry;
import com.personal.demo.rule.strategy.BaseNodeHandleStrategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 服务节点与节点处理器初始化
 * @date 2025/7/2 10:55
 */
public class InternalServiceNodeInitializer {

    private InternalServiceNodeInitializer() {
        throw new UnsupportedOperationException("禁止实例化工具类");
    }

    /**
     * 初始化节点
     * @param configs
     * @return
     */
    public static Map<BaseNode, BaseNodeHandleStrategy> initNodes(List<InternalServiceNodeRegistry> configs) {
        Map<BaseNode, BaseNodeHandleStrategy> allNodeMap = new HashMap<>();
        configs.stream().map(InternalServiceNodeRegistry::getServiceNodes).forEach(allNodeMap::putAll);
        return allNodeMap;


    }
}
