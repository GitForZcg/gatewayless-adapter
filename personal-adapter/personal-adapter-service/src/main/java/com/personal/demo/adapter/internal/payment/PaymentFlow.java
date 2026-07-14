package com.personal.demo.adapter.internal.payment;

import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.enu.internal.node.PaymentNode;
import com.personal.demo.request.base.BaseParams;
import com.personal.demo.request.payment.ImportPaymentClassificationParams;
import com.personal.demo.request.payment.ImportPaymentOrderParams;
import com.personal.demo.request.payment.PaymentOrderDetailParams;
import com.personal.demo.rule.handler.AbstractFlowProcessor;
import com.personal.demo.serivce.IPaymentService;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 付款单执行流程
 */
@SuppressWarnings("rawtypes")
@Component
public class PaymentFlow extends AbstractFlowProcessor<BaseParams> implements PaymentFlowProcessor {

    private final IPaymentService paymentService;

    public PaymentFlow(IPaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Override
    public String getProcessorType() {
        return "PAYMENT";
    }

    @Override
    protected Map<BaseNode, BaseNodeFunction<BaseParams>> initNodeHandlers() {
        return Map.of(
                PaymentNode.PAYMENT_IMPORT_FEE_DETAILS, this::importClassification,
                PaymentNode.PAYMENT_IMPORT_ORDER, this::importPaymentOrder,
                PaymentNode.PAYMENT_QUERY_ORDER, this::queryPaymentOrderDetail
        );
    }

    @Override
    public Object importClassification(BaseParams params, BaseNode node) throws Exception {
        return paymentService.importClassification((ImportPaymentClassificationParams) params.getBizData(), node);
    }

    @Override
    public Object importPaymentOrder(BaseParams params, BaseNode node) throws Exception {
        return paymentService.importPaymentOrder((ImportPaymentOrderParams) params.getBizData(), node);
    }

    @Override
    public Object queryPaymentOrderDetail(BaseParams params, BaseNode node) throws Exception {
        return paymentService.queryPaymentOrderDetail((PaymentOrderDetailParams) params.getBizData(), node);
    }
}
