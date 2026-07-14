package com.personal.demo.adapter.internal.product;

import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.enu.internal.node.ProdNode;
import com.personal.demo.enu.internal.node.StoreNode;
import com.personal.demo.request.base.BaseParams;
import com.personal.demo.request.product.ProductNotificationParam;
import com.personal.demo.request.store.TestStoreParams;
import com.personal.demo.rule.handler.AbstractFlowProcessor;
import com.personal.demo.serivce.IInternalProductService;
import com.personal.demo.serivce.IStoreService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 门店执行流程
 * @date 2025/7/2 10:44
 */
@SuppressWarnings("rawtypes")
@Component
public class ProductFlow extends AbstractFlowProcessor<BaseParams> implements ProductFlowProcessor {

    @Resource
    private IInternalProductService productService;


    @Override
    public String getProcessorType() {
        return "PRODUCT";
    }

    @Override
    protected Map<BaseNode, BaseNodeFunction<BaseParams>> initNodeHandlers() {
        return Map.of(
                ProdNode.PRODUCT_NOTIFICATION, this::notification
        );
    }


    @Override
    public Object notification(BaseParams params, BaseNode node) throws Exception {
        return productService.notification((ProductNotificationParam) params.getBizData(), node);
    }
}
