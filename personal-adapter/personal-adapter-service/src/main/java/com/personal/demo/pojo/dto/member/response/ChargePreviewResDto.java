package com.personal.demo.pojo.dto.member.response;

import com.personal.demo.pojo.dto.member.response.charge.AwardCouponDto;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

/**
 * 储值提交业务数据
 * @author fxs
 * @date 2025/01/13
 */
@Data
public class ChargePreviewResDto{

    /** 卡号 */
    private String cno;

    /** 手机号 */
    private String phone;

    /** 本次储值金额（分） */
    @SerializedName("charge_total")
    private Integer chargeTotal;

    /** 实收金额（分） */
    private Integer money;

    /** 赠送金额（分） */
    private Integer gift;

    /** 有效期描述 */
    private String expired;

    /** 赠送券列表 */
    @SerializedName("award_coupons")
    private List<AwardCouponDto> awardCoupons;

    /** 储值业务号 */
    @SerializedName("biz_id")
    private String bizId;

    /** 储值订单流水号 */
    @SerializedName("deal_id")
    private String dealId;

    /** 赠送积分 */
    @SerializedName("award_credit")
    private Integer awardCredit;

    /** 是否自定义金额 */
    @SerializedName("is_diy")
    private Boolean isDiy;
}
