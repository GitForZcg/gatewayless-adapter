package com.personal.demo.response.member;

import com.personal.demo.response.member.charge.ChargeRuleItemResultDto;
import lombok.Data;

import java.util.List;

/**
 * 储值规则业务数据
 * @author fxs
 * @date 2025/01/13
 */
@Data
public class ChargeRuleResultDto {

    /** 有效期描述 */
    private String yxq;

    /** 到期描述 */
    private String expired;

    /** 规则列表 */
    private List<ChargeRuleItemResultDto> content;

    /** 是否支持自定义礼品 */
    private Boolean isGiftDiy;

    /** 活动说明 */
    private String description;

    /** 扩展字段 */
    private String extend;

    /** 身份证限额（分） */
    private Integer idCardThreshold;
}
