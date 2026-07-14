package com.personal.demo.adapter.internal.order;

import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.enu.internal.node.OrderNode;
import com.personal.demo.request.base.BaseParams;
import com.personal.demo.request.order.PushOrderParams;
import com.personal.demo.request.order.TestOrderParams;
import com.personal.demo.rule.handler.AbstractFlowProcessor;
import com.personal.demo.serivce.IOrderService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 订单执行流程
 * @date 2025/7/2 10:43
 */
@SuppressWarnings("rawtypes")
@Component
public class OrderFlow extends AbstractFlowProcessor<BaseParams> implements OrderFlowProcessor {

    @Resource
    IOrderService orderService;

    @Override
    public String getProcessorType() {
        return "ORDER";
    }

    @Override
    protected Map<BaseNode, BaseNodeFunction<BaseParams>> initNodeHandlers() {
        return Map.of(
                OrderNode.ORDER_QUERY, this::query,
                 OrderNode.ORDER_PUSH, this::pushOrder,
                OrderNode.ORDER_PUSH_OFFSET, this::pushOffSetOrder


        );
    }

    @Override
    public Object query(BaseParams params, BaseNode node) throws Exception {
        return orderService.query((TestOrderParams) params.getBizData(), node);
    }

    @Override
    public Object pushOrder(BaseParams params, BaseNode node) throws Exception {
        return orderService.pushOrder((PushOrderParams) params.getBizData(), node);
    }
    @Override
    public Object pushOffSetOrder(BaseParams params, BaseNode node) throws Exception {
        return orderService.pushOffSetOrder((PushOrderParams) params.getBizData(), node);
    }
}
