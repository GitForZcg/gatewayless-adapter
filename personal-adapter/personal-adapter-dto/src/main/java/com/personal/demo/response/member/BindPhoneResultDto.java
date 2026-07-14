package com.personal.demo.response.member;

import lombok.Data;

/**
 * 返回参数
 *
 * @Author: fxs
 * @Date: 2026/3/4 17:23
 */
@Data
public class BindPhoneResultDto {

    /** 用户名称 */
    private String name;

    /** 手机号 */
    private String phone;

    /**
     * 会员卡号
     */
    private String membersCode;
}
