package com.personal.demo.pojo.dto.member.request;

import com.personal.demo.pojo.base.BaseMemberPublicParam;
import com.personal.demo.pojo.base.BaseSM2PublicParam;
import com.personal.demo.request.group.ValidationGroups;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;


/**
 * 券模版请求参数
 *
 *
 * @Author: fxs
 * @Date: 2026/1/12 15:52
 */
@Data
public class CouponTemplateReqDto  implements BaseMemberPublicParam {

    /**
     * 券模版id
     */
    @NotEmpty(message = "券模版id不能为空", groups = ValidationGroups.memberGroup.class)
    private Integer coupon_id;
}
