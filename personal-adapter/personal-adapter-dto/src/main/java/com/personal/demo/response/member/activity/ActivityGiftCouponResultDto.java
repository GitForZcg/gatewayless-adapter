package com.personal.demo.response.member.activity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * 活动赠礼券DTO
 * @author fxs
 * @date 2025/01/13
 */
@Data
public class ActivityGiftCouponResultDto {

    /** 券模板id */
    private String couponid;

    /** 券名称 */
    private String name;

    /** 面值/数量描述 */
    private String amount;

    /** 发放数量 */
    private Integer num;

    /** 券类型 2礼品券 */
    private Integer type;

    /**
     * 券子类型：1-代金券 2-礼品券 3-产品券 4-券包  6-营销活动券（高级券） 7-新版折扣券
     */
    @Expose
    @SerializedName("cType")
    private Integer cType;
}
