package com.personal.demo.pojo.dto.member.response.grade;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

/**
 * 等级卡片DTO
 * @author fxs
 * @date 2025/01/13
 */
@Data
public class CardLevelDto {

    /** 等级名称 */
    private String level;

    /** 等级ID */
    private String levelid;

    /** 是否规则页 1是 0否 */
    private Integer isRulepage;

    /** 到期描述 */
    private String expiryDate;

    /** 轮播标识 */
    @SerializedName("swiper_id")
    private String swiperId;

    /** 已享特权描述 */
    private String powernum;

    /** 特权列表 */
    private List<PrivilegeItemDto> privileges;
}
