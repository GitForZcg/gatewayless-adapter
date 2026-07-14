package com.personal.demo.adapter.internal.finance;

import com.personal.demo.enu.internal.node.FinanceNode;
import com.personal.demo.rule.registry.AbstractInternalServiceNodeRegistry;
import org.springframework.stereotype.Component;

/**
 * 财务节点处理器初始化
 * @date: 2025/9/10 16:09 
 */
@Component
public class FinanceNodeRegistration extends AbstractInternalServiceNodeRegistry<FinanceNode> {

    public FinanceNodeRegistration(FinanceHandler handler) {
        super(FinanceNode.class, handler);
    }
}
