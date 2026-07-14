package com.personal.demo.pojo.dto.member.request;

import com.personal.demo.pojo.base.BaseMemberPublicParam;
import com.personal.demo.pojo.base.BaseSM2PublicParam;
import lombok.Data;

/**
 * 储值撤销请求DTO
 * @author fxs
 * @date 2025/01/13
 */
@Data
public class ChargeCancelReqDto implements BaseMemberPublicParam {

    /** 储值业务号 */
    private String bizId;

    /** 撤销收银员id */
    private Integer cashierId;

    /** 备注 */
    private String remark;

    /** 商家后台操作员id */
    private Integer handlerId;

    /** 是否只检查 1检查 0直接撤销 */
    private Integer onlyCheck;
}
