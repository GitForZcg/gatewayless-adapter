package com.personal.demo.pojo.dto.compute;

import com.personal.demo.pojo.base.DemoComputeMd5Param;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * 特价菜品列表
 *
 * @author sulu
 * @date 2026年01月23日 2:52 PM
 */
@Data
public class RuleSpecialDishesDTO implements DemoComputeMd5Param, Serializable {
    /**
     * SKU ID
     */
    private String skuId;
    /**
     * 菜品ID
     */
    private String dishesId;
    /**
     * 产品 code
     */
    private String dishesNo;
    /**
     * 菜品名称
     */
    private String dishesName;
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


    public boolean resultCheck(RuleSpecialDishesDTO data) {
        if (Objects.isNull(data) || Objects.isNull(data.getDishesNo()) || Objects.isNull(data.getSpecialPrice())) {
            return false;
        }
        return true;
    }

}
