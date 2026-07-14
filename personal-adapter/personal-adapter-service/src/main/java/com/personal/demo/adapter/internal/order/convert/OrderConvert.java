package com.personal.demo.adapter.internal.order.convert;

import com.personal.demo.adapter.internal.order.convert.impl.OrderSyncData;
import com.personal.demo.dto.order.DemoOrderDTO;

/**
 * @author Li QianQian
 * describe:
 */
public interface OrderConvert {

    /**
     * 换取sku
     * @param orderNo
     * @return
     */
    boolean queryOrderSyncResult(String orderNo);

    /**
     * 保存同步中log
     * @param orderSyncData
     */
    void saveSynchronizingLog(OrderSyncData orderSyncData, DemoOrderDTO demoOrderDTO);

    /**
     * 保存异常log
     * @param orderSyncData
     */
    void saveErrorSyncLog(OrderSyncData orderSyncData,DemoOrderDTO demoOrderDTO);


    /**
     * 处理适配样例数据
     * @param orderSyncData
     */
    void handleDemoData(OrderSyncData orderSyncData,DemoOrderDTO demoOrderDTO);

    /**
     * 查询sku信息
     * @param orderSyncData
     * @return  sku信息列表
     */
    void querySku(OrderSyncData orderSyncData,DemoOrderDTO demoOrderDTO);

    /**
     * 过滤sku
     * @param orderSyncData
     */
    void filterSku(OrderSyncData orderSyncData,DemoOrderDTO demoOrderDTO);

    /**
     * 查询门店信息
     * @param orderSyncData
     * @return
     */
    void queryStore(OrderSyncData orderSyncData,DemoOrderDTO demoOrderDTO);


    /**
     * 同步适配样例订单
     * @param orderSyncData
     */
    void syncDemoOrder(OrderSyncData orderSyncData,DemoOrderDTO demoOrderDTO);





}
