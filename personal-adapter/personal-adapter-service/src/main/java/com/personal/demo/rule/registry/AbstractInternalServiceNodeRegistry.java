package com.personal.demo.rule.registry;

import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.rule.strategy.BaseNodeHandleStrategy;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 抽象内部服务节点注册
 * @date 2025/7/8 10:56
 */
public abstract class AbstractInternalServiceNodeRegistry<T extends Enum<T> & BaseNode> implements InternalServiceNodeRegistry {

    private final Class<T> nodeClass;
    private final BaseNodeHandleStrategy handler;

    protected AbstractInternalServiceNodeRegistry(Class<T> nodeClass, BaseNodeHandleStrategy handler) {
        this.nodeClass = nodeClass;
        this.handler = handler;
    }

    /**
     * 抽象服务节点
     */
    @Override
    public Map<BaseNode, BaseNodeHandleStrategy> getServiceNodes() {
        T[] values = nodeClass.getEnumConstants();
        Map<BaseNode, BaseNodeHandleStrategy> map = new HashMap<>();
        for (T value : values) {
            map.put(value, handler);
        }
        return map;
    }
}
