package com.personal.demo.rule.flow;

import com.personal.demo.enu.external.base.ExternalBaseNode;

import java.util.Set;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 外部调用流程接口规则
 * @date 2025/7/4 10:51
 */
public interface UnifiedExternalServiceFlowProcessor<T> {

    /**
     * 根据节点执行相应的业务逻辑
     */
    Object execute(ExternalBaseNode node, T params) throws Exception;

    /**
     * 获取支持的节点列表
     */
    Set<ExternalBaseNode> getSupportedNodes();

    /**
     * 获取流程处理器类型
     */
    String getProcessorType();

}
