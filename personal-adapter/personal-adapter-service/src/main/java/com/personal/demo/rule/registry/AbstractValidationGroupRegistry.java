package com.personal.demo.rule.registry;

import com.personal.demo.enu.internal.base.BaseNode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 节点验证组注册表
 * @date 2025/7/8 10:56
 */
public abstract class AbstractValidationGroupRegistry<T extends Enum<T> & BaseNode> implements ValidationGroupRegistry {

    private final Class<T> nodeClass;
    private final Class<?> validationGroup;

    protected AbstractValidationGroupRegistry(Class<T> nodeClass, Class<?> validationGroup) {
        this.nodeClass = nodeClass;
        this.validationGroup = validationGroup;
    }

    /**
     * 抽象节点验证组
     */
    @Override
    public Map<BaseNode, Class<?>> getNodeValidationGroups() {
        T[] values = nodeClass.getEnumConstants();
        Map<BaseNode, Class<?>> map = new HashMap<>();
        for (T value : values) {
            map.put(value, validationGroup);
        }
        return map;
    }
}