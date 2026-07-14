package com.personal.demo.adapter.internal.trade;


import com.personal.demo.enu.internal.node.TradeNode;
import com.personal.demo.rule.registry.AbstractInternalServiceNodeRegistry;
import org.springframework.stereotype.Component;

/**
 * trade初始化
 *
 * @Author:
 * @Date:
 */
@Component
public class TradeNodeRegistration extends AbstractInternalServiceNodeRegistry<TradeNode> {

    public TradeNodeRegistration(TradeHandler handler) {
        super(TradeNode.class, handler);
    }
}
