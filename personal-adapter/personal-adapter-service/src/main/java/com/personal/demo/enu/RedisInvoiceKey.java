package com.personal.demo.enu;

import lombok.Getter;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/9/16 13:15
 */
@Getter
public enum RedisInvoiceKey implements RedisKeyGenerator {

    INVOICE_UPDATE_LOCK("invoice:update:locked", "发票修改次数锁定"),

    ;

    /**
     * -- GETTER --
     * 获取 Redis Key 的模板
     */
    private final String prefix;
    private final String desc;

    RedisInvoiceKey(String template, String desc) {
        this.prefix = template;
        this.desc = desc;
    }

    @Override
    public String getPrefix() {
        return prefix;
    }
}
