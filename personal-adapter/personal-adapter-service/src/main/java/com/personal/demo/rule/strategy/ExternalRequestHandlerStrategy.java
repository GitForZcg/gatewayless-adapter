package com.personal.demo.rule.strategy;

import com.personal.demo.pojo.base.RequestContext;
import com.personal.demo.pojo.wrapper.AdapterDemoResponse;
import com.personal.demo.rule.exception.AdapterSignException;
import com.personal.demo.rule.factory.BaseExternalFlowManagerFactory;
import com.personal.demo.rule.handler.SignatureProcessor;
import com.personal.demo.utils.ResponseWriter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 外部请求处理策略
 * @date 2025/7/4 10:58
 */
@Component
@Slf4j
public class ExternalRequestHandlerStrategy implements RequestHandlerStrategy {

    private final BaseExternalFlowManagerFactory flowManagerFactory;
    private final ResponseWriter responseWriter;

    private final SignatureProcessor signatureProcessor;

    public ExternalRequestHandlerStrategy(BaseExternalFlowManagerFactory flowManagerFactory,
                                          ResponseWriter responseWriter, SignatureProcessor signatureProcessor) {
        this.flowManagerFactory = flowManagerFactory;
        this.responseWriter = responseWriter;
        this.signatureProcessor = signatureProcessor;
    }

    /**
     * 请求处理入口
     */
    @Override
    public boolean handle(HttpServletRequest request, HttpServletResponse response, RequestContext context) {
        log.info("外部请求允许访问，拦截器中处理: 请求方式:[{}],请求路径:[{}],请求渠道:[{}]",
                request.getMethod(), request.getRequestURI(), context.getChannelName());
        try {
            // 处理外部请求
            Object result = flowManagerFactory.handleRequest(request, context);
            String sign = signatureProcessor.calculateSignature(result, context);
            return responseWriter.writeSuccessResponse(response, AdapterDemoResponse.success(result), sign);
        } catch (AdapterSignException e) {
            return responseWriter.writeBusinessErrorResponse(response, e.getCode(), e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("当前渠道[{}]没有可用适配器", context.getChannelName());
            return responseWriter.writeNotImplementedResponse(response,
                    "当前渠道[" + context.getChannelName() + "]没有可用适配器");
        } catch (UnsupportedOperationException e) {
            log.error("API请求不支持:{}", e.getMessage());
            return responseWriter.writeNotFoundResponse(response, e.getMessage());
        } catch (Exception e) {
            log.error("渠道[{}]的请求处理失败", context.getChannelName(), e);
            return responseWriter.writeInternalErrorResponse(response);
        }
    }
}