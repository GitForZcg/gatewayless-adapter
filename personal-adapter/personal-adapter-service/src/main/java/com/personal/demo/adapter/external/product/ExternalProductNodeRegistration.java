package com.personal.demo.adapter.external.product;

import com.personal.demo.enu.external.ExternalProductNode;
import com.personal.demo.rule.registry.AbstractExternalServiceNodeRegistry;
import org.springframework.stereotype.Component;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 外部处理器初始化
 * @date 2025/7/4 10:43
 */
@Component
public class ExternalProductNodeRegistration extends AbstractExternalServiceNodeRegistry<ExternalProductNode> {
    public ExternalProductNodeRegistration(ExternalProductHandler handler) {
        super(ExternalProductNode.class, handler);
    }
}
