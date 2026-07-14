package com.personal.demo.rule.flow;

import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.request.base.AbstractBaseParams;

import java.util.Set;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 内部流程接口规则
 * @date 2025/7/2 10:51
 */

public interface UnifiedInternalServiceFlowProcessor<T extends AbstractBaseParams> {

    /**
     * 根据节点执行相应的业务逻辑
     */
    Object execute(BaseNode node, T params) throws Exception;

    /**
     * 获取支持的节点列表
     */
    Set<BaseNode> getSupportedNodes();

    /**
     * 获取流程处理器类型
     */
    String getProcessorType();

}
