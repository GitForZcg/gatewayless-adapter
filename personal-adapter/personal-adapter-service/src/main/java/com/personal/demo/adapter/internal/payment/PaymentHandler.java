package com.personal.demo.adapter.internal.payment;

import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.enu.internal.node.FinanceNode;
import com.personal.demo.enu.internal.node.PaymentNode;
import com.personal.demo.request.base.BaseParams;
import com.personal.demo.rule.handler.AbstractCategoryServiceHandler;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 付款单处理器
 */
@SuppressWarnings("rawtypes")
@Component
public class PaymentHandler extends AbstractCategoryServiceHandler {

    private final PaymentFlowProcessor flowProcessor;

    public PaymentHandler(PaymentFlowProcessor flowProcessor) {
        this.flowProcessor = flowProcessor;
    }

    @Override
    protected Map<BaseNode, SearchFunction> loadFlow() {
        return Arrays.stream(PaymentNode.values())
                .collect(Collectors.toMap(
                        node -> node,
                        node -> (service, params) -> flowProcessor.execute(node, (BaseParams) params)
                ));
    }
}