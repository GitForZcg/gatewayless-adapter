package com.personal.demo.rule.handler;


import com.personal.demo.conf.RedisFactory;
import com.personal.demo.enu.RedisAdapterKey;
import com.personal.demo.pojo.base.RequestContext;
import com.personal.demo.pojo.base.WhitelistConfig;
import com.personal.demo.rule.db.WhitelistConfigDbProcessor;
import com.personal.demo.rule.registry.DomainResolverRegistry;
import com.personal.demo.utils.ClientDomainExtractor;
import com.personal.demo.utils.ClientIpExtractor;
import com.common.redis.sdk.RedissionClient;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/7/4 10:53
 */
@Component
@Slf4j
public class WhitelistServiceHandler {

    private final WhitelistConfigDbProcessor whitelistConfigDao;
    private final DomainResolverRegistry domainResolver;
    private final RedissionClient redissionClient = RedisFactory.getClient(RedisFactory.RedisClient.DEFAULT);

    public WhitelistServiceHandler(WhitelistConfigDbProcessor whitelistConfigDao,
                                   DomainResolverRegistry domainResolver) {
        this.whitelistConfigDao = whitelistConfigDao;
        this.domainResolver = domainResolver;
    }

    /**
     * 验证请求 - 支持域名验证（域名优先级高于IP）
     *
     * @param context      请求上下文
     * @param apiPath      API路径
     * @param httpMethod   HTTP方法
     * @param clientIp     客户端IP
     * @param clientDomain 客户端域名（可选，从请求头中获取）
     */
    public boolean validateRequest(RequestContext context, String apiPath, String httpMethod,
                                   String clientIp, String clientDomain) {
        try {
            // 获取该公司的所有有效配置
            List<WhitelistConfig> configs = getActiveConfigs();

            // 遍历配置进行匹配
            for (WhitelistConfig config : configs) {
                if (config.isValid() &&
                        context.getAccessType().equals(config.getAccessType()) &&
                        config.matchesApi(apiPath, httpMethod) &&
                        config.matchesDomainOrIp(clientIp, clientDomain, domainResolver)) {

                    // 判断是通过域名还是IP匹配成功的
                    String matchType = determineMatchType(config.getDomainIp(), clientIp, clientDomain);

                    log.info("Request configuration matched successfully, Access type:[{}], Request channel:[{}], Request method:[{}], Request path:[{}], Client IP:[{}], Client domain:[{}], Match type:[{}]",
                            context.getAccessType(), context.getChannelName(), context.getHttpMethod(), apiPath, clientIp, clientDomain, matchType);
                    return Boolean.TRUE;
                }
            }

            log.warn("No configuration matched for current request, Access type:[{}], Request channel:[{}], Request method:[{}], Request path:[{}], Client IP:[{}], Client domain:[{}]",
                    context.getAccessType(), context.getChannelName(), context.getHttpMethod(), apiPath, clientIp, clientDomain);
            return Boolean.FALSE;

        } catch (Exception e) {
            log.error("Error occurred while validating request: Request channel:[{}], Request method:[{}], Request path:[{}], Client IP:[{}]",
                    context.getChannelName(), context.getHttpMethod(), apiPath, clientIp, e);
            return Boolean.FALSE;
        }
    }

    public boolean validateRequest(HttpServletRequest request) {
        String apiPath = request.getRequestURI();
        String httpMethod = request.getMethod();
        String clientIp = ClientIpExtractor.getClientIp(request);
        String clientDomain = ClientDomainExtractor.getClientDomain(request);
        try {
            // 获取该公司的所有有效配置
            List<WhitelistConfig> configs = getActiveConfigs();
            // 遍历配置进行匹配
            for (WhitelistConfig config : configs) {
                if (config.isValid() &&
                        config.matchesApi(apiPath, httpMethod) &&
                        config.matchesDomainOrIp(clientIp, clientDomain, domainResolver)) {
                    // 判断是通过域名还是IP匹配成功的
                    String matchType = determineMatchType(config.getDomainIp(), clientIp, clientDomain);
                    log.info("Request configuration matched successfully Request path:[{}], Client IP:[{}], Client domain:[{}], Match type:[{}]"
                            , apiPath, clientIp, clientDomain, matchType);
                    return Boolean.TRUE;
                }
            }
            log.warn("No configuration matched for current request,  Request path:[{}], Client IP:[{}], Client domain:[{}]",
                    apiPath, clientIp, clientDomain);
            return Boolean.FALSE;
        } catch (Exception e) {
            log.error("Error occurred while validating request: Request path:[{}], Client IP:[{}]",
                    apiPath, clientIp, e);
            return Boolean.FALSE;
        }
    }

    /**
     * 判断匹配类型（用于日志记录）
     */
    private String determineMatchType(String configDomainIp, String clientIp, String clientDomain) {
        if (configDomainIp == null || configDomainIp.trim().isEmpty()) {
            return "无限制";
        }

        if ("*".equals(configDomainIp.trim())) {
            return "通配符";
        }

        String[] entries = configDomainIp.split(",");

        // 先检查域名匹配
        for (String entry : entries) {
            entry = entry.trim();
            if (isDomainEntry(entry)) {
                if (clientDomain != null && matchesDomainPattern(entry, clientDomain)) {
                    return "域名直接匹配";
                }
                // 这里简化判断，实际项目中可以更精确
                return "域名解析匹配";
            }
        }

        // 再检查IP匹配
        for (String entry : entries) {
            entry = entry.trim();
            if (entry.equals(clientIp)) {
                return "IP直接匹配";
            }
            if (entry.contains("/")) {
                return "CIDR匹配";
            }
            if (entry.contains("*")) {
                return "IP通配符匹配";
            }
        }

        return "未知匹配";
    }

    /**
     * 判断是否为域名条目
     */
    private boolean isDomainEntry(String entry) {
        return !isIpAddress(entry) && !isIpPattern(entry) && entry.contains(".");
    }

    /**
     * 检查是否为IP地址
     */
    private boolean isIpAddress(String ip) {
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
     * 域名模式匹配
     */
    private boolean matchesDomainPattern(String pattern, String domain) {
        if (pattern.equals(domain)) {
            return true;
        }

        if (pattern.startsWith("*.")) {
            String suffix = pattern.substring(2);
            return domain.endsWith("." + suffix) || domain.equals(suffix);
        }

        return false;
    }

    /**
     * 获取所有配置
     */
    public List<WhitelistConfig> getActiveConfigs() {
        String cacheKey = RedisAdapterKey.CACHE_REQUEST_PATH.generateKey();
        try {
            // 先从Redis缓存获取
            List<WhitelistConfig> cached = redissionClient.getObjValue(cacheKey);
            if (cached != null) {
                return cached;
            }
            // 从数据库加载
            List<WhitelistConfig> configs = whitelistConfigDao.findAll();
            // 存入Redis缓存
            redissionClient.set(cacheKey, configs);
            log.debug("缓存白名单配置成功条数: {}", configs.size());
            return configs;
        } catch (Exception e) {
            log.error("获取白名单配置失败: ", e);
            // 缓存失败时直接查询数据库
            return whitelistConfigDao.findAll();
        }
    }

    /**
     * 获取指定服务的配置
     */
    public List<WhitelistConfig> getConfigsByServiceName(String serviceName) {

        String cacheKey = RedisAdapterKey.CACHE_REQUEST_PATH.generateKey(serviceName);
        try {
            // 先从Redis缓存获取
            List<WhitelistConfig> cached = redissionClient.getObjValue(cacheKey);
            if (cached != null) {
                return cached;
            }
            // 从数据库加载
            List<WhitelistConfig> configs = whitelistConfigDao.findByServiceName(serviceName);
            // 存入Redis缓存
            redissionClient.set(cacheKey, configs);
            log.debug("缓存白名单配置成功条数: {}", configs.size());
            return configs;
        } catch (Exception e) {
            log.error("获取白名单配置失败: ", e);
            // 缓存失败时直接查询数据库
            return whitelistConfigDao.findAll();
        }
    }

    /**
     * 根据请求路径推断公司名称和请求类型 - 支持域名验证
     */
    public RequestContext buildRequestContext(String apiPath, String clientIp, String httpMethod) {
        RequestContext.RequestContextBuilder builder = RequestContext.builder()
                .apiPath(apiPath)
                .clientIp(clientIp)
                .httpMethod(httpMethod);
        List<WhitelistConfig> activeConfigs = getActiveConfigs();

        return activeConfigs.stream()
                .filter(config -> config.getApiPath().equals(apiPath) &&
                        config.matchesDomainOrIp(clientIp, null, domainResolver))
                .map(config -> {
                    builder.accessType(config.getAccessType())
                            .className(config.getClassName())
                            .apiType(config.getApiType())
                            .apiSubType(config.getApiSubType())
                            .channelName(config.getChannelName())
                            .serviceName(config.getServiceName());
                    RequestContext build = builder.build();
                    log.info("构建的上下文对象为:{}", build);
                    return build;
                }).findFirst().orElse(null);
    }

    /**
     * 根据请求路径推断公司名称和请求类型 - 支持域名验证（增强版）
     */
    public RequestContext buildRequestContextWithDomain(String apiPath, String clientIp, String clientDomain, String httpMethod) {
        RequestContext.RequestContextBuilder builder = RequestContext.builder()
                .apiPath(apiPath)
                .clientIp(clientIp)
                .httpMethod(httpMethod);
        List<WhitelistConfig> activeConfigs = getActiveConfigs();
        log.info("域名构建上线纹对象apiPath:{},clientIp:{}", apiPath, clientIp);
        return activeConfigs.stream()
                .filter(config -> config.getApiPath().equals(apiPath) &&
                        config.matchesDomainOrIp(clientIp, clientDomain, domainResolver))
                .map(config -> {
                    builder.accessType(config.getAccessType())
                            .className(config.getClassName())
                            .apiType(config.getApiType())
                            .apiSubType(config.getApiSubType())
                            .channelName(config.getChannelName())
                            .serviceName(config.getServiceName())
                            .accessKeyId(config.getAccessKeyId())
                    ;
                    RequestContext build = builder.build();
                    log.info("构建的上下文对象为:{}", build);
                    return build;
                }).findFirst().orElse(null);
    }

}
