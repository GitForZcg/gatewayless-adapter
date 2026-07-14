package com.personal.demo.response.invoice;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/9/16 09:32
 */
@Data
@Accessors(chain = true)
public class InvoiceReviewAmountResultDto {

    /**
     * 开票金额
     */
    private BigDecimal invoicePrice;
    /**
     * 可开票额度
     */
    private BigDecimal remainInvoicePrice;
    /**
     * 开票订单号
     */
    private String orderNo;

}
