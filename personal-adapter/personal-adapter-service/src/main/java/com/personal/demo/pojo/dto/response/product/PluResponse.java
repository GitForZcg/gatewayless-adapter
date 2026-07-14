package com.personal.demo.pojo.dto.response.product;

import lombok.Data;

import java.util.List;

/**
 * 菜项(产品)的具体信息
 *
 * @Author: fxs
 * @Date: 2025/8/11 15:46
 */
@Data
public class PluResponse {

    /** 菜品代码(产品代码) */
    private String pluCode;

    /** 菜品描述 */
    private String descript;

    /** 菜品中文描述 */
    private String descriptZh;

    /** 价格 */
    private String price;

    /** 税码 */
    private String taxCode;

    /** 销售项代码 */
    private String salesItem;

    /** 配料列表 */
    private List<TpResponse> tpList;

    /** 门店类别代码 */
    private String storeCategoryCode;

    /** 门店类别名称 */
    private String storeCategoryName;
}
