package com.personal.demo.request.trade;

import lombok.Data;

/**
 * @author Li QianQian
 * describe:
 */
@Data
public class DiscountCouponParam {
    /**
     * 用户券id
     */
    private String user_coupon_id;

    /**
     * 券面值 单位:分
     */
    private Integer deno;
}
