package com.personal.demo.request.pay;

import com.personal.demo.request.group.ValidationGroups;
import jakarta.validation.constraints.NotBlank;
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
public class PayRefundQueryRequest {

    @NotBlank(message = "支付号不能为空", groups = ValidationGroups.payGroup.class)
    private String baid;

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
}
