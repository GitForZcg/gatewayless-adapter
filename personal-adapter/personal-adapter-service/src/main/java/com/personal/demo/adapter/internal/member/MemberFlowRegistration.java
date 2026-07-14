package com.personal.demo.adapter.internal.member;

import com.personal.demo.adapter.internal.product.ProductFlow;
import com.personal.demo.enu.ServiceType;
import com.personal.demo.rule.registry.AbstractInternalServiceFlowRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 会员注册
 *
 * @Author: fxs
 * @Date: 2026/1/12 15:40
 */
@Component
public class MemberFlowRegistration extends AbstractInternalServiceFlowRegistration<ServiceType> {

    @Autowired
    public MemberFlowRegistration(ProductFlow flow) {
        super(ServiceType.MEMBER, flow);
    }
}
