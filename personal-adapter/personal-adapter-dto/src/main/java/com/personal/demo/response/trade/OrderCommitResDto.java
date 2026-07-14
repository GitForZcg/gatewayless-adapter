package com.personal.demo.response.trade;


import lombok.Data;

@Data
public class OrderCommitResDto {

    /**
     * 交易id
     */
    private String deal_id;
    /**
     * 使用储值支付金额(单位:分)
     */
    private int stored_pay;
    /**
     * 使用实际储值支付金额(单位:分)
     */
    private int stored_sale_pay;
    /**
     * 使用赠送储值支付金额（单位：分）
     */
    private int stored_give_pay;
}
