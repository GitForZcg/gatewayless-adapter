package com.personal.demo.rule.registry;

import com.personal.demo.conf.RedisFactory;
import com.personal.demo.enu.RedisAdapterKey;
import com.common.redis.sdk.RedissionClient;
import io.netty.handler.timeout.TimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 域名解析器实现类
 * @date 2025/7/7 10:57
 */
@Component
@Slf4j
public class DomainResolverRegistration implements DomainResolverRegistry {
    private final RedissionClient redissionClient = RedisFactory.getClient(RedisFactory.RedisClient.DEFAULT);
    private static final int CACHE_EXPIRE_SECONDS = 3600; // 1小时缓存
    private static final int DNS_TIMEOUT_MILLISECONDS = 5000; // DNS查询超时时间

    public DomainResolverRegistration() {
        // 初始化DNS系统属性
        initDnsProperties();
    }

    @Override
    public Set<String> resolveToIps(String domain) {
        if (domain == null || domain.trim().isEmpty()) {
            return Collections.emptySet();
        }

        String cacheKey = RedisAdapterKey.CACHE_DOMAIN.generateKey(domain.toLowerCase());
        try {
            // 1. 先从缓存获取
            Set<String> cached = redissionClient.getObjValue(cacheKey);
            if (cached != null) {
                return cached;
            }

            // 2. 进行DNS解析
            Set<String> ips = performDnsResolution(domain);

            // 3. 存入缓存
            if (!ips.isEmpty()) {
                redissionClient.setEx(cacheKey, ips, CACHE_EXPIRE_SECONDS, TimeUnit.SECONDS);
            }

            log.debug("域名解析成功: {} -> {}", domain, ips);
            return ips;

        } catch (Exception e) {
            log.error("域名解析失败: {}", domain, e);
            return Collections.emptySet();
        }
    }

    @Override
    public Set<String> reverseResolve(String ip) {
        if (ip == null || ip.trim().isEmpty()) {
            return Collections.emptySet();
        }

        String cacheKey = RedisAdapterKey.CACHE_DOMAIN_REVERSE.generateKey(ip);
        try {
            // 1. 先从缓存获取
            Set<String> cached = redissionClient.getObjValue(cacheKey);
            if (cached != null) {
                return cached;
            }

            // 2. 进行反向DNS解析
            Set<String> domains = performReverseDnsResolution(ip);

            // 3. 存入缓存
            if (!domains.isEmpty()) {
                redissionClient.setEx(cacheKey, domains, CACHE_EXPIRE_SECONDS, TimeUnit.SECONDS);
            }

            log.debug("反向域名解析成功: {} -> {}", ip, domains);
            return domains;

        } catch (Exception e) {
            log.error("反向域名解析失败: {}", ip, e);
            return Collections.emptySet();
        }
    }

    /**
     * 执行DNS解析
     */
    private Set<String> performDnsResolution(String domain) {
        Set<String> ips = new HashSet<>();

        try {
            // 使用CompletableFuture实现超时控制
            CompletableFuture<InetAddress[]> future = CompletableFuture.supplyAsync(() -> {
                try {
                    return InetAddress.getAllByName(domain);
                } catch (UnknownHostException e) {
                    throw new RuntimeException(e);
                }
            });

            InetAddress[] addresses = future.get(DNS_TIMEOUT_MILLISECONDS, TimeUnit.MILLISECONDS);

            for (InetAddress address : addresses) {
                String ip = address.getHostAddress();
                // 过滤IPv6地址（如果不需要的话）
                if (isValidIPv4(ip)) {
                    ips.add(ip);
                }
            }

        } catch (TimeoutException e) {
            log.warn("DNS解析超时: {}", domain);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.warn("DNS解析被中断: {}", domain);
        } catch (Exception e) {
            log.error("DNS解析异常: {}", domain, e);
        }

        return ips;
    }

    /**
     * 执行反向DNS解析
     */
    private Set<String> performReverseDnsResolution(String ip) {
        Set<String> domains = new HashSet<>();

        try {
            // 使用CompletableFuture实现超时控制
            CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
                try {
                    InetAddress address = InetAddress.getByName(ip);
                    return address.getCanonicalHostName();
                } catch (UnknownHostException e) {
                    throw new RuntimeException(e);
                }
            });

            String hostname = future.get(DNS_TIMEOUT_MILLISECONDS, TimeUnit.MILLISECONDS);

            // 如果hostname不等于IP，说明解析成功
            if (!hostname.equals(ip)) {
                domains.add(hostname.toLowerCase());
            }

        } catch (TimeoutException e) {
            log.warn("反向DNS解析超时: {}", ip);
        } catch (ExecutionException e) {
            log.warn("无法反向解析IP: {}", ip);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.warn("反向DNS解析被中断: {}", ip);
        } catch (Exception e) {
            log.error("反向DNS解析异常: {}", ip, e);
        }

        return domains;
    }

    /**
     * 验证是否为有效的IPv4地址
     */
    private boolean isValidIPv4(String ip) {
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
     * 初始化DNS系统属性
     */
    private void initDnsProperties() {
        try {
            // DNS缓存时间设置（秒）
            System.setProperty("sun.net.inetaddr.ttl", "300"); // 5分钟
            System.setProperty("sun.net.inetaddr.negative.ttl", "60"); // 1分钟

            // DNS查询设置
            System.setProperty("sun.net.useExclusiveBind", "false");

            log.info("DNS resolver configuration initialization completed");
        } catch (Exception e) {
            log.warn("DNS resolver configuration initialization failed", e);
        }
    }
}
