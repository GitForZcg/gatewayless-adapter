package com.personal.demo.enu;

import lombok.Getter;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: redisKey统一管理
 * @date 2025/7/8 10:47
 */
@Getter
public enum RedisAdapterKey implements RedisKeyGenerator {
    CACHE_REQUEST_PATH("adapter:whitelist:request", "白名单"),
    CACHE_ACCESS_KEY("adapter:access:key", "访问密钥"),
    CACHE_FLOW_MAPPING("adapter:flow:mapping", "流程映射"),
    CACHE_DOMAIN("adapter:whitelist:domain:resolve", "域名缓存"),
    CACHE_DOMAIN_REVERSE("adapter:whitelist:domain:reverse", "域名解析缓存"),
    ;

    /**
     * -- GETTER --
     * 获取 Redis Key 的模板
     */
    private final String prefix;
    private final String desc;

    RedisAdapterKey(String template, String desc) {
        this.prefix = template;
        this.desc = desc;
    }

    @Override
    public String getPrefix() {
        return prefix;
    }
}
