package com.personal.demo.pojo.dto.member.request;

import com.personal.demo.pojo.base.BaseMemberPublicParam;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * 储值规则查询请求DTO
 * @author fxs
 * @date 2025/01/13
 */
@Data
public class ChargeRuleReqDto implements BaseMemberPublicParam {

    /** 门店id */
    @SerializedName("shop_id")
    private Integer shopId;

    /** 等级id */
    @SerializedName("grade_id")
    private Integer gradeId;

    /** 是否展示翻倍规则 0不展示 1展示 */
    @SerializedName("show_double_rule")
    private Integer showDoubleRule;

    /** 会员卡号 */
    private String cno;

    @Override
    public String memberCode() {
        return createMemberCode(this.cno);
    }
}
