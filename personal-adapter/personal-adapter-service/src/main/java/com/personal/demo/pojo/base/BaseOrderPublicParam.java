package com.personal.demo.pojo.base;

public interface BaseOrderPublicParam {

    /**
     * 获取订单
     */
    String orderId();
    /**
     * 创建订单
     */
    default String createOrderId(String orderId) {
        return orderId;
    }
}
