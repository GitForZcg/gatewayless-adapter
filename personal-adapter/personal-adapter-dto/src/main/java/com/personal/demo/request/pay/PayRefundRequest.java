package com.personal.demo.request.pay;

import com.personal.demo.request.group.ValidationGroups;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/10/30 14:31
 */
@Data
@Accessors(chain = true)
public class PayRefundRequest {

    @NotBlank(message = "支付号不能为空", groups = ValidationGroups.payGroup.class)
    private String baid;

    /**
     * 1、调用方的原已支付正向订单号，具体规则见详情 -Y
     */
    private String bizOrderNo;

    /**
     * 2、调用方生成，一次退款单号。部分退款，分批退款要用不同的单号, 不同的退款批次单号必须全局唯一 -Y
     */
    private String bizRefundOrderNo;

    /**
     * 3、退款金额，单位：分 支持部分退款 多次部分退款请按照退款说明 -Y
     */
    private long amount;

    /**
     * 4、退款结果回调地址 -N
     */
    private String notifyUrl;

    /**
     * 5、退款备注。最长50个汉字 -N
     */
    private String productName;

    /**
     * 6、业务方自定义参数 -N
     */
    private String bizAttach;

    /**
     * 7、使用沃银通道必填，操作员编号 -N
     */
    private String operNo;

    /**
     * 8、使用沃银通道必填，收银机编号 -N
     */
    private String posNo;

    /**
     * 9、网商 合并支付 明细单 退款付方id，即原支付单收方 -N
     */
    private String participantId;

    /**
     * 10、网商 合并支付 明细单 网商单号 -N
     */
    private String relateOrderNo;

    /**
     * 11、业务方外网IP -N
     */
    private String clientip;
}
