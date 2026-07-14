package com.personal.demo.adapter.internal.trade;


import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.enu.internal.node.MemberNode;
import com.personal.demo.enu.internal.node.TradeNode;
import com.personal.demo.request.base.BaseParams;
import com.personal.demo.rule.handler.AbstractCategoryServiceHandler;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * trade处理器
 *
 * @Author:
 * @Date:
 */
@SuppressWarnings("rawtypes")
@Component
public class TradeHandler extends AbstractCategoryServiceHandler {

    private final TradeFlowProcessor flowProcessor;

    public TradeHandler(TradeFlowProcessor flowProcessor) {
        this.flowProcessor = flowProcessor;
    }

    @Override
    protected Map<BaseNode, SearchFunction> loadFlow() {
        return Arrays.stream(TradeNode.values())
                .collect(Collectors.toMap(
                        node -> node,
                        node -> (service, params) -> flowProcessor.execute(node, (BaseParams) params)
                ));
    }
}
