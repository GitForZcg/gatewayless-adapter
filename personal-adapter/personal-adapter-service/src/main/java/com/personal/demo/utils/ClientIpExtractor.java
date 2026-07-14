package com.personal.demo.utils;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 客户端IP提取器
 * @date 2025/7/4 11:01
 */
public class ClientIpExtractor {

    private static final String[] IP_HEADERS = {
            "X-Forwarded-For",
            "X-Real-IP",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_CLIENT_IP",
            "HTTP_X_FORWARDED_FOR"
    };

    public static String getClientIp(HttpServletRequest request) {
        for (String header : IP_HEADERS) {
            String ip = request.getHeader(header);
            if (isValidIp(ip)) {
                // X-Forwarded-For可能包含多个IP，取第一个
                return ip.split(",")[0].trim();
            }
        }
        return request.getRemoteAddr();
    }

    private static boolean isValidIp(String ip) {
        return ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip);
    }
}
