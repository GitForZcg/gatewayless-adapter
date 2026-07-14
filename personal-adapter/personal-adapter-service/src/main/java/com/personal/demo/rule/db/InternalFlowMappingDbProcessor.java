package com.personal.demo.rule.db;

import com.personal.demo.conf.RedisFactory;
import com.personal.demo.enu.RedisAdapterKey;
import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.enu.internal.base.NodeFactory;
import com.personal.demo.rule.initalizer.InternalNodeInitializer;
import com.common.redis.sdk.RedissionClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.personal.demo.enu.RedisAdapterKey.CACHE_FLOW_MAPPING;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 内部流程映射处理器
 * @date 2025/7/2 10:50
 */
@Component
@Slf4j
@SuppressWarnings("all")
public class InternalFlowMappingDbProcessor {

    private final static String loadSql = "SELECT node_name, class_name FROM flow_node_mapping WHERE service_type = ? AND is_active = 1";


    private final JdbcTemplate jdbcTemplate;
    private final RedissionClient redissionClient = RedisFactory.getClient(RedisFactory.RedisClient.DEFAULT);

    public InternalFlowMappingDbProcessor(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 缓存所有配置
    private final Map<String, Map<BaseNode, Class<?>>> configCache = new ConcurrentHashMap<>();

    public Map<BaseNode, Class<?>> getFlowMapping(String serviceType) {
        return configCache.computeIfAbsent(serviceType, this::loadFromDatabase);
    }

    // 刷新指定服务类型的配置
    public void refreshConfig(String serviceType) {
        configCache.remove(serviceType);
        boolean result = redissionClient.delete(RedisAdapterKey.CACHE_FLOW_MAPPING.generateKey(serviceType));
        log.info(String.format("已清除缓存配置: %s", serviceType));
        // 重新加载
        getFlowMapping(serviceType);
        log.info(String.format("已刷新配置: %s", serviceType));
    }

    // 刷新所有配置
    public void refreshAllConfigs() {
        Set<String> serviceTypes = new HashSet<>(configCache.keySet());
        for (String serviceType : serviceTypes) {
            redissionClient.delete(RedisAdapterKey.CACHE_FLOW_MAPPING.generateKey(serviceType));
            log.info("已清除[{serviceType}]缓存配置");
        }

        configCache.clear();
        // 重新加载所有已使用的配置
        for (String serviceType : serviceTypes) {
            getFlowMapping(serviceType);
        }
        log.info(String.format("已刷新所有配置，共[ %s ]个服务类型", serviceTypes.size()));
    }

    // 获取所有已缓存的服务类型
    public Set<String> getCachedServiceTypes() {
        return new HashSet<>(configCache.keySet());
    }

    private Map<BaseNode, Class<?>> loadFromDatabase(String serviceType) {
        //确保Node都已加载
        InternalNodeInitializer.ensureInitialized();

        Map<BaseNode, Class<?>> appletMap = new HashMap<>();
        String key = RedisAdapterKey.CACHE_FLOW_MAPPING.generateKey(serviceType);
        if (redissionClient.hasKey(key)) {
            appletMap = redissionClient.readAllMap(key);
            if (ObjectUtils.isNotEmpty(appletMap)) {
                return appletMap;
            }
        }

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(loadSql, serviceType);
        for (Map<String, Object> row : rows) {
            try {
                String nodeName = (String) row.get("node_name");
                String className = (String) row.get("class_name");

                // 解析BaseNode - 根据你的实际BaseNode类型调整
                BaseNode node = parseBaseNode(nodeName.toUpperCase());
                Class<?> clazz = Class.forName(className);

                appletMap.put(node, clazz);
            } catch (Exception e) {
                // 记录错误但继续处理其他配置
                log.error(String.format("Failed to load db configuration: %s, Error: %s", row, e.getMessage()));
            }
        }
        redissionClient.putAll(CACHE_FLOW_MAPPING + serviceType, appletMap);
        log.info(String.format("Loaded configuration from database: [ %s ], total [ %s ] mappings", serviceType, appletMap.size()));
        return appletMap;
    }

    private BaseNode parseBaseNode(String nodeName) {
        // 使用工厂自注册模式解析
        return NodeFactory.parse(nodeName);
    }
}