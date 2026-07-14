package com.personal.demo.response.pay;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 预支付参数
 * @date 2025/10/21 14:26
 */
@Data
@Accessors(chain = true)
public class PayPrepareResultDto {

    /**
     * 1、支付中心支付账号 -Y
     */
    private int baId;

    /**
     * 2、场景值，见常量定义 -Y
     */
    private byte scene;

    /**
     * 3、实际使用的支付通道，见常量定义 -Y
     */
    private byte payChannel;

    /**
     * 4、实际使用的通道的商户号 非支付中心商户id -Y
     */
    private String channelMchId;

    /**
     * 5、调用方的订单号，一个订单号只能用一次，具体规则见详情 -Y
     */
    private String bizOrderNo;

    /**
     * 6、支付中心订单号（int64数字字符串） -Y
     */
    private String orderNo;

    /**
     * 7、(微信、支付宝单号) 支付通道最后端返回的订单号，如果用的是汇付这种，则返回的是微信、支付宝的订单号，而不是汇付的 -Y
     */
    private String finalOrderNo;

    /**
     * 8、支付通道订单号 支付宝或微信为空，第三方通道的通道单号，用于商户扫码退款，微信支付详情里二维码下面的单号 -N
     */
    private String channelOrderNo;

    /**
     * 9、订单金额，分 -Y
     */
    private long amount;

    /**
     * 10、订单状态，见常量定义 -Y
     */
    private byte transStatus;

    /**
     * 11、业务方附加数据,数据来自预支付请求 -N
     */
    private String bizAttach;

    /**
     * 12、微信预支付信息 -Y
     */
    private String payInfo;

    /**
     * 13、小程序收银台支付时 -N
     */
    private String embeddedMiniPay;

    /**
     * 14、商家二维码 -Y
     */
    private String qrCode;

    /**
     * 15、支付宝对应store_id，微信对应scene_info.store_info.id -N
     */
    private String storeId;

    /**
     * 16、终端号 见请求说明 -N
     */
    private String deviceInfo;

    /**
     * 17、是否开启分账 1=开启 0=未开启 只有汇付通道支持分账 -Y
     */
    private byte isSplit;

    /**
     * 18、分账信息，isSplit=1时有返回 见下 -N
     */
    private Map<String, Object> splitInfo;

    /**
     * 19、通道接口版本,会传给通道 -N
     */
    private String channelApiVersion;

    /**
     * 20、微信的优惠信息字段 -N
     */
    private String promotionDetail;

    /**
     * 21、实收金额,单位为:分 -N
     */
    private long receiptAmount;

    /**
     * 22、用于h5支付场景访问用的带签名的url -N
     */
    private String h5Url;

    /**
     * 23、用于网商银行返回支付宝支付短链 -N
     */
    private String aliPay;

    /**
     * 24、支付宝、微信购买者标识，openid或buyer_user_id -N
     */
    private String buyer;

}
