package com.personal.demo.utils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 客户端域名提取器
 * @date 2025/7/7 11:01
 */
@Slf4j
public class ClientDomainExtractor {

    /**
     * 从请求中提取客户端域名
     * @param request HTTP请求
     * @return 客户端域名，可能为null
     */
    public static String getClientDomain(HttpServletRequest request) {
        // 1. 从Host头获取
        String host = request.getHeader("Host");
        if (host != null && !host.trim().isEmpty()) {
            // 去掉端口号
            int colonIndex = host.indexOf(':');
            if (colonIndex > 0) {
                host = host.substring(0, colonIndex);
            }
            return host.toLowerCase().trim();
        }

        // 2. 从X-Forwarded-Host头获取
        String forwardedHost = request.getHeader("X-Forwarded-Host");
        if (forwardedHost != null && !forwardedHost.trim().isEmpty()) {
            // 取第一个域名
            String[] hosts = forwardedHost.split(",");
            String firstHost = hosts[0].trim();
            int colonIndex = firstHost.indexOf(':');
            if (colonIndex > 0) {
                firstHost = firstHost.substring(0, colonIndex);
            }
            return firstHost.toLowerCase().trim();
        }

        // 3. 从X-Original-Host头获取
        String originalHost = request.getHeader("X-Original-Host");
        if (originalHost != null && !originalHost.trim().isEmpty()) {
            int colonIndex = originalHost.indexOf(':');
            if (colonIndex > 0) {
                originalHost = originalHost.substring(0, colonIndex);
            }
            return originalHost.toLowerCase().trim();
        }

        // 4. 从Server Name获取
        String serverName = request.getServerName();
        if (serverName != null && !serverName.trim().isEmpty() && !"localhost".equals(serverName)) {
            return serverName.toLowerCase().trim();
        }

        return null;
    }
}
