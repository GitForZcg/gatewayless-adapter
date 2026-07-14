package com.personal.demo.pojo.dto.member.response;

import com.personal.demo.pojo.dto.member.response.grade.MemberRuleDto;
import com.personal.demo.pojo.dto.member.response.grade.PrivilegeDto;
import com.personal.demo.pojo.dto.member.response.grade.UserCardDto;
import lombok.Data;

/**
 * 会员等级升级任务业务数据
 *
 * @author fxs
 * @date 2025/01/13
 */
@Data
public class GradeUpgradeTaskResDto {

    /**
     * 用户卡片信息
     */
    private UserCardDto userCard;

    /**
     * 特权信息
     */
    private PrivilegeDto privilege;

    /**
     * 会员规则信息
     */
    private MemberRuleDto memberRule;
}
