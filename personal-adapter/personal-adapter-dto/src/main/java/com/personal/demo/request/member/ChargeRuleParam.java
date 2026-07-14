package com.personal.demo.request.member;

import lombok.Data;

/**
 * 储值规则查询请求DTO
 * @author fxs
 * @date 2025/01/13
 */
@Data
public class ChargeRuleParam {

    /** 门店id */
    private Integer shopId;

    /** 等级id */
    private Integer gradeId;

    /** 是否展示翻倍规则 0不展示 1展示 */
    private Integer showDoubleRule;

    /** 会员卡号 */
    private String membersCode;
}
