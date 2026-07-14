package com.personal.demo.pojo.dto.member.response.grade;

import lombok.Data;

import java.util.List;

/**
 * 规则项DTO
 * @author fxs
 * @date 2025/01/13
 */
@Data
public class RuleItemDto {

    /** 等级ID */
    private String levelid;

    /** 等级名称 */
    private String level;

    /** 升级规则描述列表 */
    private List<String> goup;

    /** 降级规则描述列表 */
    private List<String> godown;
}
