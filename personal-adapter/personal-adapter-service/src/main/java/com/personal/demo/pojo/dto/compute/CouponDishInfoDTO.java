package com.personal.demo.pojo.dto.compute;

import lombok.Data;

import java.io.Serializable;

/**
 * @author sulu
 * @date 2026年03月11日 7:17 PM
 */
@Data
public class CouponDishInfoDTO implements Serializable {
    /**
     * 数量
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
     * 金额（单位：分，10000 代表 100 元）
     */
    private String money;

    /**
     * 优惠券名称
     */
    private String name;

    /**
     * 模板ID
     */
    private String templateId;

    /**
     * 优惠券标题
     */
    private String title;

    /**
     * 优惠券类型
     */
    private String type;
}
