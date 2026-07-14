package com.personal.demo.request.member;

import com.personal.demo.request.group.ValidationGroups;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


/**
 * 领取券请求参数
 *
 *
 * @Author:
 * @Date:
 */
@Data
public class ReceiveCouponParam {

    /**
     * 转赠人unionid
     */
    @NotEmpty(message = "转赠人unionid不能为空", groups = ValidationGroups.memberGroup.class)
    private String transferUnionid;

    /**
     * 领取人unionid
     */
    @NotEmpty(message = "领取人unionid不能为空", groups = ValidationGroups.memberGroup.class)
    private String unionid;


    /**
     * 转赠时间戳
     */
    @NotEmpty(message = "转赠时间戳不能为空", groups = ValidationGroups.memberGroup.class)
    private String shareTime;

}
