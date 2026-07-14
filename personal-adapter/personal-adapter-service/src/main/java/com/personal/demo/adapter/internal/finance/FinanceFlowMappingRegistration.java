package com.personal.demo.adapter.internal.finance;

import com.personal.demo.enu.ServiceType;
import com.personal.demo.rule.db.InternalFlowMappingDbProcessor;
import com.personal.demo.rule.registry.AbstractInternalFlowMappingRegistration;
import org.springframework.stereotype.Component;

/**
 * 财务流程映射注册
 * @date: 2025/9/10 17:34 
 */
@Component
public class FinanceFlowMappingRegistration extends AbstractInternalFlowMappingRegistration<ServiceType> {

    public FinanceFlowMappingRegistration(InternalFlowMappingDbProcessor dbProcessor) {
        super(ServiceType.FINANCE, dbProcessor);
    }
}
