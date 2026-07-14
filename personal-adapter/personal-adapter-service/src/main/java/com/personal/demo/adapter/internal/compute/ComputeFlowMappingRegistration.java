package com.personal.demo.adapter.internal.compute;

import com.personal.demo.enu.ServiceType;
import com.personal.demo.rule.db.InternalFlowMappingDbProcessor;
import com.personal.demo.rule.registry.AbstractInternalFlowMappingRegistration;
import org.springframework.stereotype.Component;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 订单流程映射注册
 * @date 2025/7/2 10:43
 */
@Component
public class ComputeFlowMappingRegistration extends AbstractInternalFlowMappingRegistration<ServiceType> {

    public ComputeFlowMappingRegistration(InternalFlowMappingDbProcessor dbProcessor) {
        super(ServiceType.COMPUTE, dbProcessor);
    }
}
