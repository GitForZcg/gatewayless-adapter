package com.personal.demo.pojo.dto.member.request;

import com.personal.demo.pojo.base.BaseMemberPublicParam;
import lombok.Data;

/**
 * 会员卡号请求参数
 * @author fxs
 * @date 2025/01/13
 */
@Data
public class CnoReqDto implements BaseMemberPublicParam {

    /** 会员卡号 */
    private String cno;

    @Override
    public String memberCode() {
        return createMemberCode(this.cno);
    }
}
