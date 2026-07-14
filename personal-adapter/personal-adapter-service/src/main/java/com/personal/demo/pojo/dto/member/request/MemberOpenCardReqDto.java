package com.personal.demo.pojo.dto.member.request;

import com.personal.demo.pojo.base.BaseMemberPublicParam;
import com.personal.demo.pojo.base.BaseSM2PublicParam;
import lombok.Data;

/**
 * 会员开卡请求DTO
 *
 * @Author: fxs
 * @Date: 2026/1/12 16:54
 */
@Data
public class MemberOpenCardReqDto implements BaseMemberPublicParam {

    /**
     * 微生活会员id
     */
    private String anyId;

    /**
     * 会员开卡类型
     */
    private String type;

    /**
     * 用户openid
     */
    private String openid;

    /**
     * 用户unionid
     */
    private String unionid;

    /**
     * 开卡对应的微生活门店id
     */
    private Integer shopId;

    /**
     * 会员手机号
     */
    private String phone;

    /**
     * 是否解绑手机
     */
    private Boolean isUnbindPhone;

    @Override
    public String memberCode() {
        return createMemberCode(this.anyId);
    }
}
