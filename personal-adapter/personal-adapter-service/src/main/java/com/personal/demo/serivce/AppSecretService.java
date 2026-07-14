package com.personal.demo.serivce;

import com.personal.demo.conf.RedisFactory;
import com.personal.demo.enu.RedisAdapterKey;
import com.personal.demo.pojo.base.AccessKeyConfig;
import com.personal.demo.rule.db.AccessKeyConfigDbProcessor;
import com.common.redis.sdk.RedissionClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/7/8 10:36
 */
@Service
@Slf4j
public class AppSecretService {

    private final AccessKeyConfigDbProcessor dbProcessor;
    private final RedissionClient redissionClient = RedisFactory.getClient(RedisFactory.RedisClient.DEFAULT);

    public AppSecretService(AccessKeyConfigDbProcessor dbProcessor) {
        this.dbProcessor = dbProcessor;
    }

    public AccessKeyConfig getSecret(String appId, int accessKeyId) {
        List<AccessKeyConfig> all = this.getActiveConfigs();
        return all.stream().filter(conf -> appId.equals(conf.getAppid()) && accessKeyId == conf.getId())
                .findFirst().orElse(null);
    }

    public AccessKeyConfig getSecret(String appId) {
        List<AccessKeyConfig> all = this.getActiveConfigs();
        return all.stream().filter(conf -> appId.equals(conf.getAppid()))
                .findFirst().orElse(null);
    }

    public AccessKeyConfig getSecret(int accessKeyId) {
        List<AccessKeyConfig> all = this.getActiveConfigs();
        return all.stream().filter(conf -> accessKeyId == conf.getId())
                .findFirst().orElse(null);
    }

    public AccessKeyConfig getSecretByServiceName(String serviceName) {
        List<AccessKeyConfig> all = this.getActiveConfigs();
        return all.stream().filter(conf -> serviceName.equals(conf.getServiceName()))
                .findFirst().orElse(null);
    }

    public AccessKeyConfig getSM2Secret(String channel) {
        List<AccessKeyConfig> all = this.getActiveConfigs();
        return all.stream().filter(conf -> channel.equals(conf.getServiceName()))
                .findFirst().orElse(null);
    }

    public List<AccessKeyConfig> getActiveConfigs() {
        String cacheKey = RedisAdapterKey.CACHE_ACCESS_KEY.generateKey();
        try {
            // 先从Redis缓存获取
            List<AccessKeyConfig> cached = redissionClient.getObjValue(cacheKey);
            if (cached != null) {
                return cached;
            }
            // 从数据库加载
            List<AccessKeyConfig> configs = dbProcessor.findAll();
            // 存入Redis缓存
            redissionClient.set(cacheKey, configs);
            log.debug("缓存访问密钥配置成功条数: {}", configs.size());
            return configs;
        } catch (Exception e) {
            log.error("获取访问密钥配置失败: ", e);
            // 缓存失败时直接查询数据库
            return dbProcessor.findAll();
        }
    }
}
