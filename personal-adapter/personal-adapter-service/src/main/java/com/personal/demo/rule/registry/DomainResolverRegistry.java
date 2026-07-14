package com.personal.demo.rule.registry;

import java.util.Set;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 域名解析注册表
 * @date 2025/7/7 10:57
 */
public interface DomainResolverRegistry {

    /**
     * 将域名解析为IP地址集合
     * @param domain 域名
     * @return IP地址集合
     */
    Set<String> resolveToIps(String domain);

    /**
     * 反向解析IP地址为域名集合
     * @param ip IP地址
     * @return 域名集合
     */
    Set<String> reverseResolve(String ip);
}
