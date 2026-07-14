package com.personal.demo.pojo.dto.response.product;

import lombok.Data;

import java.util.List;

/**
 * 门店套餐信息返回
 *
 * @Author: fxs
 * @Date: 2025/8/11 16:42
 */
@Data
public class PluComboResponse {

    /** 套餐分类代码 */
    private String sortCode;

    /** 套餐描述（中文） */
    private String descriptZh;

    /** 套餐描述（原文） */
    private String descript;

    /** 套餐组合菜单项 */
    private String comboMenuItem;

    /** 套餐价格 */
    private String price;

    /** 主菜套餐组代码 */
    private String mainItemComboGroupCode;

    /** 门店分类代码 */
    private String storeCategoryCode;

    /** 门店分类名称 */
    private String storeCategoryName;

    /** 套餐配菜列表 */
    private List<PluComboSideResponse> comboSideList;
}
