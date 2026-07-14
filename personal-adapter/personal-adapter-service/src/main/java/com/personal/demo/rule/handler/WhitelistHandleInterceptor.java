package com.personal.demo.rule.handler;

import com.personal.demo.pojo.base.RequestContext;
import com.personal.demo.pojo.base.WhitelistConfig;
import com.personal.demo.pojo.wrapper.AdapterRespCode;
import com.personal.demo.rule.factory.RequestHandlerStrategyFactory;
import com.personal.demo.rule.strategy.RequestHandlerStrategy;
import com.personal.demo.utils.ClientDomainExtractor;
import com.personal.demo.utils.ClientIpExtractor;
import com.personal.demo.utils.RequestContextUtil;
import com.personal.demo.utils.ResponseWriter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/7/4 10:53
 */
@Component
@Slf4j
public class WhitelistHandleInterceptor implements HandlerInterceptor {

    private final WhitelistServiceHandler whitelistService;
    private final RequestContextUtil requestContextHolder;
    private final RequestHandlerStrategyFactory requestFactory;
    private final ResponseWriter responseWriter;

    public WhitelistHandleInterceptor(WhitelistServiceHandler whitelistService,
                                      RequestContextUtil requestContextHolder,
                                      RequestHandlerStrategyFactory strategyFactory,
                                      ResponseWriter responseWriter) {
        this.whitelistService = whitelistService;
        this.requestContextHolder = requestContextHolder;
        this.requestFactory = strategyFactory;
        this.responseWriter = responseWriter;
    }

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request,
                             @NotNull HttpServletResponse response,
                             @NotNull Object handler) throws Exception {

        // 1. 构建请求上下文（支持域名验证）
        RequestContext context;
        try {
            log.info("请求进入，url:{}", request.getRequestURI());
            context = buildRequestContextWithDomain(request);

            if (context == null) {
                return responseWriter.writeForbiddenResponse(response, AdapterRespCode.ILLEGAL_API_ERROR.message);
            }

            // 2. 白名单验证（支持域名验证）
            if (WhitelistConfig.AccessType.EXTERNAL.equals(context.getAccessType())
                    && !validateWhitelistWithDomain(context, request)) {
                return responseWriter.writeAccessDeniedResponse(response);
            }

            // 3. 根据访问类型选择处理策略
            RequestHandlerStrategy strategy = requestFactory.getStrategy(context.getAccessType());
            return strategy.handle(request, response, context);

        } catch (Exception e) {
            log.error("请求处理失败", e);
            return responseWriter.writeInternalErrorResponse(response);
        }
    }

    /**
     * 构建请求上下文（支持域名验证）
     */
    private RequestContext buildRequestContextWithDomain(HttpServletRequest request) {
        String apiPath = request.getRequestURI();
        String httpMethod = request.getMethod();
        String clientIp = ClientIpExtractor.getClientIp(request);
        String clientDomain = ClientDomainExtractor.getClientDomain(request);
        log.info("域名匹配--开始构建上下文对象");
        // 优先使用支持域名的构建方法
        RequestContext context = whitelistService.buildRequestContextWithDomain(apiPath, clientIp, clientDomain, httpMethod);

        // 如果域名验证失败，回退到IP验证
        if (context == null) {
            log.info("Ip匹配--开始构建上下文对象，当前clientIP为:{}", clientIp);
            context = whitelistService.buildRequestContext(apiPath, clientIp, httpMethod);
        }

        return context;
    }

    /**
     * 白名单验证（支持域名验证）
     */
    private boolean validateWhitelistWithDomain(RequestContext context, HttpServletRequest request) {
        String apiPath = request.getRequestURI();
        String httpMethod = request.getMethod();
        String clientIp = ClientIpExtractor.getClientIp(request);
        String clientDomain = ClientDomainExtractor.getClientDomain(request);

        // 使用支持域名的验证方法
        boolean hasAccess = whitelistService.validateRequest(context, apiPath, httpMethod, clientIp, clientDomain);

        context.setWhitelisted(hasAccess);
        requestContextHolder.setContext(context);

        if (!hasAccess) {
            log.warn("Whitelist validation failed, access denied: Request method:[{}], Request path:[{}], Request IP:[{}], Client domain:[{}], Request channel:[{}]",
                    httpMethod, apiPath, clientIp, clientDomain, context.getChannelName());
        } else {
            log.info("Whitelist validation successfully, access success: Request method:[{}], Request path:[{}], Request IP:[{}], Client domain:[{}], Request channel:[{}]",
                    httpMethod, apiPath, clientIp, clientDomain, context.getChannelName());
        }

        return hasAccess;
    }
}