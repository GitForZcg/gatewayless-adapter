package com.personal.demo.serivce;

import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.request.invoice.*;
import com.personal.demo.response.invoice.*;

import java.util.List;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 发票服务处理接口
 * @date 2025/9/11 13:53
 */
public interface IInvoiceService {

    Boolean invoiceGet(InvoiceGetParams params, BaseNode node) throws Exception;

    Object invoiceCancel(InvoiceGetParams params, BaseNode node) throws Exception;

    InvoiceOrderInfoResultDto invoiceList(InvoiceOrderInfoParams params, BaseNode node) throws Exception;

    InvoiceFinishedResultDto invoiceFinishedList(InvoiceFinishedParams params, BaseNode node) throws Exception;

    List<InvoiceTitleDetailResultDto> invoiceTitleSearch(InvoiceTitleSearchParams params, BaseNode node) throws Exception;

    List<InvoiceReviewAmountResultDto> invoiceReviewAmount(InvoiceReviewAmountParams params, BaseNode node) throws Exception;

    Boolean invoicePushEmail(InvoicePushEmailParams params, BaseNode node) throws Exception;
}
