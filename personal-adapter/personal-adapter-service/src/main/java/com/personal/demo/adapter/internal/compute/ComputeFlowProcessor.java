package com.personal.demo.adapter.internal.compute;

import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.request.base.BaseParams;
import com.personal.demo.rule.flow.UnifiedInternalServiceFlowProcessor;

/**
 * @author sulu
 * @version 1.0
 * @description: 算价流程实现
 * @date 2026/1/16 10:43
 */
@SuppressWarnings("rawtypes")
public interface ComputeFlowProcessor extends UnifiedInternalServiceFlowProcessor<BaseParams> {

    Object promotionCompute(BaseParams params, BaseNode node) throws Exception;

    Object cartPriceCompute(BaseParams params, BaseNode node) throws Exception;

    Object preOrderPrice(BaseParams baseParams, BaseNode baseNode) throws Exception;

}
