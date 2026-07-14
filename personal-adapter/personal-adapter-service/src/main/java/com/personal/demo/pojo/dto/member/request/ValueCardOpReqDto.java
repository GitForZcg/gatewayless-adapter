package com.personal.demo.pojo.dto.member.request;

import com.personal.demo.pojo.base.BaseMemberPublicParam;
import lombok.Data;

/**
 * 储值卡操作请求DTO
 * @author fxs
 * @date 2025/01/13
 */
@Data
public class ValueCardOpReqDto implements BaseMemberPublicParam {

    /** 储值卡卡号 */
    private String cardNum;

    /** 储值卡密码 */
    private String cardPwd;

    /** 操作类型 1查看 2充值 */
    private Integer opType;

    /** 会员卡卡号；充值时必填 */
    private String cno;

    @Override
    public String memberCode() {
        return createMemberCode(this.cno);
    }
}
