package com.personal.demo.response.member.charge;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 单条储值规则DTO
 * @author fxs
 * @date 2025/01/13
 */
@Data
public class ChargeRuleItemResultDto {

    /** 规则ID */
    private String ruleId;

    /** 频次类型 */
    private Integer frequencyType;

    /** 频次值 */
    private Integer frequencyValue;

    /** 优先级 */
    private String priority;

    /** 储值金额（元） */
    private BigDecimal price;

    /** 储值金额（分） */
    private Integer priceFen;

    /** 赠品列表 */
    private List<ChargeRuleGiftResultDto> gift;

    /** 首次消费折扣率 */
    private Integer firstConsumeRate;

    /** 首次消费折扣类型 */
    private Integer firstConsumeRateType;

    /** 排序值 */
    private Integer sortVal;

    /** 自定义图片 */
    private String customImg;

    /** 标签图片 */
    private String tagImg;

    /** 展示天数 */
    private Integer displayDay;

    /** 开始日期 */
    private String startDate;

    /** 结束日期 */
    private String endDate;

    /** 适用门店列表 */
    private List<String> shopList;

    /** 到账金额（元） */
    private Integer money;

    /** 到账金额（分） */
    private Integer moneyFen;

    /** 赠送积分比例 */
    private Integer giftPercent;

    /** 赠送积分 */
    private Integer credit;

    /** 赠送积分比例 */
    private Integer creditPercent;

    /** 适用等级列表 */
    private List<String> gradeList;

    /** 规则名称 */
    private String ruleName;
}
