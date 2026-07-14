package com.personal.demo.pojo.dto.response.product;

import lombok.Data;

/**
 * 配菜组明细信息DTO
 *
 * @Author: fxs
 * @Date: 2025/8/11 16:53
 */
@Data
public class PluComboGroupDetailResponse {

    /** 菜品代码(单品代码) */
    private String menuItemCode;

    /** 菜品名称（原文） */
    private String menuItemName;

    /** 菜品名称（中文） */
    private String menuItemNameZh;

    /** 菜品价格 */
    private String menuItemPrice;

    /** 税码 */
    private String taxCode;

    /** 销售项目 */
    private String salesItem;

    /**
     * 配菜最大可点
     */
    private String maxCount;

    /**
     *是否默认选中
     */
    private String isDefault;
}
