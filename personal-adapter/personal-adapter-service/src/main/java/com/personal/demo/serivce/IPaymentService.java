package com.personal.demo.serivce;

import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.request.payment.ImportPaymentClassificationParams;
import com.personal.demo.request.payment.ImportPaymentOrderParams;
import com.personal.demo.request.payment.PaymentOrderDetailParams;
import com.personal.demo.response.payment.PaymentClassificationResultDto;
import com.personal.demo.response.payment.PaymentOrderDetailResultDto;
import com.personal.demo.response.payment.PaymentOrderResultDto;

public interface IPaymentService {
    /**
     * 导入财务分类
     */
    PaymentClassificationResultDto importClassification(ImportPaymentClassificationParams params, BaseNode node) throws Exception;

    /**
     * 导入付款单
     */
    PaymentOrderResultDto importPaymentOrder(ImportPaymentOrderParams params, BaseNode node) throws Exception;

    /**
     * 查询付款单详情
     */
    PaymentOrderDetailResultDto queryPaymentOrderDetail(PaymentOrderDetailParams params, BaseNode node) throws Exception;
}
