package com.personal.demo.rule.db;

import com.personal.demo.conf.RedisFactory;
import com.personal.demo.enu.RedisAdapterKey;
import com.personal.demo.pojo.base.AccessKeyConfig;
import com.common.redis.sdk.RedissionClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 访问密钥处理
 * @date 2025/7/17 14:35
 */
@Component
@Slf4j
@SuppressWarnings("all")
public class AccessKeyConfigDbProcessor {

    private final JdbcTemplate jdbcTemplate;

    private final RedissionClient redissionClient = RedisFactory.getClient(RedisFactory.RedisClient.DEFAULT);

    private static final String BASE_SQL = "SELECT id, service_name,appid, secret,extra, salt,timestamp_tolerance, public_key," +
            "private_key, active_status , description,expire_time, created_time, updated_time " +
            "FROM access_key_config";

    private static final RowMapper<AccessKeyConfig> ROW_MAPPER = new AccessKeyConfigDbProcessor.AccessKeyConfigRowMapper();

    public AccessKeyConfigDbProcessor(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 查询所有有效配置
     */
    public List<AccessKeyConfig> findAll() {
        String sql = BASE_SQL + " WHERE  active_status = 1 AND (expire_time IS NULL OR expire_time > NOW())";
        return jdbcTemplate.query(sql, ROW_MAPPER);
    }

    /**
     * 根据渠道名称查询有效配置
     */
    public List<AccessKeyConfig> findByChannel(String channel) {
        String sql = BASE_SQL + " WHERE service_name = ? and active_status = 1 AND (expire_time IS NULL OR expire_time > NOW())";
        return jdbcTemplate.query(sql, ROW_MAPPER, channel);
    }

    /**
     * 清除缓存
     */
    public void refreshAllConfigs() {
        try {
//            redissionClient.delete(RedisAdapterKey.CACHE_ACCESS_KEY.generateKey());
            String key = RedisAdapterKey.CACHE_ACCESS_KEY.generateKey();
            if (redissionClient.hasKey(key)) {
                redissionClient.delete(key);
            }
            log.info("访问密钥配置缓存清除成功");
            List<AccessKeyConfig> configs = this.findAll();
            redissionClient.set(RedisAdapterKey.CACHE_ACCESS_KEY.generateKey(), configs);
            log.info("刷新全部访问密钥配置缓存成功");
        } catch (Exception e) {
            log.error("刷新全部访问密钥配置缓存失败", e);
        }
    }

    /**
     * 清除指定服务的缓存
     */
    public void refreshServiceConfig(String serviceName) {
        try {
            String key = RedisAdapterKey.CACHE_ACCESS_KEY.generateKey(serviceName);
            if (redissionClient.hasKey(key)) {
                redissionClient.delete(key);
                log.info("服务/渠道[{}]的访问密钥配置缓存清除成功", serviceName);
            }
            List<AccessKeyConfig> configs = this.findByChannel(serviceName);
            redissionClient.set(RedisAdapterKey.CACHE_ACCESS_KEY.generateKey(serviceName), configs);
            log.info("服务/渠道[{}]的访问密钥配置缓存刷新成功", serviceName);
        } catch (Exception e) {
            log.error("清除服务/渠道[{}]的访问密钥配置缓存失败", serviceName, e);
        }
    }


    /**
     * RowMapper实现
     */
    private static class AccessKeyConfigRowMapper implements RowMapper<AccessKeyConfig> {
        @Override
        public AccessKeyConfig mapRow(ResultSet rs, int rowNum) throws SQLException {
            return AccessKeyConfig.builder()
                    .id(rs.getLong("id"))
                    .serviceName(rs.getString("service_name"))
                    .appid(rs.getString("appid"))
                    .secret(rs.getString("secret"))
                    .extra(rs.getString("extra"))
                    .salt(rs.getString("salt"))
                    .timestampTolerance(rs.getInt("timestamp_tolerance"))
                    .publicKey(rs.getString("public_key"))
                    .privateKey(rs.getString("private_key"))
                    .activeStatus(rs.getBoolean("active_status"))
                    .description(rs.getString("description"))
                    .expireTime(rs.getTimestamp("expire_time") != null ?
                            rs.getTimestamp("expire_time").getTime() : null)
                    .createdTime(rs.getTimestamp("created_time").getTime())
                    .updatedTime(rs.getTimestamp("updated_time").getTime())
                    .build();
        }
    }
}
