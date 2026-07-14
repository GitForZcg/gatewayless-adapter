package com.personal.demo.conf;

import com.personal.demo.utils.SpringContextUtil;
import com.common.redis.sdk.RedissionClient;
import org.springframework.beans.BeansException;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: redis工厂获取redisClient
 * @date 2025/7/18 14:52
 */
@Component
public class RedisFactory {

    private static final Map<String, RedissionClient> clientCache = new ConcurrentHashMap<>();

    // 获取指定客户端
    public static RedissionClient getClient(String beanName) {
        return clientCache.computeIfAbsent(beanName, name -> {
            try {
                return SpringContextUtil.getBeanByNameAndType(name, RedissionClient.class);
            } catch (BeansException e) {
                throw new RuntimeException("Redisson client not found: " + name, e);
            }
        });
    }

    public static class RedisClient {
        /**
         * platform redis
         */
        public static final String DEFAULT = "default";

        /**
         * business redis
         */
        public static final String DEFAULT_1 = "default_1";
    }
}
