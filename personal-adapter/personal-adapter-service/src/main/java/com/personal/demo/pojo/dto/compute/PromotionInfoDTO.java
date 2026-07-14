package com.personal.demo.pojo.dto.compute;

import com.personal.demo.pojo.base.DemoComputeMd5Param;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * 促销信息
 * @author sulu
 * @date 2026年01月23日 2:47 PM
 */
@Data
public class PromotionInfoDTO implements DemoComputeMd5Param, Serializable {

    /**
     * 活动ID
     */
    private String id;
    /**
     * 规则名称
     */
    private String ruleName;
    /**
     * 规则标签
     */
    private String ruleTag;
    /**
     * 规则来源
     */
    private String ruleSource;
    /**
     * 包含菜品类型
     */
    private Integer includeDishesType;
    /**
     * 分类标识
     */
    private Integer categoryFlag;
    /**
     * 排除支付方式ID列表
     */
    private List<Integer> excludePaymentIdList;
    /**
     * 排除支付方式列表（空数组）
     */
    private List<Object> excludePaymentList;
    /**
     * 包含菜品列表
     */
    private List<RuleIncludeDishesDTO> includeDishesList;
    /**
     * 排除促销活动列表（空数组）
     */
    private List<Object> excludePromotionList;
    /**
     * 可叠加促销活动ID列表
     */
    private List<Integer> additivityPromotionIdList;
    /**
     * 排除规则列表
     */
    private List<CouponExcludeDTO> excludeRules;
    /**
     * 菜品资产互斥列表
     */
    private List<DishesCouponExcludeDTO> dishesAssetExcludeList;

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
