package com.personal.demo.pojo.dto.trade.request;

import com.personal.demo.pojo.base.BaseTradePublicParam;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class OrderCommitReqDto implements BaseTradePublicParam {

    /**
     * 交易业务号，收银方保证全部门店唯一，提交交易需要biz_id
     */
    private String biz_id;
    /**
     * 如果使用储值，根据交易预览接口verify_sms/verify_password返回值，需要传短信验证码或交易密码
     */
    private String verify_code;
    /**
     * 订单号
     */
    private String third_order_id;
    /**
     * 是否延迟赠送积分
     */
    private String delayed_award_credit;
    /**
     * 交易使用的活动优惠列表
     */
    private List<Map<String, Object>> discount_list;

    @Override
    public String orderId() {
        return createOrderId(biz_id);
    }
}
