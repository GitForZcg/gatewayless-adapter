package com.personal.demo.rule.factory;

import com.personal.demo.enu.ServiceType;
import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.request.base.AbstractBaseParams;
import com.personal.demo.rule.convert.BaseParamsConverter;
import com.personal.demo.rule.flow.UnifiedInternalServiceFlowProcessor;
import com.personal.demo.rule.initalizer.InternalServiceNodeInitializer;
import com.personal.demo.rule.registry.BaseInternalFlowRegistry;
import com.personal.demo.rule.registry.InternalServiceNodeRegistry;
import com.personal.demo.rule.strategy.BaseNodeHandleStrategy;
import com.personal.demo.rule.handler.BeanValidationProcessor;
import com.common.base.exception.BizException;
import com.common.base.util.BizPreconditions;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static com.personal.demo.consts.BizCode.*;


/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 流程统一管理器
 * @date 2025/7/2 10:49
 */
@Component
public class BaseFlowManagerFactory {
    private final BaseInternalFlowRegistry flowRegistry;
    private final BaseParamsConverterFactory converterFactory;
    private final Map<BaseNode, BaseNodeHandleStrategy> actionHandlers;
    private final BeanValidationProcessor validationProcessor;

    @Autowired
    public BaseFlowManagerFactory(BaseInternalFlowRegistry searchFlowRegistry, BaseParamsConverterFactory converterFactory, BeanValidationProcessor validationProcessor, List<InternalServiceNodeRegistry> nodeRegistries) {
        this.validationProcessor = validationProcessor;
        this.flowRegistry = searchFlowRegistry;
        this.converterFactory = converterFactory;
        this.actionHandlers = InternalServiceNodeInitializer.initNodes(nodeRegistries);
    }

    @SuppressWarnings("unchecked")
    public <T extends AbstractBaseParams, R> R executeFlow(ServiceType serviceType,
                                                           BaseNode node,
                                                           Object params) throws Exception {
        //参数转化器
        BaseParamsConverter<T> converter = converterFactory.getConverter(serviceType);
        //参数转化
        T convertedParams = converter.convert(serviceType, node, params);
        //参数提取
        Object bizData = convertedParams.getBizData();
        //验证状态
        BizPreconditions.checkState(ObjectUtils.isNotEmpty(bizData), PARAM_VALID, String.format("请求参数不正确:%s对象不能为空", "BIZ_DATA"));
        //验证分组
        Class<?> validationGroup = validationProcessor.getValidationGroup(node);
        //校验参数
        validationProcessor.validate(bizData, validationGroup);
        //获取流程
        UnifiedInternalServiceFlowProcessor<T> flow = (UnifiedInternalServiceFlowProcessor<T>) flowRegistry.getFlow(serviceType);
        //获取处理器
        BaseNodeHandleStrategy handler = actionHandlers.get(node);
        //验证动作
        BizPreconditions.checkState(ObjectUtils.isNotEmpty(handler), NODE_NO_FOUND, String.format("不支持的调用动作[%s]", node));
        //执行动作
        try {
            return (R) handler.handle(flow, convertedParams, node);
        } catch (UnsupportedOperationException e) {
            throw new BizException(NODE_NOT_SUPPORTED, String.format("当前调用方式[%s],不支持该动作:[%s]", serviceType, node));
        }
    }

}