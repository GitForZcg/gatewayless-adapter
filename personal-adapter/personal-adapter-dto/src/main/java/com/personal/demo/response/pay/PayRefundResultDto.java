package com.personal.demo.response.pay;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/10/30 14:44
 */
@Data
@Accessors(chain = true)
public class PayRefundResultDto {

    /**
     * 1、退款状态 -Y
     */
    private byte refundStatus;

    /**
     * 2、调用方的原已支付正向订单号，具体规则见详情 -Y
     */
    private String bizOrderNo;

    /**
     * 3、调用方的本次退款单号，一笔退款多次请求时需保证唯一 -Y
     */
    private String bizRefundOrderNo;

    /**
     * 4、支付中心的本次退款单号，多次退款返回不一样 -Y
     */
    private String refundOrderNo;

    /**
     * 5、微信或支付宝的退款单号 -N
     */
    private String finalRefundOrderNo;

    /**
     * 6、业务方自定义参数 -N
     */
    private String bizAttach;

    /**
     * 7、分账信息，见下 -N
     */
    private Map<String, Object> splitInfo;

    /**
     * 8、实际退款金额，即用户实际收到的退款金额 -N
     */
    private long receiptRefundAmount;
}
