package com.personal.demo.adapter.internal.order;

import com.personal.demo.enu.ServiceType;
import com.personal.demo.rule.registry.AbstractInternalServiceFlowRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 订单流程注册
 * @date 2025/7/2 10:43
 */
@Component
public class OrderFlowRegistration extends AbstractInternalServiceFlowRegistration<ServiceType> {
    @Autowired
    public OrderFlowRegistration(OrderFlow flow) {
        super(ServiceType.ORDER, flow);
    }

}