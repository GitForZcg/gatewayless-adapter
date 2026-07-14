package com.personal.demo.adapter.internal.order;

import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.request.base.BaseParams;
import com.personal.demo.rule.flow.UnifiedInternalServiceFlowProcessor;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 订单流程实现
 * @date 2025/7/2 10:43
 */
@SuppressWarnings("rawtypes")
public interface OrderFlowProcessor extends UnifiedInternalServiceFlowProcessor<BaseParams> {

    Object query(BaseParams params, BaseNode node) throws Exception;

    Object pushOrder(BaseParams params, BaseNode node) throws Exception;

    Object pushOffSetOrder(BaseParams params, BaseNode node) throws Exception;

}
