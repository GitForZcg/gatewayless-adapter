package com.personal.demo.response.member;

import com.personal.demo.response.member.grade.MemberRuleResultDto;
import com.personal.demo.response.member.grade.PrivilegeResultDto;
import com.personal.demo.response.member.grade.UserCardResultDto;
import lombok.Data;

/**
 * 会员等级升级任务业务数据
 *
 * @author fxs
 * @date 2025/01/13
 */
@Data
public class GradeUpgradeTaskResultDto {

    /**
     * 用户卡片信息
     */
    private UserCardResultDto userCard;

    /**
     * 特权信息
     */
    private PrivilegeResultDto privilege;

    /**
     * 会员规则信息
     */
    private MemberRuleResultDto memberRule;
}
