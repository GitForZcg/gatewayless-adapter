package com.personal.demo.response.invoice;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/9/15 17:02
 */

@Data
@Accessors(chain = true)
public class InvoiceOrderInfoResult {

    private String orderNo;
    private String createdTime;
    private BigDecimal payableAmount;
    private String ownSellerNo;
    private int invoiceStatus;
    private String storeCode;
}
