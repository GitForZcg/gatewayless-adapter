package com.personal.demo.response.compute;

import lombok.Data;

import java.io.Serializable;

/**
 * @author sulu
 * @date 2026年02月10日 4:27 PM
 */
@Data
public class CouponCashUseDTO implements Serializable {
    /**
     * 数量/金额
     */
    private String amount;

    /**
     * 优惠券适用类型
     */
    private Integer couponApplicableType;

    /**
     * 优惠券折扣类型
     */
    private Integer couponDiscountType;

    /**
     * 优惠券ID
     */
    private String couponId;

    /**
     * 优惠券面额
     */
    private String money;

    /**
     * 优惠券名称
     */
    private String name;

    /**
     * 优惠券模板ID
     */
    private String templateId;

    /**
     * 优惠券标题
     */
    private String title;

    /**
     * 优惠券类型1-代金券，2-菜品券
     */
    private String type;

    /**
     * 优惠券类型细分-枚举
     */
    private String ceType;

}
