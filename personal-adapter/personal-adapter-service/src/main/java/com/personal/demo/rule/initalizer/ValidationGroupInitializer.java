package com.personal.demo.rule.initalizer;


import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.enu.external.base.ExternalBaseNode;
import com.personal.demo.rule.registry.ValidationExternalGroupRegistry;
import com.personal.demo.rule.registry.ValidationGroupRegistry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 验证器组初始化
 * @date 2025/7/2 10:55
 */
public class ValidationGroupInitializer {

    private ValidationGroupInitializer() {
        throw new UnsupportedOperationException("禁止实例化工具类");
    }

    /**
     * 通过配置列表初始化所有验证组映射
     */
    public static Map<BaseNode, Class<?>> initGroups(List<ValidationGroupRegistry> configs) {
        Map<BaseNode, Class<?>> allGroups = new HashMap<>();
        configs.stream().map(ValidationGroupRegistry::getNodeValidationGroups).forEach(allGroups::putAll);
        return allGroups;
    }


    /**
     * 初始化所有外部api验证组映射
     */
    public static Map<ExternalBaseNode, Class<?>> initExternalApiGroups(List<ValidationExternalGroupRegistry> configs) {
        Map<ExternalBaseNode, Class<?>> allGroups = new HashMap<>();
        configs.stream().map(ValidationExternalGroupRegistry::getNodeValidationGroups).forEach(allGroups::putAll);
        return allGroups;
    }

}
