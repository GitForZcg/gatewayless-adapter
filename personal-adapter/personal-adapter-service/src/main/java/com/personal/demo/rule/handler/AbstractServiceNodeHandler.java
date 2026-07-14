package com.personal.demo.rule.handler;

import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.request.base.AbstractBaseParams;
import com.personal.demo.rule.flow.UnifiedInternalServiceFlowProcessor;
import com.personal.demo.rule.strategy.BaseNodeHandleStrategy;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 顶级调用动作管理器基类
 * @date 2025/7/2 10:53
 */
public abstract class AbstractServiceNodeHandler implements BaseNodeHandleStrategy {

    // 函数式构造器
    public static BaseNodeHandleStrategy of(SearchFunction function) {
        return new AbstractServiceNodeHandler() {
            @Override
            protected Object doHandle(UnifiedInternalServiceFlowProcessor<AbstractBaseParams> flow, AbstractBaseParams params, BaseNode node) throws Exception {
                return function.apply(flow, params, node);
            }
        };
    }

    /**
     * 处理方法
     */
    @Override
    @SuppressWarnings("unchecked")
    public Object handle(UnifiedInternalServiceFlowProcessor<?> flow, AbstractBaseParams params, BaseNode node) throws Exception {
        return doHandle((UnifiedInternalServiceFlowProcessor<AbstractBaseParams>) flow, params, node);
    }

    /**
     * 执行处理方法
     */
    protected abstract Object doHandle(UnifiedInternalServiceFlowProcessor<AbstractBaseParams> flow, AbstractBaseParams params, BaseNode node) throws Exception;

    @FunctionalInterface
    public interface SearchFunction {
        Object apply(UnifiedInternalServiceFlowProcessor<AbstractBaseParams> flow, AbstractBaseParams params, BaseNode node) throws Exception;
    }
}
