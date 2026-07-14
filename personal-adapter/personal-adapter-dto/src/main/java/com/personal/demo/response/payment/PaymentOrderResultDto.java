package com.personal.demo.response.payment;

import lombok.Data;

@Data
public class PaymentOrderResultDto {
    /**
     * code
     */
    private String code;

    /**
     * message
     */
    private String message;

    /**
     * success
     */
    private Boolean success;

    /**
     * successData
     */
    private String successData;

    /**
     * errorData
     */
    private ErrorPayOrderDataResultDto errorData;
}
