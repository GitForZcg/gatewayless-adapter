package com.personal.demo.pojo.dto.response.product;

import lombok.Data;

/**
 * 日结时间设置
 * @Author: fxs
 * @Date: 2025/8/18 9:57
 */
@Data
public class DayCarryoverResponse {

    /**
     * 日结主键id-门店主键ID -不传
     */
    private String dayCarryoverId;

    /**
     * 日结名称
     */
    private String dayCarryoverName;

    /**
     * 日结编码
     */
    private String dayCarryoverNo;

    /**
     * 门店id【逗号隔开可以传多个门店id】
     */
    private String ognIds;

    /**
     * 日结时间
     */
    private String dayValue;

    /**
     * 日结方式 0-自动日结 1-手动日结 2-手动日结+自动日结
     */
    private String dailyType = "0";

    /**
     * 状态 -0-正常 1-停用 2-逻辑删除
     */
    private String status;

    /**
     * 日结排序
     */
    private String order;

    /**
     * 餐段时间选择今天还是  0--昨天,默认为 1--今天
     */
    private String isDay = "0";
}
