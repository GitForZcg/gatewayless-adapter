package com.personal.demo.dto.order;

import lombok.Data;

import java.io.Serializable;

/**
 * @author sulu
 * @date 2026年04月09日 9:25 AM
 */
@Data
public class DemoBalancePayDTO implements Serializable {

    /**
     * 交易id
     */
    private String deal_id;

    /**
     * 使用储值支付金额(单位:分)
     */
    private Integer stored_pay;

    /**
     * 使用实际储值支付金额(单位:分)
     */
    private Integer stored_sale_pay;

    /**
     * 使用赠送储值支付金额（单位：分）
     */
    private Integer stored_give_pay;
}
