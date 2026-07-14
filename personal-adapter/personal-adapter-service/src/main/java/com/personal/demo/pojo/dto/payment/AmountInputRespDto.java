package com.personal.demo.pojo.dto.payment;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AmountInputRespDto {

    /**
     * 币种
     */
    private String currency;

    /**
     * 金额
     */
    private BigDecimal amount;


    /**
     * 带币种金额
     */
    private String amountStr;
}
