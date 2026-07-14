package com.personal.demo.response.payment;

import lombok.Data;

@Data
public class ErrorDataResultDto {

    /**
     * idx
     */
    private Long idx;

    /**
     * expenseCode
     */
    private String errorMessage;

    /**
     * integrity
     */
    private String errorCode;
}

