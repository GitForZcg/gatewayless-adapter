package com.personal.demo.enu;

import lombok.Getter;


/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 服务类型
 * @date 2025/7/2 10:40
 */
@Getter
public enum ServiceType {

    ORDER("订单系统","ORDER"),
    BILL("账单系统","BILL"),
    PAY("支付系统","PAY"),
    STORE("门店系统","STORE"),
    PRODUCT("产品系统","PRODUCT"),
    FINANCE("财务系统","FINANCE"),
    PAYMENT("付款单系统","PAYMENT"),
    INVOICE("发票系统", "INVOICE"),
    MEMBER("会员系统", "MEMBER"),
    TRADE("交易系统", "TRADE"),
    COMPUTE("算价系统", "COMPUTE"),
    ;

    public final String desc;
    public final String value;

    ServiceType(String desc, String value) {
        this.desc = desc;
        this.value = value;
    }
}
