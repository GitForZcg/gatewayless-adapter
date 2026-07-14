package com.personal.demo.rule.factory;

import com.personal.demo.enu.external.base.ExternalBaseNode;
import com.personal.demo.pojo.base.RequestContext;
import com.personal.demo.rule.convert.DataAdapterConvert;
import com.personal.demo.rule.flow.UnifiedExternalServiceFlowProcessor;
import com.personal.demo.rule.handler.BeanValidationProcessor;
import com.personal.demo.rule.handler.SignatureValidationProcessor;
import com.personal.demo.rule.initalizer.ExternalServiceNodeInitializer;
import com.personal.demo.rule.registry.BaseExternalFlowRegistry;
import com.personal.demo.rule.registry.ExternalNodeRegistry;
import com.personal.demo.rule.registry.ExternalServiceNodeRegistry;
import com.personal.demo.rule.strategy.ExternalBaseNodeHandleStrategy;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 外部请求统一处理工厂
 * @date 2025/7/4 10:49
 */
@Component
public class BaseExternalFlowManagerFactory {
    private final BeanValidationProcessor validationProcessor;
    private final DataAdapterConvert dataAdapterConvert;

    private final BaseExternalFlowRegistry flowRegistry;

    private final Map<ExternalBaseNode, ExternalBaseNodeHandleStrategy> actionHandlers;

    private final ExternalNodeRegistry nodeRegistry;

    private final SignatureValidationProcessor signatureValidationProcessor;


    public BaseExternalFlowManagerFactory(BeanValidationProcessor validationProcessor, DataAdapterConvert dataAdapterConvert, BaseExternalFlowRegistry flowRegistry, List<ExternalServiceNodeRegistry> configs, ExternalNodeRegistry nodeRegistry, SignatureValidationProcessor signatureValidationProcessor) {
        this.validationProcessor = validationProcessor;
        this.dataAdapterConvert = dataAdapterConvert;
        this.flowRegistry = flowRegistry;
        this.actionHandlers = ExternalServiceNodeInitializer.initNodes(configs);
        this.nodeRegistry = nodeRegistry;
        this.signatureValidationProcessor = signatureValidationProcessor;
    }

    @SuppressWarnings("unchecked")
    public <R> R handleRequest(HttpServletRequest request, RequestContext context) throws Exception {

        //获取执行节点
        ExternalBaseNode node = nodeRegistry.getNode(context.getApiType(), context.getApiSubType());
        //获取目标类并转换参数
        Object params = dataAdapterConvert.convertData(request, context);
        //执行验签
        signatureValidationProcessor.validateSignature(params, context);
        //验证参数
        Class<?> validationGroup = validationProcessor.getValidationGroup(node);
        validationProcessor.validateExternalParams(params, validationGroup);

        UnifiedExternalServiceFlowProcessor<?> flow = flowRegistry.getFlow(context.getApiType());
        //获取处理器
        ExternalBaseNodeHandleStrategy handler = actionHandlers.get(node);

        return (R) handler.handle(flow, params, node);
    }

}
