package com.personal.demo.serivce;

import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.request.base.BaseParams;
import com.personal.demo.request.compute.CartPriceRequest;
import com.personal.demo.request.compute.PreOrderPriceRequest;
import com.personal.demo.request.compute.StorePromotionRequest;

public interface IComputeService {

    Object promotionCompute(StorePromotionRequest params, BaseNode node) throws Exception;

    Object cartPriceCompute(CartPriceRequest params, BaseNode node) throws Exception;

    Object preOrderPrice(PreOrderPriceRequest params, BaseNode node) throws Exception;

}
