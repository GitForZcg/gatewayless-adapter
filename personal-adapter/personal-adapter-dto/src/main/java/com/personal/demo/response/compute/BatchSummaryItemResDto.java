package com.personal.demo.response.compute;

import lombok.Data;

import java.io.Serializable;

/**
 * @author sulu
 * @date 2026年02月10日 4:54 PM
 */
@Data
public class BatchSummaryItemResDto implements Serializable {

    /**
     * 批次号
     */
    private Integer batchNum;

    /**
     * 本批次小计金额(单位:分)
     */
    private Integer summaryAmount;

    /**
     * 本批次菜品原价合计(单位:分)
     */
    private Integer originalAmount;

    /**
     * 本批次实收金额(单位:分)
     */
    private Integer finalAmount;

    /**
     * 本批次餐盒费合计(单位:分)
     */
    private Integer boxAmountSum;

}
