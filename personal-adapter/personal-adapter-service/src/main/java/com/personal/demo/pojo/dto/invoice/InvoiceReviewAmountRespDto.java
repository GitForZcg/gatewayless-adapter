package com.personal.demo.pojo.dto.invoice;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/9/16 09:27
 */
@Data
@Accessors(chain = true)
public class InvoiceReviewAmountRespDto {

    private String orderNum;
    /**
     * 可开票金额，默认值：0.00
     */
    private BigDecimal invoicePrice;
    /**
     * 剩余可开票金额，默认值：0.00
     */
    private BigDecimal remainInvoicePrice;
    /**
     * 不可开票金额，默认值：0.00
     */
    private BigDecimal noInvoicePrice;
    /**
     * 原始可开票金额
     */
    private BigDecimal originInvoicePrice;
    /**
     * Integer	响应标识：1-订单不存在，2-订单额度已开完
     */
    private int respFlag;

}
