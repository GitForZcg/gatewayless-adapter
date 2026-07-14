package com.personal.demo.serivce;

import com.personal.demo.rule.db.AccessKeyConfigDbProcessor;
import org.springframework.stereotype.Service;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/9/8 17:54
 */
@Service
public class AccessKeyConfigRefreshService {

    private final AccessKeyConfigDbProcessor dbProcessor;

    public AccessKeyConfigRefreshService(AccessKeyConfigDbProcessor dbProcessor) {
        this.dbProcessor = dbProcessor;
    }

    public void refreshAccessKeyConfig(String serviceName) {
        dbProcessor.refreshServiceConfig(serviceName);
    }

    // 刷新所有配置
    public void refreshAllConfigs() {
        dbProcessor.refreshAllConfigs();
    }
}
