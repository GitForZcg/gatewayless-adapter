package com.personal.demo.adapter.internal.product;

import com.personal.demo.enu.internal.node.ProdNode;
import com.personal.demo.enu.internal.node.StoreNode;
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
public class ProductGroupRegistration extends AbstractValidationGroupRegistry<ProdNode> {

    public ProductGroupRegistration() {
        super(ProdNode.class, ValidationGroups.productGroup.class);
    }

}
