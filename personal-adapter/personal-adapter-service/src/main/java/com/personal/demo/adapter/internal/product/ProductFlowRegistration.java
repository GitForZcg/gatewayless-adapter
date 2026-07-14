package com.personal.demo.adapter.internal.product;

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
public class ProductFlowRegistration extends AbstractInternalServiceFlowRegistration<ServiceType> {
    @Autowired
    public ProductFlowRegistration(ProductFlow flow) {
        super(ServiceType.PRODUCT, flow);
    }
}
