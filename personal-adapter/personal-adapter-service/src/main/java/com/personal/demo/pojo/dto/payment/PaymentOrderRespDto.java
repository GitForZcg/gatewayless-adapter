package com.personal.demo.pojo.dto.payment;

import com.personal.demo.pojo.base.VendorPublicParam;
import lombok.Data;

@Data
public class PaymentOrderRespDto implements VendorPublicParam {
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
     * data
     */
    private Object data;
}
