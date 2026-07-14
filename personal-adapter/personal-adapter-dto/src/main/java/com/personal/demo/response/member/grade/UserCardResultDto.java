package com.personal.demo.response.member.grade;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * 用户卡片信息DTO
 * @author fxs
 * @date 2025/01/13
 */
@Data
public class UserCardResultDto {

    /** 是否可升级 1可升 0不可升 */
    private Integer isUpgrade;

    /** 用户ID */
    private String userId;

    /** 手机号 */
    private String phone;

    /** 公司ID */
    private String ccid;

    /** 当前等级ID */
    private String levelid;

    /** 当前等级名称 */
    private String level;

    /** 卡号 */
    private String number;

    /** 已消费/条件值 */
    private Integer amount;

    /** 升级需完成值 */
    private Integer upgradeAmount;

    /** 差值 */
    private Integer levelDifference;

    /** 下一等级名称 */
    private String nextLevel;

    /** 规则类型 */
    private Integer ruletype;

    /**
     * 充值进度
     */
    private ConditionResultDto chargeProgress;

    /**
     * 消费进度
     */
    private ConditionResultDto consumeProgress;

}
