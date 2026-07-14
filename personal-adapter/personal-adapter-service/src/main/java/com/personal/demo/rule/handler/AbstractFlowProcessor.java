package com.personal.demo.rule.handler;

import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.request.base.AbstractBaseParams;
import com.personal.demo.rule.flow.UnifiedInternalServiceFlowProcessor;
import com.common.base.exception.BizException;

import java.util.Map;
import java.util.Set;

import static com.personal.demo.consts.BizCode.NODE_NOT_SUPPORTED;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 抽象流程处理器
 * @date 2025/7/2 10:52
 */
public abstract class AbstractFlowProcessor<T extends AbstractBaseParams> implements UnifiedInternalServiceFlowProcessor<T> {
    private final Map<BaseNode, BaseNodeFunction<T>> nodeHandlers;

    protected AbstractFlowProcessor() {
        this.nodeHandlers = initNodeHandlers();
    }

    /**
     * 子类实现：初始化节点处理器映射
     */
    protected abstract Map<BaseNode, BaseNodeFunction<T>> initNodeHandlers();

    @Override
    public Object execute(BaseNode node, T params) throws Exception {
        BaseNodeFunction<T> handler = nodeHandlers.get(node);
        if (handler == null) {
            throw new BizException(NODE_NOT_SUPPORTED,
                    String.format("流程处理器[%s]不支持节点[%s]", getProcessorType(), node.name()));
        }
        return handler.apply(params, node);
    }

    /**
     * 获取已被支持的节点
     */
    @Override
    public Set<BaseNode> getSupportedNodes() {
        return nodeHandlers.keySet();
    }

    @FunctionalInterface
    protected interface BaseNodeFunction<T> {
        Object apply(T params, BaseNode node) throws Exception;
    }
}
