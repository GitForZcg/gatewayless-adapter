package com.personal.demo.request.member;

import com.personal.demo.request.group.ValidationGroups;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;


/**
 * 获取列表请求参数
 *
 *
 * @Author: fxs
 * @Date: 2026/1/12 15:52
 */
@Data
public class CouponDetailParam  {

    /**
     * 会员编码(对应适配样例卡号)
     */
    @NotEmpty(message = "会员编码不能为空", groups = ValidationGroups.memberGroup.class)
    private String membersCode;

    /**
     * 会员券码(适配样例的会员券id)
     */
    @NotEmpty(message = "会员券码不能为空", groups = ValidationGroups.memberGroup.class)
    private String couponCode;


}
