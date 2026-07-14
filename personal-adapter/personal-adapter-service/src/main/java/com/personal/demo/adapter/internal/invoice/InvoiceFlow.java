package com.personal.demo.adapter.internal.invoice;

import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.enu.internal.node.InvoiceNode;
import com.personal.demo.request.base.BaseParams;
import com.personal.demo.request.invoice.*;
import com.personal.demo.rule.handler.AbstractFlowProcessor;
import com.personal.demo.serivce.IInvoiceService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 门店执行流程
 * @date 2025/7/2 10:44
 */
@SuppressWarnings("rawtypes")
@Component
public class InvoiceFlow extends AbstractFlowProcessor<BaseParams> implements InvoiceFlowProcessor {

    @Resource
    IInvoiceService invoiceService;

    @Override
    public String getProcessorType() {
        return "INVOICE";
    }

    @Override
    protected Map<BaseNode, BaseNodeFunction<BaseParams>> initNodeHandlers() {
        return Map.of(
                InvoiceNode.INVOICE_GET, this::invoiceGet,
                InvoiceNode.INVOICE_CANCEL, this::invoiceCancel,
                InvoiceNode.INVOICE_LIST, this::invoiceList,
                InvoiceNode.INVOICE_FINISH_LIST, this::invoiceFinishedList,
                InvoiceNode.INVOICE_TITLE_SEARCH, this::invoiceTitleSearch,
                InvoiceNode.INVOICE_REVIEW_AMOUNT, this::invoiceReviewAmount,
                InvoiceNode.INVOICE_PUSH_EMAIL, this::invoicePushEmail
        );
    }

    @Override
    public Object invoiceGet(BaseParams params, BaseNode node) throws Exception {
        return invoiceService.invoiceGet((InvoiceGetParams) params.getBizData(), node);
    }

    @Override
    public Object invoiceCancel(BaseParams params, BaseNode node) throws Exception {
        return invoiceService.invoiceCancel((InvoiceGetParams) params.getBizData(), node);
    }

    @Override
    public Object invoiceList(BaseParams params, BaseNode node) throws Exception {
        return invoiceService.invoiceList((InvoiceOrderInfoParams) params.getBizData(), node);
    }

    @Override
    public Object invoiceFinishedList(BaseParams params, BaseNode node) throws Exception {
        return invoiceService.invoiceFinishedList((InvoiceFinishedParams) params.getBizData(), node);
    }

    @Override
    public Object invoiceTitleSearch(BaseParams params, BaseNode node) throws Exception {
        return invoiceService.invoiceTitleSearch((InvoiceTitleSearchParams) params.getBizData(), node);
    }

    @Override
    public Object invoiceReviewAmount(BaseParams params, BaseNode node) throws Exception {
        return invoiceService.invoiceReviewAmount((InvoiceReviewAmountParams) params.getBizData(), node);
    }

    @Override
    public Object invoicePushEmail(BaseParams params, BaseNode node) throws Exception {
        return invoiceService.invoicePushEmail((InvoicePushEmailParams) params.getBizData(), node);
    }
}
