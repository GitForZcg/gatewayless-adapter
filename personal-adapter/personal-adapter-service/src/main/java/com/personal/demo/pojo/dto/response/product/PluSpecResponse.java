package com.personal.demo.pojo.dto.response.product;

import lombok.Data;

import java.util.List;

/**
 * 门店配料(规格)信息返回
 *
 * @Author: fxs
 * @Date: 2025/8/11 16:30
 */
@Data
public class PluSpecResponse {

    /** 菜品分类代码 */
    private String sortCode;

    /** 菜品分类描述（中文） */
    private String sortCodeDescriptZh;

    /** 菜品分类描述（原文） */
    private String sortCodeDescript;

    /** 是否选中（1表示选中） */
    private Integer selectFlag;

    /** 排序顺序 */
    private Integer order;

    /** 该分类下的PLU列表 */
    private List<PluSubSpecResponse> pluList;
}
