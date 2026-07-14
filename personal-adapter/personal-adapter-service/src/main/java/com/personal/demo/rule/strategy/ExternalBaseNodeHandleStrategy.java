package com.personal.demo.rule.strategy;

import com.personal.demo.enu.external.base.ExternalBaseNode;
import com.personal.demo.rule.flow.UnifiedExternalServiceFlowProcessor;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 外部调用处理策略
 * @date 2025/7/4 10:59
 */
public interface ExternalBaseNodeHandleStrategy {

    /**
     * 执行调用动作
     *
     * @param flow   调用流程实例
     * @param params 调用参数
     * @return 处理结果
     */
    Object handle(UnifiedExternalServiceFlowProcessor<?> flow, Object params, ExternalBaseNode node) throws Exception;
}
