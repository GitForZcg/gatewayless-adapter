package com.personal.demo.rule.registry;

import com.personal.demo.rule.exception.AdapterException;
import com.personal.demo.rule.flow.UnifiedExternalServiceFlowProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.personal.demo.pojo.wrapper.AdapterErrorCode.FLOW_NOT_SUPPORT;


/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 外部默认调用流程注册器实现
 * @date 2025/7/4 10:56
 */
@Component
public class DefaultExternalFlowRegistry implements BaseExternalFlowRegistry {
    /**
     * 存储调用流程的映射
     */
    private final Map<String, UnifiedExternalServiceFlowProcessor<?>> flows = new HashMap<>();

    /**
     * 注入所有调用流程
     */
    @Autowired
    public DefaultExternalFlowRegistry(List<ExternalServiceFlowRegistration> registrations) {
        registrations.forEach(registration ->
                registerFlow(registration.getFlowType(), registration.getSearchFlow())
        );
    }

    /**
     * 注册调用流程
     */
    @Override
    public void registerFlow(String node, UnifiedExternalServiceFlowProcessor<?> flow) {
        flows.put(node, flow);
    }

    /**
     * 获取流程
     */
    @Override
    public UnifiedExternalServiceFlowProcessor<?> getFlow(String node) {
        UnifiedExternalServiceFlowProcessor<?> flow = flows.get(node);
        if (flow == null) {
            throw new AdapterException(FLOW_NOT_SUPPORT.code,
                    String.format(FLOW_NOT_SUPPORT.message, node),
                    FLOW_NOT_SUPPORT.type
            );
        }
        return flow;
    }

}