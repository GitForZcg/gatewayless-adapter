package com.personal.demo.pojo.dto.pay;

import com.personal.demo.pojo.base.BasePayPublicParams;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/10/30 15:49
 */
@Data
@Accessors(chain = true)
public class PayRefundQueryReqDto implements BasePayPublicParams {

    /**
     * 1、调用方退款单号，具体规则见详情 三选一，优先级refundOrderNo > finalRefundOrderNo > bizRefundOrderNo -N
     */
    private String bizRefundOrderNo;

    /**
     * 2、支付中心退款单号 三选一，优先级refundOrderNo > finalRefundOrderNo > bizRefundOrderNo -N
     */
    private String refundOrderNo;

    /**
     * 3、微信或支付宝退款单号 三选一，优先级refundOrderNo > finalRefundOrderNo > bizRefundOrderNo -N
     */
    private String finalRefundOrderNo;

    @Override
    public String orderId() {
        return this.bizRefundOrderNo;
    }
}
