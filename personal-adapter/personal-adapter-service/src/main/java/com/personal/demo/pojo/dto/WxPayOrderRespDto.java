package com.personal.demo.pojo.dto;


import com.personal.demo.pojo.base.BaseOrderPublicParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 微信支付响应体
 */
@Setter
@Getter
@ToString
@Accessors(chain = true)
public class WxPayOrderRespDto implements BaseOrderPublicParam {

    private String merId;
    private String orderId;

    @Override
    public String orderId() {
        return createOrderId(orderId);
    }
}
