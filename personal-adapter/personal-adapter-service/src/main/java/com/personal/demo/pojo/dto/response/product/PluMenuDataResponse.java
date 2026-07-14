package com.personal.demo.pojo.dto.response.product;

import lombok.Data;

/**
 * 门店菜谱的详细信息
 *
 * @Author: fxs
 * @Date: 2025/8/11 16:58
 */
@Data
public class PluMenuDataResponse {

    /** 菜谱编号(菜单) */
    private String menuNo;

    /** 菜谱名称 */
    private String menuName;

    /** 组织ID（具体含义暂定） */
    private String ognids;

    /** 状态（0：正常，1：停用等） */
    private Integer status;

    /** 菜谱开始时间 */
    private String menuStartTime;

    /** 排序号 */
    private Integer order;

    /** 外卖标识（0：不外卖，1：外卖） */
    private Integer takeoutFoodFlag;
}
