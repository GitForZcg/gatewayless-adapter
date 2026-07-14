package com.personal.demo.adapter.internal.trade;

import com.personal.demo.enu.internal.node.TradeNode;
import com.personal.demo.request.group.ValidationGroups;
import com.personal.demo.rule.registry.AbstractValidationGroupRegistry;
import org.springframework.stereotype.Component;


/**
 * trade验证组注册注册
 *
 * @Author:
 * @Date:
 */
@Component
public class TradeGroupRegistration extends AbstractValidationGroupRegistry<TradeNode> {

    public TradeGroupRegistration() {
        super(TradeNode.class, ValidationGroups.tradeGroup.class);
    }

}
