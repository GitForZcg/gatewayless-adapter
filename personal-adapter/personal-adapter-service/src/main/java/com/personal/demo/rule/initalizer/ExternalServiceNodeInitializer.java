package com.personal.demo.rule.initalizer;

import com.personal.demo.enu.external.base.ExternalBaseNode;
import com.personal.demo.rule.registry.ExternalServiceNodeRegistry;
import com.personal.demo.rule.strategy.ExternalBaseNodeHandleStrategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 服务节点与节点处理器初始化
 * @date 2025/7/4 10:53
 */
public class ExternalServiceNodeInitializer {

    private ExternalServiceNodeInitializer() {
        throw new UnsupportedOperationException("禁止实例化工具类");
    }

    /**
     * 初始化节点
     */
    public static Map<ExternalBaseNode, ExternalBaseNodeHandleStrategy> initNodes(List<ExternalServiceNodeRegistry> configs) {
        Map<ExternalBaseNode, ExternalBaseNodeHandleStrategy> allNodeMap = new HashMap<>();
        configs.stream().map(ExternalServiceNodeRegistry::getServiceNodes).forEach(allNodeMap::putAll);
        return allNodeMap;


    }
}
