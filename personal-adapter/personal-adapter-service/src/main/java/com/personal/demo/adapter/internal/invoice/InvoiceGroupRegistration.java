package com.personal.demo.adapter.internal.invoice;

import com.personal.demo.enu.internal.node.InvoiceNode;
import com.personal.demo.request.group.ValidationGroups;
import com.personal.demo.rule.registry.AbstractValidationGroupRegistry;
import org.springframework.stereotype.Component;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 支付验证组注册
 * @date 2025/7/2 10:45
 */
@Component
public class InvoiceGroupRegistration extends AbstractValidationGroupRegistry<InvoiceNode> {

    public InvoiceGroupRegistration() {
        super(InvoiceNode.class, ValidationGroups.invoiceGroup.class);
    }

}
