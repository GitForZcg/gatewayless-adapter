package com.personal.demo.pojo.dto.payment;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TaxItemsRespDto {
    /**
     * 明细金额
     */
    private BigDecimal detailAmount;

    /**
     * 税率
     */
    private BigDecimal taxRate;


    /**
     * 抵扣税额
     */
    private BigDecimal approvedDeductionAmount;

    /**
     * 税额
     */
    private BigDecimal approvedTaxAmount;


    /**
     * 是否转出
     */
    private Boolean transferOut;


    /**
     * 未税金额
     */
    private BigDecimal unTaxAmount;

}
