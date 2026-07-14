package com.personal.demo.pojo.dto.compute;

import com.personal.demo.pojo.base.DemoComputeMd5Param;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 门店促销活动列表
 * @author sulu
 * @date 2026年01月23日 2:23 PM
 */
@Data
public class PromotionStoreDTO implements DemoComputeMd5Param, Serializable {

    /**
     * 促销活动列表
     */
    private List<PromotionInfoDTO> promotionList;
    /**
     * 是否享受会员价（字符串类型）
     */
    private String enjoyMemberPrice;
    /**
     * 享受会员价的等级列表
     */
    private List<String> enjoyMemberPriceGradeList;
    /**
     * 享受会员价的人群列表
     */
    private List<RuleFlagDomainDTO> enjoyMemberPricePeopleList;
    /**
     * 特价菜品列表
     */
    private List<RuleSpecialDishesDTO> specialDishesList;
    /**
     * 规则菜品标签映射（key：菜品ID，value：标签对象）
     */
    private Map<String, RuleDishesTagDTO> ruleDishesTagMap;
    /**
     * 会员店铺列表
     */
    private List<RuleIncludeMemberShopDTO> memberShops;
    /**
     * 弹窗类型
     */
    private Integer popupType;
    /**
     * 推荐样式
     */
    private Integer recommendStyle;
    /**
     * 推荐标题
     */
    private String recommendTitle;
    /**
     * 推荐背景图URL
     */
    private String recommendBg;
    /**
     * 推荐详情背景图URL
     */
    private String recommendDetailBg;
    /**
     * 推荐逻辑
     */
    private Integer recommendLogic;
    /**
     * 是否执行优惠
     */
    private Integer executeDiscount;
    /**
     * 余额限制
     */
    private Integer balanceConfine;

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
