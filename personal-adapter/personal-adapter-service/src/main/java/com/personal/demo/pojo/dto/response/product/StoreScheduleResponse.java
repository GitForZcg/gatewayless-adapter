package com.personal.demo.pojo.dto.response.product;

import lombok.Data;

/**
 * 获取门店日结餐段设置
 * 主 DTO 类，包含 daycarryover 和 periodschemetime
 *
 * @Author: fxs
 * @Date: 2025/8/18 9:55
 */
@Data
public class StoreScheduleResponse {


    /**
     * 门店日结设置
     */
    private DayCarryoverResponse dayCarryover;

    /**
     * 门店餐段时间设置
     */
    private PeriodSchemeTimeResponse periodSchemeTime;
}
