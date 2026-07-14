package com.personal.demo.pojo.dto.member.response.grade;

import lombok.Data;

/**
 * 升级权益DTO
 * @author fxs
 * @date 2026/1/12
 */
@Data
public class UpRightDto {

    /** 权益类型 4升级赠礼 */
    private Integer type;

    /** 权益标题 */
    private String title;

    /** 权益描述 */
    private String desc;

    /** 券模板ID */
    private String couponid;

    /** 券名称 */
    private String couponName;
}
