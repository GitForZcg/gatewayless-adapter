package com.personal.demo.pojo.dto.member.response.grade;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

/**
 * 会员规则信息DTO
 * @author fxs
 * @date 2025/01/13
 */
@Data
public class MemberRuleDto {

    /** 小程序页面标题 */
    @SerializedName("weapp_pagetitle")
    private String weappPagetitle;

    /** 表头 */
    private List<String> listhead;

    /** 规则列表 */
    private List<RuleItemDto> rulelist;

    /** 下一等级名称 */
    private String nextlevel;

    /** 升级任务列表 */
    private List<TargetItemDto> targetlist;
}
