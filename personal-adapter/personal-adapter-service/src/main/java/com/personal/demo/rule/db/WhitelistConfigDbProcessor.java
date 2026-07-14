package com.personal.demo.rule.db;

import com.personal.demo.conf.RedisFactory;
import com.personal.demo.enu.RedisAdapterKey;
import com.personal.demo.pojo.base.WhitelistConfig;
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
 * @description: 白名单配置处理器
 * @date 2025/7/4 10:51
 */
@Component
@Slf4j
@SuppressWarnings("all")
public class WhitelistConfigDbProcessor {

    private final JdbcTemplate jdbcTemplate;

    private final RedissionClient redissionClient = RedisFactory.getClient(RedisFactory.RedisClient.DEFAULT);

    private static final String BASE_SQL = "SELECT id, access_type, channel_name, domain_ip, service_name," +
            "api_type, api_sub_type ,api_path, http_method, client_status, ip_status, api_status, " +
            "description , class_name,expire_time, created_time, updated_time ,access_key_id FROM whitelist_config";

    private static final RowMapper<WhitelistConfig> ROW_MAPPER = new WhitelistConfigRowMapper();

    public WhitelistConfigDbProcessor(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 查询所有有效配置
     */
    public List<WhitelistConfig> findAll() {
        String sql = BASE_SQL + " WHERE  client_status = 1 AND ip_status = 1 AND api_status = 1 " +
                "AND (expire_time IS NULL OR expire_time > NOW())";
        return jdbcTemplate.query(sql, ROW_MAPPER);
    }

    /**
     * 根据公司名称查询有效配置
     */
    public List<WhitelistConfig> findByServiceName(String serviceName) {
        String sql = BASE_SQL + " WHERE service_name = ? AND client_status = 1 AND ip_status = 1 AND api_status = 1 " +
                "AND (expire_time IS NULL OR expire_time > NOW())";
        return jdbcTemplate.query(sql, ROW_MAPPER, serviceName);
    }

    /**
     * 清除缓存
     */
    public void refreshAllConfigs() {
        try {
            redissionClient.delete(RedisAdapterKey.CACHE_REQUEST_PATH.generateKey());
            log.info("白名单全部配置缓存清除成功");
            List<WhitelistConfig> configs = this.findAll();
            redissionClient.set(RedisAdapterKey.CACHE_REQUEST_PATH.generateKey(), configs);
            log.info("刷新白名单全部配置缓存成功");
        } catch (Exception e) {
            log.error("刷新全部白名单配置缓存失败", e);
        }
    }

    /**
     * 清除指定服务的缓存
     */
    public void refreshServiceConfig(String serviceName) {
        try {
            String key = RedisAdapterKey.CACHE_REQUEST_PATH.generateKey(serviceName);
            if (redissionClient.hasKey(key)) {
                redissionClient.delete(key);
                log.info("服务[{}]的白名单配置缓存清除成功", serviceName);
            }
            List<WhitelistConfig> configs = this.findByServiceName(serviceName);
            redissionClient.set(RedisAdapterKey.CACHE_REQUEST_PATH.generateKey(serviceName), configs);
            log.info("服务[{}]的白名单配置缓存刷新成功", serviceName);
        } catch (Exception e) {
            log.error("清除服务[{}]的白名单配置缓存失败", serviceName, e);
        }
    }

    /**
     * RowMapper实现
     */
    private static class WhitelistConfigRowMapper implements RowMapper<WhitelistConfig> {
        @Override
        public WhitelistConfig mapRow(ResultSet rs, int rowNum) throws SQLException {
            return WhitelistConfig.builder()
                    .id(rs.getLong("id"))
                    .accessType(WhitelistConfig.AccessType.valueOf(rs.getString("access_type")))
                    .channelName(rs.getString("channel_name"))
                    .domainIp(rs.getString("domain_ip"))
                    .serviceName(rs.getString("service_name"))
                    .apiPath(rs.getString("api_path"))
                    .apiType(rs.getString("api_type"))
                    .apiSubType(rs.getString("api_sub_type"))
                    .className(rs.getString("class_name"))
                    .accessKeyId(rs.getInt("access_key_id"))
                    .httpMethod(rs.getString("http_method"))
                    .clientStatus(rs.getBoolean("client_status"))
                    .ipStatus(rs.getBoolean("ip_status"))
                    .apiStatus(rs.getBoolean("api_status"))
                    .description(rs.getString("description"))
                    .expireTime(rs.getTimestamp("expire_time") != null ?
                            rs.getTimestamp("expire_time").getTime() : null)
                    .createdTime(rs.getTimestamp("created_time").getTime())
                    .updatedTime(rs.getTimestamp("updated_time").getTime())
                    .build();
        }
    }
}
