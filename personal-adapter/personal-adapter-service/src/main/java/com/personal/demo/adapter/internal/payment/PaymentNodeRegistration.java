package com.personal.demo.adapter.internal.payment;

import com.personal.demo.enu.internal.node.PaymentNode;
import com.personal.demo.rule.registry.AbstractInternalServiceNodeRegistry;
import org.springframework.stereotype.Component;

/**
 * 付款单节点处理器初始化
 */
@Component
public class PaymentNodeRegistration extends AbstractInternalServiceNodeRegistry<PaymentNode> {

    public PaymentNodeRegistration(PaymentHandler handler) {
        super(PaymentNode.class, handler);
    }
}
