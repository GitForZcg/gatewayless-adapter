package com.personal.demo.response.member.coupon;

import lombok.Data;


/**
 * 领取券返回
 *
 *
 * @Author:
 * @Date:
 */
@Data
public class ReceiveCouponResultDto {

    /**
     * 用户券状态: 1.已领取 2. 已抢光（只针对领取人）3.被使用 4.已过期
     */
    private Integer couponStatus;

}
