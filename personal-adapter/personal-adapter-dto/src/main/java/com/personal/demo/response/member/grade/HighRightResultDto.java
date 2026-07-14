package com.personal.demo.response.member.grade;

import lombok.Data;

/**
 * 增值权益DTO
 * @author fxs
 * @date 2026/1/12
 */
@Data
public class HighRightResultDto {

    /** 图标暗色图 */
    private String darkImg;

    /** 特权名称 */
    private String name;

    /** 特权描述 */
    private String desc;
}
