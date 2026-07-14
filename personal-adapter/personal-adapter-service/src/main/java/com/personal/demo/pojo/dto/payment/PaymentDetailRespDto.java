package com.personal.demo.pojo.dto.payment;

import com.personal.demo.pojo.base.VendorPublicParam;
import lombok.Data;

@Data
public class PaymentDetailRespDto implements VendorPublicParam {
    /**
     * code
     */
    private String code;

    /**
     * message
     */
    private String message;


    /**
     * data
     */
    private PaymentOrderDetailRespDto data;
}
