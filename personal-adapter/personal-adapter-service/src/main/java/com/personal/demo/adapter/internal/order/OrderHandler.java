package com.personal.demo.adapter.internal.order;


import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.enu.internal.node.OrderNode;
import com.personal.demo.request.base.BaseParams;
import com.personal.demo.rule.handler.AbstractCategoryServiceHandler;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 订单处理器
 * @date 2025/7/2 10:44
 */
@SuppressWarnings("rawtypes")
@Component
public class OrderHandler extends AbstractCategoryServiceHandler {

    private final OrderFlowProcessor flowProcessor;

    public OrderHandler(OrderFlowProcessor flowProcessor) {
        this.flowProcessor = flowProcessor;
    }

    @Override
    protected Map<BaseNode, SearchFunction> loadFlow() {

        return Arrays.stream(OrderNode.values())
                .collect(Collectors.toMap(
                        node -> node,
                        node -> (service, params) -> flowProcessor.execute(node, (BaseParams) params)
                ));
    }
}