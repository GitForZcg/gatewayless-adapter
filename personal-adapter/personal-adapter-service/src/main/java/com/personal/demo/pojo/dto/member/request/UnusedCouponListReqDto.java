package com.personal.demo.pojo.dto.member.request;

import com.personal.demo.pojo.base.BaseMemberPublicParam;
import com.personal.demo.pojo.base.BaseSM2PublicParam;
import com.personal.demo.request.group.ValidationGroups;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


/**
 * 获取未使用的优惠券列表请求参数
 *
 *
 * @Author:
 * @Date:
 */
@Data
public class UnusedCouponListReqDto implements BaseMemberPublicParam {

    /**
     * 会员编码(对应适配样例卡号)
     */
    @NotEmpty(message = "会员编码不能为空", groups = ValidationGroups.memberGroup.class)
    private String cno;

    /**
     *  门店，如果指定门店，只查该门店系可用券
     */
    private String sid;

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
