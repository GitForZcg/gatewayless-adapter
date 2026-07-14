package com.personal.demo.adapter.internal.payment;

import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.request.base.BaseParams;
import com.personal.demo.rule.flow.UnifiedInternalServiceFlowProcessor;

/**
 * 付款单流程实现
 */
@SuppressWarnings("rawtypes")
public interface PaymentFlowProcessor extends UnifiedInternalServiceFlowProcessor<BaseParams> {

    /**
     * 导入财务分类
     */
    Object importClassification(BaseParams params, BaseNode node) throws Exception;

    /**
     * 导入付款单
     */
    Object importPaymentOrder(BaseParams params, BaseNode node) throws Exception;

    /**
     * 查询付款单详情
     */
    Object queryPaymentOrderDetail(BaseParams params, BaseNode node) throws Exception;


}
