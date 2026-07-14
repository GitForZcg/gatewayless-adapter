package com.personal.demo.pojo.base;

import com.personal.demo.rule.registry.DomainResolverRegistry;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.Set;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/7/4 10:48
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class WhitelistConfig {

    private Long id;

    /**
     * 访问类型：INTERNAL-公司内网，EXTERNAL-公司外网
     */
    private AccessType accessType;

    /**
     * 公司名称
     */
    private String channelName;

    /**
     * IP或域名地址，多个用逗号分隔
     */
    private String domainIp;

    /**
     * URL所属服务名称
     */
    private String serviceName;

    /**
     * 服务类型
     */

    private String apiType;

    /**
     * 服务子类型
     */

    private String apiSubType;

    /**
     * API路径地址
     */
    private String apiPath;

    /**
     * HTTP请求方式
     */
    private String httpMethod;

    /**
     * 客户端可用状态
     */
    private Boolean clientStatus;

    /**
     * IP域名可用状态
     */
    private Boolean ipStatus;

    /**
     * API可用状态
     */
    private Boolean apiStatus;

    /**
     * 描述信息
     */
    private String description;
    /**
     * 适配样例实体对象路径
     */
    private String className;
    /**
     * 访问密钥
     */
    private int accessKeyId;

    /**
     * 配置有效期
     */
    private Long expireTime;

    /**
     * 创建时间
     */
    private Long createdTime;

    /**
     * 更新时间
     */
    private Long updatedTime;

    public enum AccessType {
        INTERNAL, EXTERNAL
    }

    /**
     * 检查配置是否有效
     */
    public boolean isValid() {
        return clientStatus && ipStatus && apiStatus &&
                (expireTime == null || expireTime > Instant.now().toEpochMilli());
    }

    /**
     * 检查IP/域名是否匹配（域名优先级高于IP）
     *
     * @param clientIp       客户端IP地址
     * @param clientDomain   客户端域名（可选）
     * @param domainResolver 域名解析器
     */
    public boolean matchesDomainOrIp(String clientIp, String clientDomain, DomainResolverRegistry domainResolver) {
        if (domainIp == null || domainIp.trim().isEmpty()) {
            return true; // 没有配置IP/域名限制，默认允许
        }

        String[] entries = domainIp.split(",");
        log.info("当前请求ip:{}", clientIp);

        // 1. 通配符匹配（允许所有）
        for (String entry : entries) {
            entry = entry.trim();
            if ("*".equals(entry)) {
                return true;
            }
        }

        // 2. 优先进行域名匹配
        boolean domainMatched = matchDomainEntries(entries, clientIp, clientDomain, domainResolver);
        if (domainMatched) {
            return true;
        }

        // 3. 域名匹配失败后，进行IP匹配
        return matchIpEntries(entries, clientIp);
    }

    /**
     * 域名匹配处理（优先级高）
     */
    private boolean matchDomainEntries(String[] entries, String clientIp, String clientDomain, DomainResolverRegistry domainResolver) {
        for (String entry : entries) {
            entry = entry.trim();

            // 只处理域名相关的条目
            if (isDomainName(entry)) {
                if (matchesDomain(entry, clientIp, clientDomain, domainResolver)) {
                    log.debug("域名匹配成功: 配置域名=[{}], 客户端IP=[{}], 客户端域名=[{}]",
                            entry, clientIp, clientDomain);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * IP匹配处理（优先级低）
     */
    private boolean matchIpEntries(String[] entries, String clientIp) {
        for (String entry : entries) {
            entry = entry.trim();

            // 直接IP匹配
            if (isValidIpAddress(entry) && entry.equals(clientIp)) {
                log.debug("IP直接匹配成功: 配置IP=[{}], 客户端IP=[{}]", entry, clientIp);
                return true;
            }

            // CIDR格式匹配
            if (entry.contains("/") && isIpInCidrRange(clientIp, entry)) {
                log.debug("CIDR匹配成功: 配置CIDR=[{}], 客户端IP=[{}]", entry, clientIp);
                return true;
            }

            // IP通配符匹配
            if (isIpPattern(entry) && matchesIpPattern(clientIp, entry)) {
                log.debug("IP通配符匹配成功: 配置模式=[{}], 客户端IP=[{}]", entry, clientIp);
                return true;
            }
        }
        return false;
    }

    /**
     * 检查是否为有效的IP地址
     */
    private boolean isValidIpAddress(String ip) {
        try {
            String[] parts = ip.split("\\.");
            if (parts.length != 4) return false;
            for (String part : parts) {
                int num = Integer.parseInt(part);
                if (num < 0 || num > 255) return false;
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 检查是否为IP模式（含通配符）
     */
    private boolean isIpPattern(String pattern) {
        return pattern.contains("*") && pattern.contains(".");
    }

    /**
     * 检查是否为域名
     */
    private boolean isDomainName(String entry) {
        // 简单判断：不是IP地址且不是IP模式且包含点号的视为域名
        return !isValidIpAddress(entry) && !isIpPattern(entry) && entry.contains(".");
    }

    /**
     * IP通配符匹配
     */
    private boolean matchesIpPattern(String clientIp, String pattern) {
        try {
            String regex = pattern.replace(".", "\\.").replace("*", "\\d+");
            return clientIp.matches(regex);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 域名匹配
     */
    private boolean matchesDomain(String configDomain, String clientIp, String clientDomain, DomainResolverRegistry domainResolver) {
        if (domainResolver == null) {
            return false;
        }

        try {
            // 1. 直接域名匹配（如果客户端提供了域名）
            if (clientDomain != null && matchesDomainPattern(configDomain, clientDomain)) {
                return true;
            }

            // 2. 解析配置中的域名为IP进行匹配
            Set<String> resolvedIps = domainResolver.resolveToIps(configDomain);
            if (resolvedIps.contains(clientIp)) {
                return true;
            }

            // 3. 反向解析客户端IP为域名进行匹配
            Set<String> reverseDomains = domainResolver.reverseResolve(clientIp);
            for (String domain : reverseDomains) {
                if (matchesDomainPattern(configDomain, domain)) {
                    return true;
                }
            }

            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 域名模式匹配（支持通配符）
     */
    private boolean matchesDomainPattern(String pattern, String domain) {
        if (pattern.equals(domain)) {
            return true;
        }

        // 支持通配符域名匹配，如 *.example.com
        if (pattern.startsWith("*.")) {
            String suffix = pattern.substring(2);
            return domain.endsWith("." + suffix) || domain.equals(suffix);
        }

        return false;
    }

    /**
     * CIDR格式IP段匹配
     */
    private boolean isIpInCidrRange(String clientIp, String cidr) {
        try {
            String[] parts = cidr.split("/");
            if (parts.length != 2) return false;

            String networkIp = parts[0];
            int prefixLength = Integer.parseInt(parts[1]);

            long clientIpLong = ipToLong(clientIp);
            long networkIpLong = ipToLong(networkIp);
            long mask = (-1L << (32 - prefixLength));

            return (clientIpLong & mask) == (networkIpLong & mask);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * IP地址转换为long类型
     */
    private long ipToLong(String ip) {
        String[] parts = ip.split("\\.");
        long result = 0;
        for (int i = 0; i < 4; i++) {
            result |= (Long.parseLong(parts[i]) << (24 - (8 * i)));
        }
        return result;
    }

    /**
     * 检查API路径是否匹配
     */
    public boolean matchesApi(String apiPath, String method) {
        // 检查HTTP方法
        if (!"*".equals(httpMethod) && !httpMethod.equalsIgnoreCase(method)) {
            return false;
        }

        // 检查API路径
        if (this.apiPath.equals(apiPath)) {
            return true;
        }

        // 支持通配符匹配
        if (this.apiPath.contains("*")) {
            String pattern = this.apiPath.replace("*", ".*");
            return apiPath.matches(pattern);
        }

        return false;
    }
}
