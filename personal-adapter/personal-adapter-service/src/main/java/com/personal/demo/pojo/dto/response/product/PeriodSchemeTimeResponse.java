package com.personal.demo.pojo.dto.response.product;

import lombok.Data;

/**
 *
 * 餐段时间设置
 * @Author: fxs
 * @Date: 2025/8/18 9:58
 */
@Data
public class PeriodSchemeTimeResponse {

    /**
     * 餐段时间设置ID
     */
    private String pstId;

    /**
     * 餐段时间设置名称-- daycarryover.daycarryovername 一致
     */
    private String pstName;

    /**
     * -闭店时间
     */
    private String dayValue;

    /**
     * 早市id  【所有数据可以都传1】
     */
    private int ptId;
    /**
     * 餐段时间名称 默认‘早市’
     */
    private String ptName;

    /**
     * 是否启用早市 0-否 1-是
     */
    private Integer isNoEnable;

    /**
     * 早市开始时间-固定写死
     */
    private String ptStartTime;

    /**
     * 门店编码
     */
    private String ognIds;

    /**
     * 日结方式 0-自动日结 1-手动日结 2-手动日结+自动日结
     */
    private Integer dailyType = 0;

    /**
     * 餐段时间选择今天还是  0--昨天,默认为 1--今天
     */
    private Integer isDay  = 1;
}
