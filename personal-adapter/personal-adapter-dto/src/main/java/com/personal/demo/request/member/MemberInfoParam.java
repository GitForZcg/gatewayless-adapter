package com.personal.demo.request.member;

import lombok.Data;

/**
 * 查询会员信息返回业务数据
 * @author fxs
 * @date 2025/01/13
 */
@Data
public class MemberInfoParam {

    /** 卡号 */
    private Integer cno;

    /** 电话 */
    private Integer phone;

    /** 点餐类型；默认api */
    private Integer sourceType;

    /** 区号 */
    private String countryCode;
}
