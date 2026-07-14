package com.personal.demo.pojo.dto.member.response.grade;

import lombok.Data;

/**
 * 基础权益DTO
 * @author fxs
 * @date 2026/1/12
 */
@Data
public class BaseRightDto {

    /** 权益类型 1积分 2积分换礼 3生日礼 */
    private Integer type;

    /** 权益标题 */
    private String title;

    /** 权益描述 */
    private String desc;

    /** 消费门槛（分）；type=1时有值 */
    private Integer conume;

    /** 券模板ID；type=2,3时有值 */
    private String couponid;

    /** 券名称；type=2,3时有值 */
    private String couponName;
}
