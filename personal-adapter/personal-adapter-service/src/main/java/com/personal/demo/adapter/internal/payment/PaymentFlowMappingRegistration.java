package com.personal.demo.adapter.internal.payment;

import com.personal.demo.enu.ServiceType;
import com.personal.demo.rule.db.InternalFlowMappingDbProcessor;
import com.personal.demo.rule.registry.AbstractInternalFlowMappingRegistration;
import org.springframework.stereotype.Component;

/**
 * 付款单流程映射注册
 */
@Component
public class PaymentFlowMappingRegistration extends AbstractInternalFlowMappingRegistration<ServiceType> {

    public PaymentFlowMappingRegistration(InternalFlowMappingDbProcessor dbProcessor) {
        super(ServiceType.PAYMENT, dbProcessor);
    }
}
