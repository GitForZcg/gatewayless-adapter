package com.personal.demo.pojo.dto.member.response.coupon;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Li QianQian
 * describe:
 */
@Data
public class TransferCouponDetailRespDto {

    /**
     * 转赠人姓名
     */
    private String shareUserName;

    /**
     * 券面额（元）
     */
    private Float money;

    /**
     * 券模版类型：1代金券 2礼品券 4券包 5折扣
     */
    private Integer type;

    /**
     * 券过期时间
     */
    private String expiryTime;

    /**
     * 券状态：1:未使用；2:已使用；3:撤销充值，同时删除本次充值赠送的券；4:撤销消费，同时删除本次消费赠送的券；
     * 5:消费流程失败，同时删除本次消费赠送的券；6:手工调账扣减券；7:撤销积分还礼；8:转增中状态；9:已转增完
     * 10：商城退款 11：合并账户 12：撤销api发券 13：已过期 14:撤销售卖等级发券 15：撤销售卖等级发券
     */
    private Integer status;
}
