package com.personal.demo.rule.handler;

import com.personal.demo.enu.external.base.ExternalBaseNode;
import com.personal.demo.rule.flow.UnifiedExternalServiceFlowProcessor;
import com.personal.demo.rule.strategy.ExternalBaseNodeHandleStrategy;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 抽象顶级调用动作
 * @date 2025/7/4 10:52
 */
public abstract class AbstractExternalServiceNodeHandler implements ExternalBaseNodeHandleStrategy {

    // 函数式构造器
    public static ExternalBaseNodeHandleStrategy of(SearchFunction function) {
        return new AbstractExternalServiceNodeHandler() {
            @Override
            protected Object doHandle(UnifiedExternalServiceFlowProcessor<?> flow, Object params, ExternalBaseNode node) throws Exception {
                return function.apply(flow, params, node);
            }
        };
    }

    /**
     * 处理方法
     */
    @Override
    @SuppressWarnings("uncheck")
    public Object handle(UnifiedExternalServiceFlowProcessor<?> flow, Object params, ExternalBaseNode node) throws Exception {
        return doHandle(flow, params, node);
    }

    /**
     * 执行处理方法
     */
    protected abstract Object doHandle(UnifiedExternalServiceFlowProcessor<?> flow, Object params, ExternalBaseNode node) throws Exception;

    @FunctionalInterface
    public interface SearchFunction {
        Object apply(UnifiedExternalServiceFlowProcessor<?> flow, Object params, ExternalBaseNode node) throws Exception;
    }
}
