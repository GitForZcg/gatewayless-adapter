package com.personal.demo.rule.registry;

import com.personal.demo.enu.external.base.ExternalBaseNode;

import java.util.Map;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 外部验证组注册表
 * @date 2025/7/4 10:58
 */
public interface ValidationExternalGroupRegistry {

    /**
     * 获取验证组
     */
    Map<ExternalBaseNode, Class<?>> getNodeValidationGroups();
}
