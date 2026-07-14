package com.personal.demo.enu;

import lombok.Getter;

@Getter
public enum RedisVendorKey implements RedisKeyGenerator {


    LOGIN_AUTH("vendor:login:auth", "供应商登陆认证"),
    ;

    /**
     * -- GETTER --
     * 获取 Redis Key 的模板
     */
    private final String prefix;
    private final String desc;

    RedisVendorKey(String template, String desc) {
        this.prefix = template;
        this.desc = desc;
    }

    @Override
    public String getPrefix() {
        return prefix;
    }

}
