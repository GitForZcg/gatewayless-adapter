package com.personal.demo.response.compute;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * @author sulu
 * @date 2026年02月10日 5:36 PM
 */
@Data
public class ViewDishesItemResDto implements Serializable {
    /**
     * SKU ID
     */
    private String skuId;

    /**
     * 菜品ID
     */
    private Long dishesId;

    /**
     * 规格ID
     */
    private Long sizeId;

    /**
     * 价格类型:1原价 2会员价 3特价 4加价购 5赠品 6买赠
     */
    private String mathFlag;

    /**
     * 计算价格(单位:分)
     */
    private Integer mathPrice;

    /**
     * 原售卖金额(单位:分)
     */
    private Integer salePrice;

    /**
     * 数量
     */
    private Integer count;

    /**
     * 分组标识
     */
    private String groupFlag;

    /**
     * 菜品所享活动标签集合
     */
    private Set<String> ruleTags;

    /**
     * 批次号
     */
    private Integer batchNum;

    /**
     * 是否更改过
     */
    private Boolean isUpdate;


}
