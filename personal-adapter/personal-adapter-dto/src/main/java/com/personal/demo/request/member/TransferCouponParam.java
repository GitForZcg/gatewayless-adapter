package com.personal.demo.request.member;

import com.personal.demo.request.group.ValidationGroups;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;


/**
 * 转增券请求参数
 *
 *
 * @Author:
 * @Date:
 */
@Data
public class TransferCouponParam {

    /**
     * 会员编码(对应适配样例卡号)
     */
    @NotEmpty(message = "会员编码不能为空", groups = ValidationGroups.memberGroup.class)
    private String membersCode;

    /**
     * 转赠人unionid
     */
    @NotEmpty(message = "转赠人unionid不能为空", groups = ValidationGroups.memberGroup.class)
    private String unionid;

    /**
     *  转赠券的唯一ID列表
     */
    @NotEmpty(message = "转赠券的唯一ID列表不能为空", groups = ValidationGroups.memberGroup.class)
    private String couponCode;


}
