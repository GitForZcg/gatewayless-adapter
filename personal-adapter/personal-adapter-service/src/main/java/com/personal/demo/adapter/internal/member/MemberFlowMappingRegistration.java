package com.personal.demo.adapter.internal.member;

import com.personal.demo.enu.ServiceType;
import com.personal.demo.rule.db.InternalFlowMappingDbProcessor;
import com.personal.demo.rule.registry.AbstractInternalFlowMappingRegistration;
import org.springframework.stereotype.Component;

/**
 * 会员流程映射注册
 *
 * @Author: fxs
 * @Date: 2026/1/12 15:37
 */
@Component
public class MemberFlowMappingRegistration  extends AbstractInternalFlowMappingRegistration<ServiceType> {
    public MemberFlowMappingRegistration(InternalFlowMappingDbProcessor dbProcessor) {
        super(ServiceType.MEMBER, dbProcessor);
    }
}
