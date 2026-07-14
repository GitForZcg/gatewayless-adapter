package com.personal.demo.request.member;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 储值提交请求DTO
 * @author fxs
 * @date 2025/01/13
 */
@Data
public class ChargeCommitParam {

    /** 是否自定义金额储值 */
    private Boolean is_diy;

    /**
     * 交易业务号，收银方保证全部门店唯一，提交交易需要biz_id
     */
    private String biz_id;

    /**
     * 交易使用的活动优惠列表
     */
    private List<Map<String, Object>> third_trade_no;

}
