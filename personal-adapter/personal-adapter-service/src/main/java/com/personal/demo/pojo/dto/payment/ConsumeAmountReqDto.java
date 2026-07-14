package com.personal.demo.pojo.dto.payment;

import lombok.Data;

@Data
public class ConsumeAmountReqDto {

    /**
     *货币编码
     */
    private String currency;

    /**
     *金额
     */
    private String amount;
}
