package com.personal.demo.request.member;

import com.personal.demo.request.group.ValidationGroups;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;


/**
 * 获取失效的优惠券列表请求参数
 *
 *
 * @Author:
 * @Date:
 */
@Data
public class InvalidCouponListParam {

    /**
     * 会员编码(对应适配样例卡号)
     */
    @NotEmpty(message = "会员编码不能为空", groups = ValidationGroups.memberGroup.class)
    private String cno;

    /**
     *  门店，如果指定门店，只查该门店系可用券
     */
    private String sid;
}
