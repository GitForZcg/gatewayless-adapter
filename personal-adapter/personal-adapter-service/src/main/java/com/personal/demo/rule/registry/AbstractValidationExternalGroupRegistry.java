package com.personal.demo.rule.registry;

import com.personal.demo.enu.external.base.ExternalBaseNode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 外部服务节点验证组注册表
 * @date 2025/7/8 10:56
 */
public abstract class AbstractValidationExternalGroupRegistry<T extends Enum<T> & ExternalBaseNode> implements ValidationExternalGroupRegistry {

    private final Class<T> nodeClass;
    private final Class<?> validationGroup;

    protected AbstractValidationExternalGroupRegistry(Class<T> nodeClass, Class<?> validationGroup) {
        this.nodeClass = nodeClass;
        this.validationGroup = validationGroup;
    }

    /**
     * 抽象节点验证组
     */
    @Override
    public Map<ExternalBaseNode, Class<?>> getNodeValidationGroups() {
        T[] values = nodeClass.getEnumConstants();
        Map<ExternalBaseNode, Class<?>> map = new HashMap<>();
        for (T value : values) {
            map.put(value, validationGroup);
        }
        return map;
    }
}