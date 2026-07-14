package com.personal.demo.adapter.internal.payment;

import com.personal.demo.adapter.internal.finance.FinanceFlow;
import com.personal.demo.enu.ServiceType;
import com.personal.demo.rule.flow.UnifiedInternalServiceFlowProcessor;
import com.personal.demo.rule.registry.AbstractInternalServiceFlowRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 付款单流程注册
 */
@Component
public class PaymentFlowRegistration extends AbstractInternalServiceFlowRegistration<ServiceType> {
    @Autowired
    public PaymentFlowRegistration(FinanceFlow flow) {
        super(ServiceType.PAYMENT, flow);
    }

    protected PaymentFlowRegistration(ServiceType serviceType, UnifiedInternalServiceFlowProcessor<?> flow) {
        super(serviceType, flow);
    }
}