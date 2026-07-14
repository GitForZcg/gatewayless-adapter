package com.personal.demo.rule.registry;

import com.personal.demo.enu.external.base.ExternalBaseNode;
import com.personal.demo.rule.strategy.ExternalBaseNodeHandleStrategy;

import java.util.Map;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 系统节点注册表
 * @date 2025/7/4 10:57
 */
public interface ExternalServiceNodeRegistry {

    /**
     * 获取服务节点
     */
    Map<ExternalBaseNode, ExternalBaseNodeHandleStrategy> getServiceNodes();
}
