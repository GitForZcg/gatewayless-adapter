package com.personal.demo.response.payment;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaymentOrderDetailResultDto {

    /**
     * code
     */
    private String code;

    /**
     * message
     */
    private String message;

    /**
     * 单据内码
     */
    private String formDataCode;

    /**
     * 单据号
     */
    private String formCode;

    /**
     * 结转方式
     */
    private String settlementMethod;

    /**
     * 单据状态
     */
    private String formStatus;
    /**
     * 支付时间
     */
    private LocalDateTime settledAt;

    /**
     * 核销单据code
     */
    private String reimburseCode;



    //发票相关的

    /**
     * 费用类型编码
     */
    private String expenseTypeBizCode;

    /**
     * 费用类型名称
     */
    private String expenseTypeName;

    /**
     * 回票金额
     */
    private BigDecimal totalPriceAmount;

    /**
     * 税额
     */
    private BigDecimal totalTaxAmount;

    /**
     * 价税合计
     */
    private BigDecimal totalPriceAndTax;

    /**
     *回票日期
     */
    private LocalDateTime deductionDate;


}
