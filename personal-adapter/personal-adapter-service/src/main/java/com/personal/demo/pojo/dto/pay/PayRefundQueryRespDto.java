package com.personal.demo.pojo.dto.pay;

import com.personal.demo.pojo.base.BasePayPublicParams;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/10/30 15:49
 */
@Data
@Accessors(chain = true)
public class PayRefundQueryRespDto implements BasePayPublicParams {

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
     * 5、通道方支付商户号 支付宝对应商家sellerid 微信对应子商户号 汇付对应商户号 美团对应merchantId -Y
     */
    private String channelSubMchId;

    /**
     * 6、退款订单状态，详情 -Y
     */
    private byte refundStatus;

    /**
     * 7、调用方退款单号，具体规则见详情 -N
     */
    private String bizRefundOrderNo;

    /**
     * 8、支付中心退款单号 -N
     */
    private String refundOrderNo;

    /**
     * 9、微信或支付宝退款单号 -N
     */
    private String finalRefundOrderNo;

    /**
     * 10、订单金额，分 -Y
     */
    private long amount;

    /**
     * 11、退款订单创建时间 -Y
     */
    private String createTime;

    /**
     * 12、业务方自定义参数 -N
     */
    private String bizAttach;

    /**
     * 13、是否开启分账 只有汇付通道支持分账 -Y
     */
    private byte isSplit;

    /**
     * 14、分账信息,当isSplit=1时有返回，见下 -N
     */
    private Map<String, Object> splitInfo;

    /**
     * 15、支付宝对应store_id，微信对应scene_info.store_info.id -N
     */
    private String storeId;

    /**
     * 16、三方支付通道返回的交易状态，包含通道交易状态码或通道交易状态描述 -N
     */
    private String channelRefundStatus;

    /**
     * 17、实际退款金额，即用户实际收到的退款金额 -N
     */
    private long receiptRefundAmount;

    @Override
    public String orderId() {
        return this.bizRefundOrderNo;
    }
}
