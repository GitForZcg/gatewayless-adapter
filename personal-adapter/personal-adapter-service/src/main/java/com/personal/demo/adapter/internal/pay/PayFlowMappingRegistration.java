package com.personal.demo.adapter.internal.pay;

import com.personal.demo.enu.ServiceType;
import com.personal.demo.rule.db.InternalFlowMappingDbProcessor;
import com.personal.demo.rule.registry.AbstractInternalFlowMappingRegistration;
import org.springframework.stereotype.Component;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 支付流程映射注册
 * @date 2025/7/2 10:45
 */
@Component
public class PayFlowMappingRegistration extends AbstractInternalFlowMappingRegistration<ServiceType> {

    public PayFlowMappingRegistration(InternalFlowMappingDbProcessor dbProcessor) {
        super(ServiceType.PAY, dbProcessor);
    }
}
