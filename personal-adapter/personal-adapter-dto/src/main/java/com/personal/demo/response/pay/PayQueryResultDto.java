package com.personal.demo.response.pay;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/10/29 14:26
 */
@Data
@Accessors(chain = true)
public class PayQueryResultDto {

    /**
     * 1、支付中心支付账号 -Y
     */
    private int baId;

    /**
     * 2、支付场景，见常量定义 -Y
     */
    private byte scene;

    /**
     * 3、支付通道，见常量定义 -Y
     */
    private byte payChannel;

    /**
     * 4、通道方支付商户号 支付宝待定 微信对应主商户号 汇付对应渠道号 美团对应appid -Y
     */
    private String channelMchId;

    /**
     * 5、通道方支付商户号 支付宝对应商家sellerid 微信对应子商户号 汇付对应商户号 美团对应merchantId -N
     */
    private String channelSubMchId;

    /**
     * 6、通道订单号 支付宝或微信为空，第三方通道的通道单号，用于商户扫码退款，微信支付详情里二维码下面的单号(有的通道不返回) -N
     */
    private String channelOrderNo;

    /**
     * 7、订单状态，详情 -Y
     */
    private byte transStatus;

    /**
     * 8、调用方的订单号，一个订单号只能用一次 -Y
     */
    private String bizOrderNo;

    /**
     * 9、支付中心订单号 -Y
     */
    private String orderNo;

    /**
     * 10、微信或支付宝的订单号,有的支付通道不返回 -N
     */
    private String finalOrderNo;

    /**
     * 11、订单金额，分 -Y
     */
    private long amount;

    /**
     * 12、订单创建时间 -Y
     */
    private String createTime;

    /**
     * 13、业务方预支付请求时的自定义参数 -N
     */
    private String bizAttach;

    /**
     * 14、支付宝对应store_id，微信对应scene_info.store_info.id -N
     */
    private String storeId;

    /**
     * 15、终端号设备号 支付宝对应terminal_id，微信对应device_info -N
     */
    private String deviceInfo;

    /**
     * 16、是否开启分账 只有汇付通道支持分账 -Y
     */
    private byte isSplit;

    /**
     * 17、分账信息，isSplit=1时有返回，见下 -N
     */
    private Map<String, Object> splitInfo;

    /**
     * 18、三方支付通道返回的交易状态，包含通道交易状态码或通道交易状态描述 -N
     */
    private String channelTransStatus;

    /**
     * 19、支付宝相关的额外参数 -N
     */
    private String aliExtParams;

    /**
     * 20、微信相关的额外参数 -N
     */
    private String wxExtParams;

    /**
     * 21、通道接口版本,会传给通道 -N
     */
    private String channelApiVersion;

    /**
     * 22、微信的优惠信息字段 -N
     */
    private String promotionDetail;

    /**
     * 23、实收金额,单位为:分 -N
     */
    private long receiptAmount;

    /**
     * 24、支付宝、微信购买者标识，openid或buyer_user_id -N
     */
    private String buyer;

    /**
     * 25、网商合并支付查询返回的subOrderList -N
     */
    private String subOrderList;

    /**
     * 26、服务费 -N
     */
    private Map<String, Object> service;

}
