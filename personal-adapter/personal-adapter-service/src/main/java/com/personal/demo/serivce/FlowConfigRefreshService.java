package com.personal.demo.serivce;

import com.personal.demo.rule.db.InternalFlowMappingDbProcessor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 配置刷新服务
 * @date 2025/7/2 11:00
 */
@Service
public class FlowConfigRefreshService {


    private final InternalFlowMappingDbProcessor dbHelper;

    public FlowConfigRefreshService(InternalFlowMappingDbProcessor dbHelper) {
        this.dbHelper = dbHelper;
    }

    // 刷新指定服务的配置
    public void refreshServiceConfig(String serviceType) {
        dbHelper.refreshConfig(serviceType);
    }

    // 刷新所有配置
    public void refreshAllConfigs() {
        dbHelper.refreshAllConfigs();
    }

    // 获取当前缓存状态
    public Map<String, Object> getCacheStatus() {
        Set<String> cachedTypes = dbHelper.getCachedServiceTypes();
        Map<String, Object> status = new HashMap<>();
        status.put("cachedServiceTypes", cachedTypes);
        status.put("cacheCount", cachedTypes.size());
        status.put("lastRefreshTime", new Date());
        return status;
    }
}