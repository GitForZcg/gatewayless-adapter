package com.personal.demo.adapter.internal.finance;

import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.enu.internal.node.FinanceNode;
import com.personal.demo.request.base.BaseParams;
import com.personal.demo.rule.handler.AbstractCategoryServiceHandler;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

/**
 * 财务处理器
 * @date: 2025/9/10 16:06 
 */
@SuppressWarnings("rawtypes")
@Component
public class FinanceHandler extends AbstractCategoryServiceHandler {

    private final FinanceFlowProcessor flowProcessor;

    public FinanceHandler(FinanceFlowProcessor flowProcessor) {
        this.flowProcessor = flowProcessor;
    }

    @Override
    protected Map<BaseNode, SearchFunction> loadFlow() {
        return Arrays.stream(FinanceNode.values())
                .collect(Collectors.toMap(
                        node -> node,
                        node -> (service, params) -> flowProcessor.execute(node, (BaseParams) params)
                ));
    }
}