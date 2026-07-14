package com.personal.demo.response.compute;

import lombok.Data;

import java.io.Serializable;

/**
 * @author sulu
 * @date 2026年02月04日 6:04 PM
 */
@Data
public class SpecialDishListDto implements Serializable {

    /**
     * 产品 code
     */
    private String productCode;
    /**
     * 菜品名称
     */
    private String productName;

    /**
     * 特价（单位：分，如1500=15元）
     */
    private Integer specialPrice;

    /**
     * 原价（单位：分）
     */
    private Integer originalPrice;
    /**
     * 规则ID
     */
    private String ruleId;
    /**
     * 规则名称
     */
    private String ruleName;
}
