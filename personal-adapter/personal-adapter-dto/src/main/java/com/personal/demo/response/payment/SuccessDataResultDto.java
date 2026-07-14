package com.personal.demo.response.payment;

import lombok.Data;

@Data
public class SuccessDataResultDto {
    /**
     * idx
     */
    private Long idx;

    /**
     * expenseCode
     */
    private String expenseCode;

    /**
     * integrity
     */
    private Boolean integrity;
}
