package com.personal.demo.controller;


import com.personal.demo.serivce.AccessKeyConfigRefreshService;
import com.personal.demo.serivce.FlowConfigRefreshService;
import com.personal.demo.serivce.WhitelistConfigRefreshService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/7/8 10:47
 */
@RestController
@RequestMapping("/config")
public class RefreshConfigConfigController {

    @Resource
    WhitelistConfigRefreshService whitelistConfigRefreshService;

    @Resource
    FlowConfigRefreshService flowConfigRefreshService;

    @Resource
    AccessKeyConfigRefreshService accessKeyConfigRefreshService;

    @PostMapping("whitelistAll")
    public void refreshWhitelistAllConfigs() {
        whitelistConfigRefreshService.refreshAllConfigs();
    }

    @PostMapping("whitelist")
    public void refreshWhitelistServiceConfig(@RequestParam("serviceName") String serviceName) {
        whitelistConfigRefreshService.refreshServiceConfig(serviceName);
    }

    @PostMapping("flowMappingAll")
    public void refreshFlowMappingAllConfigs() {
        flowConfigRefreshService.refreshAllConfigs();
    }

    @PostMapping("flowMapping")
    public void refreshFlowMappingServiceType(@RequestParam("serviceType") String serviceType) {
        flowConfigRefreshService.refreshServiceConfig(serviceType);
    }

    @PostMapping("accessKeyAll")
    public void refreshAccessKeyAllConfigs() {
        accessKeyConfigRefreshService.refreshAllConfigs();
    }

    @PostMapping("accessKey")
    public void refreshAccessKeyAllConfigs(@RequestParam("serviceName") String serviceName) {
        accessKeyConfigRefreshService.refreshAccessKeyConfig(serviceName);
    }
}
