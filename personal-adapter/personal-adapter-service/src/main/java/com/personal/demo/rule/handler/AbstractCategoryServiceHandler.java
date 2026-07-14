package com.personal.demo.rule.handler;

import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.request.base.AbstractBaseParams;
import com.personal.demo.rule.flow.UnifiedInternalServiceFlowProcessor;
import com.common.base.exception.BizException;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Map;

import static com.personal.demo.consts.BizCode.NODE_NOT_SUPPORTED;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 抽象分类处理器基类
 * @date 2025/7/2 10:51
 */
public abstract class AbstractCategoryServiceHandler extends AbstractServiceNodeHandler {

    private final Map<BaseNode, SearchFunction> loadFlowMap;

    protected AbstractCategoryServiceHandler() {
        this.loadFlowMap = loadFlow();
    }

    /**
     * 子类实现：定义操作映射
     */
    protected abstract Map<BaseNode, SearchFunction> loadFlow();

    @Override
    protected final Object doHandle(UnifiedInternalServiceFlowProcessor<AbstractBaseParams> flow, AbstractBaseParams params, BaseNode node) throws Exception {
        SearchFunction function = loadFlowMap.get(node);
        if (ObjectUtils.isEmpty(function)) {
            throw new BizException(NODE_NOT_SUPPORTED, "不支持的操作: " + node);
        }
        return function.apply(flow, params);
    }

    @FunctionalInterface
    protected interface SearchFunction {
        Object apply(UnifiedInternalServiceFlowProcessor<AbstractBaseParams> flow, AbstractBaseParams params) throws Exception;
    }
}