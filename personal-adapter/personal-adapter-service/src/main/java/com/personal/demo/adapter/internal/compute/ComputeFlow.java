package com.personal.demo.adapter.internal.compute;

import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.enu.internal.node.ComputeNode;
import com.personal.demo.request.base.BaseParams;
import com.personal.demo.request.compute.CartPriceRequest;
import com.personal.demo.request.compute.PreOrderPriceRequest;
import com.personal.demo.request.compute.StorePromotionRequest;
import com.personal.demo.rule.handler.AbstractFlowProcessor;
import com.personal.demo.serivce.IComputeService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 订单执行流程
 * @date 2025/7/2 10:43
 */
@SuppressWarnings("rawtypes")
@Component
public class ComputeFlow extends AbstractFlowProcessor<BaseParams> implements ComputeFlowProcessor {

    @Resource
    IComputeService computeService;

    @Override
    public String getProcessorType() {
        return "COMPUTE";
    }

    @Override
    protected Map<BaseNode, BaseNodeFunction<BaseParams>> initNodeHandlers() {
        return Map.of(
                ComputeNode.COMPUTE_PROMOTION, this::promotionCompute,
                ComputeNode.COMPUTE_CART_PRICE, this::cartPriceCompute,
                ComputeNode.COMPUTE_MEMBER_ASSETS, this::memberAssets,
                ComputeNode.COMPUTE_PRE_ORDER_PRICE, this::preOrderPrice,
                ComputeNode.COMPUTE_POS_MEMBER_ASSETS, this::memberPosAssets
                // 这里可以添加更多的节点处理逻辑


        );

    }


    @Override
    public Object promotionCompute(BaseParams params, BaseNode node) throws Exception {
        Object result = computeService.promotionCompute((StorePromotionRequest)params.getBizData(), node);
        return result;
    }
    @Override
    public Object cartPriceCompute(BaseParams params, BaseNode node) throws Exception {
        Object result = computeService.cartPriceCompute((CartPriceRequest)params.getBizData(), node);
        return result;
    }

    @Override
    public Object preOrderPrice(BaseParams params, BaseNode node) throws Exception {
        Object result = computeService.preOrderPrice((PreOrderPriceRequest)params.getBizData(), node);
        return result;
    }

    private Object memberAssets(BaseParams baseParams, BaseNode baseNode) {
        return null;
    }


    private Object memberPosAssets(BaseParams baseParams, BaseNode baseNode) {return null;
    }


}
