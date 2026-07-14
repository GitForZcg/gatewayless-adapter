package com.personal.demo.response.member.charge;

import lombok.Data;

/**
 * 储值规则赠品DTO
 * @author fxs
 * @date 2025/01/13
 */
@Data
public class ChargeRuleGiftResultDto {

    /** 券模板ID */
    private String couponId;

    /** 券面值 */
    private Integer value;

    /** 券名称 */
    private String name;

    /** 赠送数量 */
    private Integer number;

    /** 券类型 */
    private String cType;

    /** 使用范围 */
    private String cUsingScope;


    /** 券副标题 */
    private String otherName;
}
