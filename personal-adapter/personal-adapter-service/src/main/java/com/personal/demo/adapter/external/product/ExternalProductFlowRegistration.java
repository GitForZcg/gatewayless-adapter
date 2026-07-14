package com.personal.demo.adapter.external.product;

import com.personal.demo.enu.external.ExternalApiNode;
import com.personal.demo.rule.registry.AbstractExternalServiceFlowRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 外部产品流程注册
 * @date 2025/7/4 10:42
 */
@Component
public class ExternalProductFlowRegistration extends AbstractExternalServiceFlowRegistration<ExternalApiNode> {
    @Autowired
    public ExternalProductFlowRegistration(ExternalProductFlow flow) {
        super(ExternalApiNode.PRODUCT, flow);
    }
}