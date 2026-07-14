package com.personal.demo.response.member.coupon;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author
 * @Date
 * @Version 1.0
 * @description: 优惠券集合
 */

@Data
public class CouponDetailResultDto {


    /**
     * 券的code码
     */
    private String couponId;

    /**
     * 券模板id
     **/
    private Integer templateId;


    /**
     * 券的名称
     */
    private String couponName;


    /**
     * 使用说明
     */
    private String instruction;

    /**
     * 券的类型
     */
    private Integer couponType;
    /**
     * 券抵扣的钱/折扣
     */
    private BigDecimal couponPrice;

    /**
     * 有效期截止日期
     */
    private String validityEndTime;


    /**
     * 券的状态
     **/
    private Integer couponStatus;

    /**
     * 是否标红（小于72小时后标红）
     */
    private Boolean isMarkedRed;


    /**
     * 是否可以转赠 true可以转赠false 不可以转赠
     */
    private Boolean isTransfer;

    /**
     * 券销售金额
     */
    private Integer saleMoney;


}
