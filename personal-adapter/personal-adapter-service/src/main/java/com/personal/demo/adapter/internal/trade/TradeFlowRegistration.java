package com.personal.demo.adapter.internal.trade;

import com.personal.demo.adapter.internal.product.ProductFlow;
import com.personal.demo.enu.ServiceType;
import com.personal.demo.rule.registry.AbstractInternalServiceFlowRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * trade注册
 *
 * @Author:
 * @Date:
 */
@Component
public class TradeFlowRegistration extends AbstractInternalServiceFlowRegistration<ServiceType> {

    @Autowired
    public TradeFlowRegistration(ProductFlow flow) {
        super(ServiceType.TRADE, flow);
    }
}
