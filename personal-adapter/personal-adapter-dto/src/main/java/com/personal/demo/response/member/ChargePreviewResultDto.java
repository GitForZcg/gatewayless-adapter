package com.personal.demo.response.member;

import com.personal.demo.response.member.charge.AwardCouponResultDto;
import lombok.Data;

import java.util.List;

/**
 * 储值提交业务数据
 * @author fxs
 * @date 2025/01/13
 */
@Data
public class ChargePreviewResultDto {

    /** 卡号 */
    private String cno;

    /** 手机号 */
    private String phone;

    /** 本次储值金额（分） */
    private Integer chargeTotal;

    /** 实收金额（分） */
    private Integer money;

    /** 赠送金额（分） */
    private Integer gift;

    /** 有效期描述 */
    private String expired;

    /** 赠送券列表 */
    private List<AwardCouponResultDto> awardCoupons;

    /** 储值业务号 */
    private String bizId;

    /** 储值订单流水号 */
    private String dealId;

    /** 赠送积分 */
    private Integer awardCredit;

    /** 是否自定义金额 */
    private Boolean isDiy;
}
