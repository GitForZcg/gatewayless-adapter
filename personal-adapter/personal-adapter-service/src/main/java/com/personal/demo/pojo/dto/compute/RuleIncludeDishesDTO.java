package com.personal.demo.pojo.dto.compute;

import com.personal.demo.pojo.base.DemoComputeMd5Param;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * @author sulu
 * @date 2026年01月23日 2:49 PM
 */
@Data
public class RuleIncludeDishesDTO implements DemoComputeMd5Param, Serializable {
    /**
     * SKU ID
     */
    private String skuId;
    /**
     * 菜品ID
     */
    private String dishesId;
    /**
     * 菜品名称
     */
    private String dishesName;
    /**
     * 排除类型
     */
    private Integer exclusionType;
    /**
     * 排除非抵扣项
     */
    private Integer excludeNonDeduct;
    /**
     * 触发类型
     */
    private Integer triggerType;
    /**
     * 触发值
     */
    private Integer triggerValue;
    /**
     * 折扣比例（85代表85折）
     */
    private Integer discountRatio;


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
