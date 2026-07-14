package com.personal.demo.pojo.dto.response.product;

import lombok.Data;

import java.util.List;

/**
 * 套餐配菜信息DTO
 *
 * @Author: fxs
 * @Date: 2025/8/11 16:52
 */
@Data
public class PluComboSideResponse {

    /** 配菜组代码(套餐分类) */
    private String comboGroupCode;

    /** 配菜组名称（中文） */
    private String comboGroupNameZh;

    /** 配菜组名称（原文） */
    private String comboGroupName;

    /** 配菜的商品类型（1:普通菜品, 2:套餐） */
    private Integer pdType;

    /** 配菜的请求类型（1:即食, 2:预点） */
    private Integer rpdType;

    /** 配菜数量 */
    private Integer count;

    /** 配菜最小数量 */
    private Integer minCount;

    /** 配菜最大数量 */
    private Integer maxCount;

    /** 配菜是否为提醒项（1:是, 0:否） */
    private Integer isPrompty;

    /** 配菜明细列表 */
    private List<PluComboGroupDetailResponse> comboGroupDetail;
}
