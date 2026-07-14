package com.personal.demo.pojo.dto.pay;

import com.personal.demo.pojo.base.BasePayPublicParams;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/10/29 14:27
 */

@Data
@Accessors(chain = true)
public class PayQueryReqDto implements BasePayPublicParams {

    /**
     * 1、调用方的订单号，一个订单号只能用一次，具体规则见详情 -N
     */
    private String bizOrderNo;

    /**
     * 2、支付中心订单号（实际值应为prepay返回的orderNo） -N
     */
    private String orderNo;

    /**
     * 3、平台（微信或支付宝）的订单号 -N
     */
    private String finalOrderNo;

    /**
     * 4、支付通道订单号（聚合支付通道的订单号） -N
     */
    private String channelOrderNo;

    /**
     * 5、通道接口版本,会传给通道 -N
     */
    private String channelApiVersion;

    @Override
    public String orderId() {
        return createOrderId(this.bizOrderNo);
    }
}
