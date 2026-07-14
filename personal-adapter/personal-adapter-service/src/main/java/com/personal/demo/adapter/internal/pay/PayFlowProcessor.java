package com.personal.demo.adapter.internal.pay;

import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.request.base.BaseParams;
import com.personal.demo.rule.flow.UnifiedInternalServiceFlowProcessor;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 支付流程实现
 * @date 2025/7/2 10:45
 */
@SuppressWarnings("rawtypes")
public interface PayFlowProcessor extends UnifiedInternalServiceFlowProcessor<BaseParams> {

    Object payPrepare(BaseParams params, BaseNode node) throws Exception;

    Object payQuery(BaseParams params, BaseNode node) throws Exception;

    Object refund(BaseParams params, BaseNode node) throws Exception;

    Object refundQuery(BaseParams params, BaseNode node) throws Exception;

}
