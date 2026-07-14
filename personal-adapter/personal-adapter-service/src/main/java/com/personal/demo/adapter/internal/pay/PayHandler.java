package com.personal.demo.adapter.internal.pay;


import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.enu.internal.node.PayNode;
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
public class PayHandler extends AbstractCategoryServiceHandler {

    private final PayFlowProcessor flowProcessor;

    public PayHandler(PayFlowProcessor flowProcessor) {
        this.flowProcessor = flowProcessor;
    }

    @Override
    protected Map<BaseNode, SearchFunction> loadFlow() {
        return Arrays.stream(PayNode.values())
                .collect(Collectors.toMap(
                        node -> node,
                        node -> (service, params) -> flowProcessor.execute(node, (BaseParams) params)
                ));
    }
}