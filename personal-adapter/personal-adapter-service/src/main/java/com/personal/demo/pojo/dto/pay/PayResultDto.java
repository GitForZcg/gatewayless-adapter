package com.personal.demo.pojo.dto.pay;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 支付回调通知
 * @date 2025/11/13 10:55
 */
@Data
@Accessors(chain = true)
public class PayResultDto {

    /**
     * 1、订单类型 1-正向订单结果通知 2-退款结果通知 3-关单 4-提现
     */
    private Integer orderType;

    /**
     * 2、支付中心分配的厂商产品线appid
     */
    private String appId;

    /**
     * 3、支付中心支付账号
     */
    private Integer baId;

    /**
     * 4、业务方订单号
     */
    private String bizOrderNo;

    /**
     * 5、交易状态
     */
    private Integer status;

    /**
     * 6、金额，单位分
     */
    private Long amount;

    /**
     * 7、gopay订单号
     */
    private String orderNo;

    /**
     * 8、购买者，微信是openid,支付宝是buyerID
     */
    private String buyer;

    /**
     * 9、业务方附加参数，原样返回
     */
    private String bizAttach;

    /**
     * 10、微信或支付宝的最终支付流水号
     */
    private String finalOrderNo;

    /**
     * 11、通道方支付商户号
     */
    private String channelMchId;

    /**
     * 12、通道方支付商户号
     */
    private String channelSubMchId;

    /**
     * 13、支付场景，见常量定义
     */
    private Integer scene;

    /**
     * 14、支付通道，见常量定义
     */
    private Integer payChannel;

    /**
     * 15、分账信息，json
     */
    private String splitInfo;

    /**
     * 16、网商合并支付子订单信息
     */
    private String pmlRemark;

    /**
     * 17、时间戳，秒级
     */
    private Long ts;

    /**
     * 18、签名
     */
    private String sign;

    /**
     * 19、支付宝相关的额外参数，JSON
     */
    private String aliParams;

    /**
     * 20、微信相关的额外参数，JSON
     */
    private String wxParams;

    /**
     * 21、通道方的子商户appId
     */
    private String channelSubAppId;

    /**
     * 22、微信的优惠信息字段
     */
    private String promotionDetail;

    /**
     * 23、实收金额，单位：分
     */
    private Long receiptAmount;

    /**
     * 24、实际退款金额，单位：分
     */
    private Long receiptRefundAmount;

    /**
     * 25、实际使用的微信子商户号
     */
    private String wxSubMchId;

    /**
     * 26、实际使用的支付宝子商户号
     */
    private String aliSubMchId;

    /**
     * 27、服务费相关，服务费为0时没有该项
     */
    private Map<String, Object> service;
}
