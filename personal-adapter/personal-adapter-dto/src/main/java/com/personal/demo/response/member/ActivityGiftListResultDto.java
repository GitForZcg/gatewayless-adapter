package com.personal.demo.response.member;

import com.personal.demo.response.member.activity.ActivityGiftCouponResultDto;
import lombok.Data;
import java.util.List;

/**
 * 活动赠礼列表业务数据
 * @author fxs
 * @date 2025/01/13
 */
@Data
public class ActivityGiftListResultDto {

    /** 活动id */
    private String id;

    /** 活动名称 */
    private String name;

    /** 活动类型 1开卡礼 2生日赠礼 3完善资料赠礼 */
    private Integer type;

    /** 券列表 */
    private List<ActivityGiftCouponResultDto> coupons;

    /** 赠送积分 */
    private Integer awardCredit;

    /** 赠送储值（分） */
    private Integer awardCharge;

    /** 活动描述 */
    private String desc;
}
