package com.personal.demo.adapter.internal.finance;

import com.personal.demo.enu.ServiceType;
import com.personal.demo.rule.registry.AbstractInternalServiceFlowRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 财务流程注册
 * @date: 2025/9/10 16:15 
 */
@Component
public class FinanceFlowRegistration extends AbstractInternalServiceFlowRegistration<ServiceType> {
    @Autowired
    public FinanceFlowRegistration(FinanceFlow flow) {
        super(ServiceType.FINANCE, flow);
    }
}