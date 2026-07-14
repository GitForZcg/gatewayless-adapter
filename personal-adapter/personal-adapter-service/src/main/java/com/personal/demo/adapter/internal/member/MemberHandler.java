package com.personal.demo.adapter.internal.member;


import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.enu.internal.node.MemberNode;
import com.personal.demo.request.base.BaseParams;
import com.personal.demo.rule.handler.AbstractCategoryServiceHandler;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 会员处理器
 *
 * @Author: fxs
 * @Date: 2026/1/12 15:40
 */
@SuppressWarnings("rawtypes")
@Component
public class MemberHandler extends AbstractCategoryServiceHandler {

    private final MemberFlowProcessor flowProcessor;

    public MemberHandler(MemberFlowProcessor flowProcessor) {
        this.flowProcessor = flowProcessor;
    }

    @Override
    protected Map<BaseNode, SearchFunction> loadFlow() {
        return Arrays.stream(MemberNode.values())
                .collect(Collectors.toMap(
                        node -> node,
                        node -> (service, params) -> flowProcessor.execute(node, (BaseParams) params)
                ));
    }
}
