package com.personal.demo.request.trade;

import lombok.Data;

/**
 * @author Li QianQian
 * describe:
 */
@Data
public class DiyGiftCouponPayParam {
    /**
     * 用户券id
     */
    private String userCouponId;

    /**
     * 券面值 单位:分
     */
    private Integer deno;
}
