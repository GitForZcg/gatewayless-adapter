package com.personal.demo.request.member;


import lombok.Data;

/**
 * 会员基础查询请求参数
 *
 * @Author: fxs
 * @Date: 2026/1/13
 */
@Data
public class MemberBaseParam {

    /**卡号 用户卡号(手机号、实体卡号、电子卡号) */
    private String cno;
    /**门店ID，默认总部 99999999*/
    private Integer sid;

    /**会员openid*/
    private String openid;
}
