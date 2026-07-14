package com.personal.demo.adapter.internal.trade;

import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.request.base.BaseParams;
import com.personal.demo.rule.flow.UnifiedInternalServiceFlowProcessor;

/**
 * trade服务
 *
 * @Author:
 * @Date:
 */
@SuppressWarnings("rawtypes")
public interface TradeFlowProcessor extends UnifiedInternalServiceFlowProcessor<BaseParams> {

    /**
     * 交易预览接口
     *
     * @param params
     * @param node
     * @return
     * @throws Exception
     */
    Object orderPreview(BaseParams params, BaseNode node) throws Exception;

    Object orderCommit(BaseParams params, BaseNode node) throws Exception;

    Object orderCancel(BaseParams params, BaseNode node) throws Exception;


}
