package com.personal.demo.rule.registry;

import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.rule.strategy.BaseNodeHandleStrategy;

import java.util.Map;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 系统节点注册表
 * @date 2025/7/2 10:57
 */
public interface InternalServiceNodeRegistry {

    /**
     * 获取服务节点
     */
    Map<BaseNode, BaseNodeHandleStrategy>  getServiceNodes();
}
