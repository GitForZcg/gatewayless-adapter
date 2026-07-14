package com.personal.demo.pojo.base;

import java.util.Set;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 财务md5参数顶级规则
 * @date 2025/7/24 13:39
 */
public interface DemoFinanceMd5Param {

    /**
     * 是否需要签名
     */
    Set<String> needSign();

    /**
     * 创建签名
     */
    default Set<String> createSign(Set<String> set) {
        return set;
    }

    /**
     * 需要加密或解密的参数
     */
    Set<String> needSignParam();

    default Set<String> createSignParam(Set<String> set) {
        return set;
    }

    String orderId();

    /**
     * 创建订单
     */
    default String createOrderId(String orderId) {
        return orderId;
    }


}
