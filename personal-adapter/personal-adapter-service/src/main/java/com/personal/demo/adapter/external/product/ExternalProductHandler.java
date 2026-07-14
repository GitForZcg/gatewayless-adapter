package com.personal.demo.adapter.external.product;


import com.personal.demo.enu.external.ExternalProductNode;
import com.personal.demo.enu.external.base.ExternalBaseNode;
import com.personal.demo.rule.handler.AbstractExternalCategoryServiceHandler;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 外部产品处理器
 * @date 2025/7/4 10:42
 */
@Component
public class ExternalProductHandler extends AbstractExternalCategoryServiceHandler {

    private final ExternalProductFlowProcessor flowProcessor;

    public ExternalProductHandler(ExternalProductFlowProcessor flowProcessor) {
        this.flowProcessor = flowProcessor;
    }

    @Override
    protected Map<ExternalBaseNode, SearchFunction> loadFlow() {
        return Arrays.stream(ExternalProductNode.values())
                .collect(Collectors.toMap(
                        node -> node,
                        node -> (service, params) -> flowProcessor.execute(node, params)
                ));
    }
}