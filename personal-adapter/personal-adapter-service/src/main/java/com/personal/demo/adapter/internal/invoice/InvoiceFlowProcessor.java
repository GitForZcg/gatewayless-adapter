package com.personal.demo.adapter.internal.invoice;

import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.request.base.BaseParams;
import com.personal.demo.rule.flow.UnifiedInternalServiceFlowProcessor;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 支付流程实现
 * @date 2025/7/2 10:45
 */
@SuppressWarnings("rawtypes")
public interface InvoiceFlowProcessor extends UnifiedInternalServiceFlowProcessor<BaseParams> {

    Object invoiceGet(BaseParams params, BaseNode node) throws Exception;

    Object invoiceCancel(BaseParams params, BaseNode node) throws Exception;

    Object invoiceList(BaseParams params, BaseNode node) throws Exception;

    Object invoiceFinishedList(BaseParams params, BaseNode node) throws Exception;

    Object invoiceTitleSearch(BaseParams params, BaseNode node) throws Exception;

    Object invoiceReviewAmount(BaseParams params, BaseNode node) throws Exception;

    Object invoicePushEmail(BaseParams params, BaseNode node) throws Exception;

}
