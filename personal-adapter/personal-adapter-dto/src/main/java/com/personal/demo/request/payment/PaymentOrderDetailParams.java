package com.personal.demo.request.payment;

import com.personal.demo.request.group.ValidationGroups;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class PaymentOrderDetailParams {
    /**
     * 单据号
     */
    @NotEmpty(message = "付款单单号不能为空", groups = ValidationGroups.paymentGroup.class)
    private String formCode;
}
