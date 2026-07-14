package com.personal.demo.pojo.dto.member.request;

import com.personal.demo.pojo.base.BaseMemberPublicParam;
import com.personal.demo.pojo.base.BaseSM2PublicParam;
import com.personal.demo.request.group.ValidationGroups;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


/**
 * 领取券的详情信息请求参数
 *
 *
 * @Author:
 * @Date:
 */
@Data
public class QueryTransferCouponDetailReqDto implements BaseMemberPublicParam {

    /**
     * 转赠人unionid
     */
    @NotEmpty(message = "转赠人unionid不能为空", groups = ValidationGroups.memberGroup.class)
    private String transfer_unionid;


    /**
     * 转赠时间戳
     */
    @NotEmpty(message = "转赠时间戳不能为空", groups = ValidationGroups.memberGroup.class)
    private String share_time;

}
