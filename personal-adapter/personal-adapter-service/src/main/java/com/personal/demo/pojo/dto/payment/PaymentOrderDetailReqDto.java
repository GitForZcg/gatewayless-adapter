package com.personal.demo.pojo.dto.payment;

import lombok.Data;

@Data
public class PaymentOrderDetailReqDto {
    /**
     * 单据号
     */
    private String formCode;

    /**
     *是否需要费用列表
     */
    private Boolean enableExpenseList;

    /**
     *是否需要发票列表（受enableExpenseList控制）
     */
    private Boolean enableInvoiceList;


}
