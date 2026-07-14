package com.personal.demo.rule.handler;

import com.personal.demo.enu.external.base.ExternalBaseNode;
import com.personal.demo.rule.flow.UnifiedExternalServiceFlowProcessor;
import com.common.base.exception.BizException;

import java.util.Map;
import java.util.Set;

import static com.personal.demo.consts.BizCode.NODE_NOT_SUPPORTED;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 抽象流程处理器
 * @date 2025/7/4 10:52
 */
public abstract class AbstractExternalFlowProcessor<T> implements UnifiedExternalServiceFlowProcessor<T> {
    private final Map<ExternalBaseNode, BaseNodeFunction<T>> nodeHandlers;

    protected AbstractExternalFlowProcessor() {
        this.nodeHandlers = initNodeHandlers();
    }

    /**
     * 子类实现：初始化节点处理器映射
     */
    protected abstract Map<ExternalBaseNode, BaseNodeFunction<T>> initNodeHandlers();

    @Override
    public Object execute(ExternalBaseNode node, T params) throws Exception {
        BaseNodeFunction<T> handler = nodeHandlers.get(node);
        if (handler == null) {
            throw new BizException(NODE_NOT_SUPPORTED,
                    String.format("流程处理器[%s]不支持节点[%s]", getProcessorType(), node.name()));
        }
        return handler.apply(params);
    }

    @Override
    public Set<ExternalBaseNode> getSupportedNodes() {
        return nodeHandlers.keySet();
    }

    @FunctionalInterface
    protected interface BaseNodeFunction<T> {
        Object apply(T params) throws Exception;
    }
}
