package com.personal.demo.adapter.external.product;

import com.personal.demo.enu.external.ExternalProductNode;
import com.personal.demo.request.group.ValidationGroups;
import com.personal.demo.rule.registry.AbstractValidationExternalGroupRegistry;
import org.springframework.stereotype.Component;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 外部产品组注册
 * @date 2025/7/4 10:42
 */
@Component
public class ExternalProductGroupRegistration extends AbstractValidationExternalGroupRegistry<ExternalProductNode> {
    public ExternalProductGroupRegistration() {
        super(ExternalProductNode.class, ValidationGroups.externalDemoProductGroup.class);
    }
}
