package com.personal.demo.request.member;

import lombok.Data;

/**
 * 储值卡操作请求DTO
 * @author fxs
 * @date 2025/01/13
 */
@Data
public class ValueCardOpParam {

    /** 储值卡卡号 */
    private String cardNum;

    /** 储值卡密码 */
    private String cardPwd;

    /** 操作类型 1查看 2充值 */
    private Integer opType;

    /** 会员卡卡号；充值时必填 */
    private String membersCode;
}
