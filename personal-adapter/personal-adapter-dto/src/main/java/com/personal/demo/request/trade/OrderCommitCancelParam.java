package com.personal.demo.request.trade;

import lombok.Data;

@Data
public class OrderCommitCancelParam {

    /**
     * 交易业务号，收银方保证全部门店唯一，提交交易需要biz_id
     */
    private String tradeCommitId;
}
