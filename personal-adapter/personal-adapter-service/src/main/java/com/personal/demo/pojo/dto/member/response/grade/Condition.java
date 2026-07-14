package com.personal.demo.pojo.dto.member.response.grade;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * 表示条件数据的类，包含条件类型、规则值、当前条件数值以及下一等级名称等信息。
 * @author fxs
 * @date 2026/03/03
 */
@Data
public class Condition {

    /** 条件类型 */
    @SerializedName("sub_type")
    private int subType;

    /** 规则设置的条件值 */
    @SerializedName("rule_value")
    private String ruleValue;

    /** 当前条件达到的数值 */
    @SerializedName("value")
    private String value;

    /** rule_value-value */
    @SerializedName("d_value")
    private String dValue;

}
