package com.personal.demo.pojo.dto.member.response.grade;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * 用户卡片信息DTO
 * @author fxs
 * @date 2025/01/13
 */
@Data
public class UserCardDto {

    /** 是否可升级 1可升 0不可升 */
    private Integer isUpgrade;

    /** 用户ID */
    @SerializedName("C_id")
    private String cId;

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
    @SerializedName("upgrade_amount")
    private Integer upgradeAmount;

    /** 差值 */
    @SerializedName("d_value")
    private Integer dValue;

    /** 下一等级名称 */
    @SerializedName("next_level")
    private String nextLevel;

    /** 规则类型 */
    private Integer ruletype;

    /**
     * 充值进度
     */
    @SerializedName("charge_progress")
    private Condition chargeProgress;

    /**
     * 消费进度
     */
    @SerializedName("consume_progress")
    private Condition consumeProgress;

}
