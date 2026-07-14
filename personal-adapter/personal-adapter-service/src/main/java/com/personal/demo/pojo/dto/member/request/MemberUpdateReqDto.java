package com.personal.demo.pojo.dto.member.request;

import com.personal.demo.pojo.base.BaseMemberPublicParam;
import com.personal.demo.request.group.ValidationGroups;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.time.LocalDate;

/**
 * 修改会员信息
 *
 * @Author: fxs
 * @Date: 2026/1/12 15:52
 */
@Data
public class MemberUpdateReqDto implements BaseMemberPublicParam {

    /**
     * 要修改的会员卡号
     */
    @NotEmpty(message = "会员编码不能为空", groups = ValidationGroups.memberGroup.class)
    private String cno;

    /**
     * 性别 1:男 2:女
     */
    private Integer gender;

    /**
     * 用户姓名
     */
    private String username;

    /**
     * 生日 yyyy-MM-dd
     */
    private String birth;

    /**
     * 生日历法 0阳历 1阴历，默认0
     */
    private Integer birthflay = 0;

    @Override
    public String memberCode() {
        return createMemberCode(this.cno);
    }
}
