package com.personal.demo.pojo.dto.compute;

import com.personal.demo.pojo.base.DemoComputeMd5Param;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * @author sulu
 * @date 2026年02月10日 4:54 PM
 */
@Data
public class BatchSummaryItemDTO implements DemoComputeMd5Param, Serializable {

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


    @Override
    public Set<String> needSign() {
        return null;
    }

    @Override
    public Set<String> needSignParam() {
        return null;
    }

    @Override
    public String orderId() {
        return null;
    }
}
