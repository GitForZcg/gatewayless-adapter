package com.personal.demo.rule.handler;

import com.personal.demo.enu.external.base.ExternalBaseNode;
import com.personal.demo.rule.flow.UnifiedExternalServiceFlowProcessor;
import com.common.base.exception.BizException;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Map;

import static com.personal.demo.consts.BizCode.NODE_NOT_SUPPORTED;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 外部抽象分类处理器
 * @date 2025/7/7 10:52
 */
public abstract class AbstractExternalCategoryServiceHandler extends AbstractExternalServiceNodeHandler {


    private final Map<ExternalBaseNode, SearchFunction> loadFlowMap;

    protected AbstractExternalCategoryServiceHandler() {
        this.loadFlowMap = loadFlow();
    }

    /**
     * 子类实现：定义操作映射
     */
    protected abstract Map<ExternalBaseNode, SearchFunction> loadFlow();

    @Override
    protected final Object doHandle(UnifiedExternalServiceFlowProcessor<?> flow, Object params, ExternalBaseNode node) throws Exception {
        SearchFunction function = loadFlowMap.get(node);
        if (ObjectUtils.isEmpty(function)) {
            throw new BizException(NODE_NOT_SUPPORTED, "不支持的操作: " + node);
        }
        return function.apply(flow, params);
    }

    @FunctionalInterface
    protected interface SearchFunction {
        Object apply(UnifiedExternalServiceFlowProcessor<?> flow, Object params) throws Exception;
    }
}
