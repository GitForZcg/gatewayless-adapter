package com.personal.demo.enu;

import lombok.Getter;

@Getter
public enum RedisOrderKey implements RedisKeyGenerator {


    STORE_BINDING_STORE_KEY("store:query:bindingStore", "与适配样例对应的门店id"),

    ORDER_EXIST_KEY ("order:query:orderExist", "是否存在此订单"),

    ORDER_SYNC_LOCK_KEY("order:sync:lock", "订单同步锁");

    /**
     * -- GETTER --
     * 获取 Redis Key 的模板
     */
    private final String prefix;
    private final String desc;

    RedisOrderKey(String template, String desc) {
        this.prefix = template;
        this.desc = desc;
    }

    @Override
    public String getPrefix() {
        return prefix;
    }

}
