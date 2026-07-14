package com.personal.demo.rule.registry;

import com.personal.demo.enu.internal.base.BaseNode;

import java.util.Map;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 验证组注册表
 * @date 2025/7/2 10:58
 */
public interface ValidationGroupRegistry {

    /**
     * 获取节点验证组映射
     */
    Map<BaseNode, Class<?>> getNodeValidationGroups();
}
