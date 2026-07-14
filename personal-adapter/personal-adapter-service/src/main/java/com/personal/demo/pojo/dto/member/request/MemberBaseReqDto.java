package com.personal.demo.pojo.dto.member.request;


import com.personal.demo.pojo.base.BaseMemberPublicParam;
import com.personal.demo.pojo.base.BaseSM2PublicParam;
import lombok.Data;

/**
 * 会员基础查询请求参数
 *
 * @Author: fxs
 * @Date: 2026/1/13
 */
@Data
public class MemberBaseReqDto implements BaseMemberPublicParam {

    /**卡号用户卡号(手机号、实体卡号、电子卡号) */
    private String cno;
    /**门店ID，默认总部 99999999*/
    private Integer sid;

    /**会员openid*/
    private String openid;

    @Override
    public String memberCode() {
        return createMemberCode(this.cno);
    }
}
