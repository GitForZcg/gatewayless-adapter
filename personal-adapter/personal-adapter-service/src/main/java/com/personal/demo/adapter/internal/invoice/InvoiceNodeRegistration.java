package com.personal.demo.adapter.internal.invoice;

import com.personal.demo.enu.internal.node.InvoiceNode;
import com.personal.demo.rule.registry.AbstractInternalServiceNodeRegistry;
import org.springframework.stereotype.Component;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 支付器初始化
 * @date 2025/7/2 10:45
 */
@Component
public class InvoiceNodeRegistration extends AbstractInternalServiceNodeRegistry<InvoiceNode> {

    public InvoiceNodeRegistration(InvoiceHandler handler) {
        super(InvoiceNode.class, handler);
    }
}
