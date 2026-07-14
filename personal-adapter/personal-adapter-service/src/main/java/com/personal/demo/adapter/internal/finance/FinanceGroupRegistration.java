package com.personal.demo.adapter.internal.finance;

import com.personal.demo.enu.internal.node.FinanceNode;
import com.personal.demo.request.group.ValidationGroups;
import com.personal.demo.rule.registry.AbstractValidationGroupRegistry;
import org.springframework.stereotype.Component;

/**
 * 财务验证组注册
 * @date: 2025/9/10 15:39 
 */
@Component
public class FinanceGroupRegistration extends AbstractValidationGroupRegistry<FinanceNode> {

    public FinanceGroupRegistration() {
        super(FinanceNode.class, ValidationGroups.financeGroup.class);
    }

}
