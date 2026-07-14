package com.personal.demo.adapter.internal.store;

import com.personal.demo.enu.internal.node.StoreNode;
import com.personal.demo.rule.registry.AbstractInternalServiceNodeRegistry;
import org.springframework.stereotype.Component;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 支付器初始化
 * @date 2025/7/2 10:45
 */
@Component
public class StoreNodeRegistration extends AbstractInternalServiceNodeRegistry<StoreNode> {

    public StoreNodeRegistration(StoreHandler handler) {
        super(StoreNode.class, handler);
    }
}
