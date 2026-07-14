package com.personal.demo.serivce;

import com.personal.demo.rule.db.WhitelistConfigDbProcessor;
import org.springframework.stereotype.Service;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:  配置刷新服务
 * @date 2025/7/8 11:00
 */
@Service
public class WhitelistConfigRefreshService {


    private final WhitelistConfigDbProcessor dbHelper;

    public WhitelistConfigRefreshService(WhitelistConfigDbProcessor dbHelper) {
        this.dbHelper = dbHelper;
    }

    // 刷新指定服务的配置
    public void refreshServiceConfig(String serviceName) {
        dbHelper.refreshServiceConfig(serviceName);
    }

    // 刷新所有配置
    public void refreshAllConfigs() {
        dbHelper.refreshAllConfigs();
    }

}