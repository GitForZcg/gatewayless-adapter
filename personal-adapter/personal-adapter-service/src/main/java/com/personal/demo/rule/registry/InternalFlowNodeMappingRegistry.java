package com.personal.demo.rule.registry;

import com.personal.demo.enu.ServiceType;
import com.personal.demo.enu.internal.base.BaseNode;

import java.util.Map;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 流程节点注册表
 * @date 2025/7/2 10:57
 */

public interface InternalFlowNodeMappingRegistry {

    /**
     * 获取节点配置
     */
    Map<ServiceType, Map<BaseNode, Class<?>>> getFlowMappingConfig();
}
