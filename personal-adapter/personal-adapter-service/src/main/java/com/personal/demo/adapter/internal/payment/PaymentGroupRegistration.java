package com.personal.demo.adapter.internal.payment;

import com.personal.demo.enu.internal.node.PaymentNode;
import com.personal.demo.request.group.ValidationGroups;
import com.personal.demo.rule.registry.AbstractValidationGroupRegistry;
import org.springframework.stereotype.Component;

/**
 * 付款单验证组注册
 */
@Component
public class PaymentGroupRegistration extends AbstractValidationGroupRegistry<PaymentNode> {

    public PaymentGroupRegistration() {
        super(PaymentNode.class, ValidationGroups.paymentGroup.class);
    }

}
