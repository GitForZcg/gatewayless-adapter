package com.personal.demo.response.member.coupon;

import lombok.Data;


/**
 * 转增券返回参数
 *
 *
 * @Author:
 * @Date:
 */
@Data
public class TransferCouponResultDto {

    /**
     * 转增结果
     */
    private String result;

    /**
     * 转赠人unionid
     */
    private String unionid;

    /**
     * 转发时间戳
     */
    private String shareTime;


}
