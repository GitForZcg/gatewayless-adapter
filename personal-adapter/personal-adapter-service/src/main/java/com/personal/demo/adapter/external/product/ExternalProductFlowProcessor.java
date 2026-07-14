package com.personal.demo.adapter.external.product;

import com.personal.demo.rule.flow.UnifiedExternalServiceFlowProcessor;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 外部接口流程实现
 * @date 2025/7/4 10:42
 */
public interface ExternalProductFlowProcessor extends UnifiedExternalServiceFlowProcessor<Object> {

    /**
     * 1.1 菜项同步预热 将下面接口存到数据库
     */
    Object pluSyncPreheat(Object params);

    /**
     * 1.2 获取门店菜项集合接口
     */
    Object pluAll(Object params);


    /**
     * 1.3 获取门店配料(规格信息)
     */
    Object pluCondiment(Object params);

    /**
     * 1.4 获取门店套餐列表
     */
    Object getComboDetail(Object params);

    /**
     * 1.5 获取品牌级菜项基础数据(单品与套餐)
     */
    Object pluMasterAll(Object params);


    /**
     * 1.7 获取菜单设置信息(菜单)
     */
    Object getMenuSettings(Object params);

    /**
     * 1.8 获取门店日结信息
     */
    Object getDailySummary(Object params);

}
