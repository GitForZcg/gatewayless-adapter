package com.personal.demo.pojo.base;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/11/6 10:15
 */
public interface BasePayPublicParams {

    String orderId();

    default String createOrderId(String orderId) {
        return orderId;
    }

}
