package com.personal.demo.response.member.charge;

import lombok.Data;

/**
 * 储值明细单项DTO
 * @author fxs
 * @date 2025/01/13
 */
@Data
public class ChargeUsageItemResultDto {

    /** 业务类型编码 */
    private String type;

    /** 业务类型名称 */
    private String typeName;

    /** 收支符号 */
    private String status;

    /** 日期时间 yyyy-MM-dd HH:mm:ss */
    private String dateTime;

    /** 日期 yyyy-MM-dd */
    private String date;

    /** 时间 HH:mm:ss */
    private String time;

    /** 交易id */
    private String tcId;

    /** 关联交易id */
    private String tcRelateId;

    /** 用户储值余额 */
    private String tcUserSaving;

    /** 用户销售储值余额 */
    private String tcUserSaleSaving;

    /** 用户奖励储值余额 */
    private String tcUserRewardSaving;

    /** 倍数 */
    private String multiple;

    /** 门店id */
    private String sid;

    /** 操作员id */
    private String mid;

    /** 变动金额 */
    private String money;

    /** 奖励金额 */
    private String awardmoney;

    /** 门店名称 */
    private String sName;

    /** 来源卡号 */
    private String fromUNo;

    /** 来源微信卡号 */
    private String fromWxUNo;

    /** 操作员名称 */
    private String mName;

    /** 过期日期 */
    private String overDate;

    /** 目标卡号 */
    private String toUNo;

    /** 奖励券 */
    private String awardcoupon;

    /** 奖励券详情 */
    private String awardcouponDetail;
}
