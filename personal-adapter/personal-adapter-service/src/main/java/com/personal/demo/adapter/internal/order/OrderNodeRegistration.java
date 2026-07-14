package com.personal.demo.adapter.internal.order;

import com.personal.demo.enu.internal.node.OrderNode;
import com.personal.demo.rule.registry.AbstractInternalServiceNodeRegistry;
import org.springframework.stereotype.Component;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 处理器初始化
 * @date 2025/7/4 10:44
 */
@Component
public class OrderNodeRegistration extends AbstractInternalServiceNodeRegistry<OrderNode> {
    public OrderNodeRegistration(OrderHandler handler) {
        super(OrderNode.class, handler);
    }
}
