package com.personal.demo.rule.strategy;

import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.request.base.AbstractBaseParams;
import com.personal.demo.rule.flow.UnifiedInternalServiceFlowProcessor;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 顶级调用动作策略
 * @date 2025/7/2 10:58
 */
public interface BaseNodeHandleStrategy {

    /**
     * 执行调用动作
     *
     * @param flow   调用流程实例
     * @param params 调用参数
     * @return 处理结果
     */
    Object handle(UnifiedInternalServiceFlowProcessor<?> flow, AbstractBaseParams params, BaseNode node) throws Exception;
}
