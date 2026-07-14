package com.personal.demo.request.member;

import com.personal.demo.request.group.ValidationGroups;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


/**
 * 会员优惠券列表请求参数
 *
 *
 * @Author:
 * @Date:
 */
@Data
public class CouponListParam {

    /**
     * 会员编码(对应适配样例卡号)
     */
    @NotEmpty(message = "会员编码不能为空", groups = ValidationGroups.memberGroup.class)
    private String membersCode;

    /**
     * 页码
     */
    @NotNull(message = "页码不能为空")
    private Integer page;

    /**
     * 每页数量
     */
    @NotNull(message = "每页数量不能为空")
    private Integer perPage;



}
