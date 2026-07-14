package com.personal.demo.adapter.internal.invoice;

import com.personal.demo.enu.ServiceType;
import com.personal.demo.rule.registry.AbstractInternalServiceFlowRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 支付流程注册
 * @date 2025/7/2 10:45
 */
@Component
public class InvoiceFlowRegistration extends AbstractInternalServiceFlowRegistration<ServiceType> {
    @Autowired
    public InvoiceFlowRegistration(InvoiceFlow flow) {
        super(ServiceType.INVOICE, flow);
    }
}