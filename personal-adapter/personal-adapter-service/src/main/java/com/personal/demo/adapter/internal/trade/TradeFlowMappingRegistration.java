package com.personal.demo.adapter.internal.trade;

import com.personal.demo.enu.ServiceType;
import com.personal.demo.rule.db.InternalFlowMappingDbProcessor;
import com.personal.demo.rule.registry.AbstractInternalFlowMappingRegistration;
import org.springframework.stereotype.Component;

/**
 * trade流程映射注册
 *
 * @Author:
 * @Date:
 */
@Component
public class TradeFlowMappingRegistration extends AbstractInternalFlowMappingRegistration<ServiceType> {
    public TradeFlowMappingRegistration(InternalFlowMappingDbProcessor dbProcessor) {
        super(ServiceType.TRADE, dbProcessor);
    }
}
