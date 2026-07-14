package com.personal.demo.adapter.internal.order.convert.impl;

import com.personal.demo.dto.order.DemoBillDetailedDTO;
import com.personal.demo.dto.order.SkuDto;
import com.personal.demo.dto.order.SyncOrderBindingStoreDto;
import com.personal.product.internal.SkuCodeDto;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author Li QianQian
 * describe:
 */
@Data
public class OrderSyncData implements Serializable {

    /**
     * 过滤后的菜品详情列表
     */
    private List<DemoBillDetailedDTO> filterBillDetailList;

    /**
     * 菜品详情id对应的规格列表
     */
    private Map<String, List<String>> repeatBillDetailTpsMap;

    /**
     * 产品-sku列表
     */
    private List<SkuCodeDto> skuCodeResultList;

    /**
     * 绑定门店
     */
    private SyncOrderBindingStoreDto syncOrderBindingStoreDto;

    /**
     * 扣减库存map
     */
    private Map<String, SkuDto> reduceInventorySkuMap;

    /**
     * 异常sku列表
     */
    private List<SkuDto> errorSkuList;


}
