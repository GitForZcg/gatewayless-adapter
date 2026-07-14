package com.personal.demo.adapter.internal.trade;

import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.enu.internal.node.TradeNode;
import com.personal.demo.request.base.BaseParams;
import com.personal.demo.request.trade.OrderCommitCancelParam;
import com.personal.demo.request.trade.OrderCommitParam;
import com.personal.demo.request.trade.OrderPreviewParam;
import com.personal.demo.rule.handler.AbstractFlowProcessor;
import com.personal.demo.serivce.trade.ITradeService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * trade执行流程
 *
 * @Author:
 * @Date:
 */
@SuppressWarnings("rawtypes")
@Component
public class TradeFlow extends AbstractFlowProcessor<BaseParams> implements TradeFlowProcessor {

    @Resource
    private ITradeService iTradeService;

    @Override
    public String getProcessorType() {
        return "TRADE";
    }

    @Override
    protected Map<BaseNode, BaseNodeFunction<BaseParams>> initNodeHandlers() {
        return Map.of(
                TradeNode.TRADE_ORDER_PREVIEW, this::orderPreview,
                TradeNode.TRADE_ORDER_COMMIT, this::orderCommit,
                TradeNode.TRADE_ORDER_CANCEL, this::orderCancel
        );
    }


    @Override
    public Object orderPreview(BaseParams params, BaseNode node) throws Exception {
        return iTradeService.orderPreview((OrderPreviewParam) params.getBizData(), node);
    }

    @Override
    public Object orderCommit(BaseParams params, BaseNode node) throws Exception {
        return iTradeService.orderCommit((OrderCommitParam) params.getBizData(), node);
    }

    @Override
    public Object orderCancel(BaseParams params, BaseNode node) throws Exception {
        return iTradeService.orderCancel((OrderCommitCancelParam) params.getBizData(), node);
    }
}
