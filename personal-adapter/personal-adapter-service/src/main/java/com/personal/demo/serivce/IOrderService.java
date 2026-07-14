package com.personal.demo.serivce;

import com.personal.demo.dto.order.DemoOrderDTO;
import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.request.order.PushOrderParams;
import com.personal.demo.request.order.TestOrderParams;

public interface IOrderService {

    Object query(TestOrderParams params, BaseNode node) throws Exception;

    Object pushOrder(PushOrderParams params, BaseNode node) throws Exception;

    /**
     * 拉取订单
     * @param demoOrderDTO 适配样例订单数据
     */
    void pullOrder(DemoOrderDTO demoOrderDTO) ;

    Object pushOffSetOrder(PushOrderParams params, BaseNode node) throws Exception;
}
