package com.personal.demo.pojo.dto.member.request;

import com.personal.demo.pojo.base.BaseMemberPublicParam;
import lombok.Data;

/**
 * 绑定/修改手机号请求参数
 *
 * @author 86136
 */
@Data
public class BindPhoneReqDto implements BaseMemberPublicParam {

    /**
     * 卡号（必填）
     */
    private String cno;

    /**
     * 电话（必填）
     */
    private String phone;

    /**
     * 来源类型（选填）
     * 默认api,点餐类型：4
     */
    private Integer sourceType;

    /**
     * 区号（选填）
     */
    private String countryCode;
}
