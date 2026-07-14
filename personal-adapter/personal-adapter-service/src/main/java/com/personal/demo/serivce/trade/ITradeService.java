package com.personal.demo.serivce.trade;

import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.request.trade.OrderCommitCancelParam;
import com.personal.demo.request.trade.OrderCommitParam;
import com.personal.demo.request.trade.OrderPreviewParam;
import com.personal.demo.response.trade.OrderCommitCancelResultDto;
import com.personal.demo.response.trade.OrderCommitResultDto;
import com.personal.demo.response.trade.OrderPreviewResultDto;


/**
 * trade服务
 *
 * @Author:
 * @Date:
 */
public interface ITradeService {

    /**
     * 订单预览接口
     *
     * @param bizData
     * @param node
     * @return
     */
    OrderPreviewResultDto orderPreview(OrderPreviewParam bizData, BaseNode node);

    OrderCommitResultDto orderCommit(OrderCommitParam bizData, BaseNode node);

    OrderCommitCancelResultDto orderCancel(OrderCommitCancelParam bizData, BaseNode node);

    /**
     * 查询门店信息
     * @param bizData
     * @param node
     */
    //NdShopDTO queryStore(OrderPreviewParam bizData, BaseNode node);

}
