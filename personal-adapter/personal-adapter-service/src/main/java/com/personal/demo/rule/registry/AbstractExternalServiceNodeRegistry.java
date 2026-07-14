package com.personal.demo.rule.registry;

import com.personal.demo.enu.external.base.ExternalBaseNode;
import com.personal.demo.rule.strategy.ExternalBaseNodeHandleStrategy;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 外部服务节点注册表
 * @date 2025/7/8 10:55
 */
public abstract class AbstractExternalServiceNodeRegistry<T extends Enum<T> & ExternalBaseNode> implements ExternalServiceNodeRegistry {

    private final Class<T> nodeClass;
    private final ExternalBaseNodeHandleStrategy handler;

    protected AbstractExternalServiceNodeRegistry(Class<T> nodeClass, ExternalBaseNodeHandleStrategy handler) {
        this.nodeClass = nodeClass;
        this.handler = handler;
    }

    /**
     * 获取服务节点
     */
    @Override
    public Map<ExternalBaseNode, ExternalBaseNodeHandleStrategy> getServiceNodes() {
        T[] values = nodeClass.getEnumConstants();
        Map<ExternalBaseNode, ExternalBaseNodeHandleStrategy> map = new HashMap<>();
        for (T value : values) {
            map.put(value, handler);
        }
        return map;
    }
}