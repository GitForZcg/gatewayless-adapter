package com.personal.demo.serivce;

import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.pojo.dto.request.product.ExternalProductCommonRequest;
import com.personal.demo.pojo.dto.request.product.ExternalProductDetailRequest;
import com.personal.demo.pojo.dto.response.product.*;
import com.personal.demo.request.product.ProductNotificationParam;

import java.util.List;

/**
 * 外部系统菜项服务
 * @author 86136
 */
public interface IExternalProductService {

    /**
     * 1.1 菜项同步预热 将下面接口存到数据库
     */
    Boolean pluSyncPreheat(ExternalProductDetailRequest request);

    /**
     * 1.2 获取门店菜项集合接口
     */
    List<PluAllResponse> pluAll(ExternalProductDetailRequest request);

    /**
     * 1.3 获取门店配料(规格信息)
     */
    List<PluSpecResponse> pluCondiment(ExternalProductDetailRequest request);

    /**
     * 1.4 获取门店套餐列表
     */
    List<PluComboResponse> getComboDetail(ExternalProductDetailRequest request);

    /**
     * 1.5 获取品牌级菜项基础数据(单品与套餐)
     */
    List<PluMasterAllResponse> pluMasterAll(ExternalProductCommonRequest request);


    /**
     * 1.7 获取菜单设置信息(菜单)
     */
    List<PluMenuDataResponse> getMenuSettings(ExternalProductCommonRequest request);


    List<StoreScheduleResponse> getDailySummary(ExternalProductCommonRequest request);


}
