package com.personal.demo.adapter.internal.pay;

import com.personal.demo.enu.internal.node.PayNode;
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
public class PayGroupRegistration extends AbstractValidationGroupRegistry<PayNode> {

    public PayGroupRegistration() {
        super(PayNode.class, ValidationGroups.payGroup.class);
    }

}
