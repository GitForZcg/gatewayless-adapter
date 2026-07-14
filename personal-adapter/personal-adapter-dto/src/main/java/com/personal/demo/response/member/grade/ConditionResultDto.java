package com.personal.demo.response.member.grade;

import lombok.Data;

/**
 * 表示条件数据的类，包含条件类型、规则值、当前条件数值以及下一等级名称等信息。
 * @author fxs
 * @date 2026/03/03
 */
@Data
public class ConditionResultDto {

    /** 条件类型 */
    private int subType;

    /** 规则设置的条件值 */
    private String ruleValue;

    /** 当前条件达到的数值 */
    private String value;

    /** rule_value-value */
    private String levelDifference;



}
