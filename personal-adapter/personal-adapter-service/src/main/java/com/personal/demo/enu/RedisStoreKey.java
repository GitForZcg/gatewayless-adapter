package com.personal.demo.enu;

import lombok.Getter;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/9/16 13:15
 */
@Getter
public enum RedisStoreKey implements RedisKeyGenerator {

    MAPPING_STORE_CODE("store:query:MappingStoreCode", "适配样例门店映射门店"),

    STORE_CODE_MAPPING("store:query:StoreCodeMapping", "门店映射适配样例门店"),
    ;

    /**
     * -- GETTER --
     * 获取 Redis Key 的模板
     */
    private final String prefix;
    private final String desc;

    RedisStoreKey(String template, String desc) {
        this.prefix = template;
        this.desc = desc;
    }

    @Override
    public String getPrefix() {
        return prefix;
    }
}
