package com.personal.demo.response.member.charge;

import lombok.Data;

/**
 * 奖励券DTO
 * @author fxs
 * @date 2025/01/13
 */
@Data
public class AwardCouponResultDto {

//    /** 券模板ID */
//    private String couponId;
//
//    /** 面值 */
//    private Integer value;
//
//    /** 券名称 */
//    private String name;
//
//    /** 赠送数量 */
//    private Integer number;
//
//    /** 券类型 */
//    private String cType;
//
//    /** 使用范围 */
//    private String cUsingScope;

    /**券名称*/
    private String title;

    /**券面值*/
    private String deno;

    /**券类型*/
    private String type;

    /**赠送数量*/
    private String count;
}
