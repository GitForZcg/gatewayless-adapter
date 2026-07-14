package com.personal.demo.request.member;

import lombok.Data;

/**
 * 绑定/修改手机号请求参数
 *
 * @author fxs
 */
@Data
public class BindPhoneParam {

    /**
     * 卡号（必填）
     */

    private String membersCode;

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



    /**
     * 保留的uid
     */
    private String uid;

    /**
     * 需要保留的卡等级
     */
    private String grade;
}
