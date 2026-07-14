package com.personal.demo.adapter.internal.pay;

import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.enu.internal.node.PayNode;
import com.personal.demo.request.base.BaseParams;
import com.personal.demo.request.pay.PayPrepareRequest;
import com.personal.demo.request.pay.PayQueryRequest;
import com.personal.demo.request.pay.PayRefundQueryRequest;
import com.personal.demo.request.pay.PayRefundRequest;
import com.personal.demo.rule.handler.AbstractFlowProcessor;
import com.personal.demo.serivce.IPayService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 支付执行流程
 * @date 2025/7/2 10:44
 */
@SuppressWarnings("rawtypes")
@Component
public class PayFlow extends AbstractFlowProcessor<BaseParams> implements PayFlowProcessor {

    @Resource
    IPayService payService;

    @Override
    public String getProcessorType() {
        return "PAY";
    }

    @Override
    protected Map<BaseNode, BaseNodeFunction<BaseParams>> initNodeHandlers() {
        return Map.of(
                PayNode.PAY_PREPARE, this::payPrepare,
                PayNode.PAY_QUERY, this::payQuery,
                PayNode.PAY_REFUND, this::refund,
                PayNode.PAY_REFUND_QUERY, this::refundQuery
        );
    }

    @Override
    public Object payPrepare(BaseParams params, BaseNode node) throws Exception {
        return payService.payPrepare((PayPrepareRequest) params.getBizData(),node);
    }

    @Override
    public Object payQuery(BaseParams params, BaseNode node) throws Exception {
        return payService.payQuery((PayQueryRequest) params.getBizData(),node);
    }

    @Override
    public Object refund(BaseParams params, BaseNode node) throws Exception {
        return payService.refund((PayRefundRequest) params.getBizData(),node);
    }

    @Override
    public Object refundQuery(BaseParams params, BaseNode node) throws Exception {
        return payService.refundQuery((PayRefundQueryRequest) params.getBizData(),node);
    }
}
