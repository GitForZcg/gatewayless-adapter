package com.personal.demo.adapter.internal.product;


import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.enu.internal.node.ProdNode;
import com.personal.demo.enu.internal.node.StoreNode;
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
public class ProductHandler extends AbstractCategoryServiceHandler {

    private final ProductFlowProcessor flowProcessor;

    public ProductHandler(ProductFlowProcessor flowProcessor) {
        this.flowProcessor = flowProcessor;
    }

    @Override
    protected Map<BaseNode, SearchFunction> loadFlow() {
        return Arrays.stream(ProdNode.values())
                .collect(Collectors.toMap(
                        node -> node,
                        node -> (service, params) -> flowProcessor.execute(node, (BaseParams) params)
                ));
    }
}
