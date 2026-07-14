package com.personal.demo.adapter.internal.invoice;


import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.enu.internal.node.InvoiceNode;
import com.personal.demo.request.base.BaseParams;
import com.personal.demo.rule.handler.AbstractCategoryServiceHandler;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 支付处理器
 * @date 2025/7/2 10:45
 */
@SuppressWarnings("rawtypes")
@Component
public class InvoiceHandler extends AbstractCategoryServiceHandler {

    private final InvoiceFlowProcessor flowProcessor;

    public InvoiceHandler(InvoiceFlowProcessor flowProcessor) {
        this.flowProcessor = flowProcessor;
    }

    @Override
    protected Map<BaseNode, SearchFunction> loadFlow() {
        return Arrays.stream(InvoiceNode.values())
                .collect(Collectors.toMap(
                        node -> node,
                        node -> (service, params) -> flowProcessor.execute(node, (BaseParams) params)
                ));
    }
}