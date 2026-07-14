package com.personal.demo.pojo.dto.trade.reponse;

import lombok.Data;

import java.util.List;


/**
 * @author Li QianQian
 * describe:订单预览返回参数
 */
@Data
public class OrderPreviewRespDto {
    /**
     * 交易id，用于打印流水号
     */
    private Long tcid;

    /**
     * 是否需要短信验证
     */
    private Boolean verifySms;

    /**
     * 是否需要交易密码验证
     */
    private Boolean verifyPassword;

    /**
     * 本次交易，用户升级至的等级信息，未升级则返回null
     */
    private GradeInfoRespDto grade;

    /**
     * 本次交易使用储值后，用户的储值余额(单位:分)，未使用也返回剩余储值
     */
    private Integer balance;

    /**
     * 本次交易使用积分后，用户的积分剩余(单位:个)，未使用也返回剩余积分
     */
    private Integer credit;

    /**
     * 本次交易，奖励用户的积分数量(单位:个)，未奖励则返回0
     */
    private Integer receiveCredit;

    /**
     * 本次交易，奖励用户的券
     */
    private List<CouponRespDto> coupons;

    /**
     * 本次交易，使用的积分
     */
    private Integer useCredit;

}
