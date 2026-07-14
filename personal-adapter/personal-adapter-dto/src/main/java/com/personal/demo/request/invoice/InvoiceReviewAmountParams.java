package com.personal.demo.request.invoice;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/9/15 18:03
 */
@Data
@Accessors(chain = true)
public class InvoiceReviewAmountParams {

    private List<InvoiceReviewAmountParam> invoiceOrderList;
}
