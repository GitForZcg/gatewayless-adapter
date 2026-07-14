package com.personal.demo.pojo.dto;

import com.personal.demo.pojo.base.BaseOrderPublicParam;
import com.personal.demo.pojo.base.BaseValidationSignParam;
import com.personal.demo.request.group.ValidationGroups;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信支付请求体  y-必传 n-非必传
 */
@Setter
@Getter
@ToString
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class WxPayOrderReqDto extends BaseValidationSignParam implements BaseOrderPublicParam {

    /**
     * 8、商户订单号-y 
     */
    @NotEmpty(message = "orderId为空", groups = ValidationGroups.externalDemoProductGroup.class)
    private String orderId;
    /**
     * 29、子单信息-y
     */
    @Valid
    @NotEmpty(message = "subOrderList为空", groups = ValidationGroups.externalDemoProductGroup.class)
    private List<WxPaySubOrderReqDto> subOrderList;

    @Override
    public String orderId() {
        return createOrderId(orderId);
    }

    @Override
    protected Map<String, Object> getHeaderParams() {
        // 返回微信支付特有的头部参数（如果有的话）
        Map<String, Object> params = new HashMap<>();
        // 例如：params.put("payType", "wechat");
        return params;
    }

    @Override
    public Map<String, Object> getSignatureParams() {
        // 获取父类的基础签名参数（appid, sign, apisign, timestamp）
        Map<String, Object> params = super.getSignatureParams();

        // 添加子类特有的头部参数
        params.putAll(this.getHeaderParams());

        // 添加业务参数
        params.putAll(this.getOriginalParams());

        return params;
    }

    @Override
    public Map<String, Object> getOriginalParams() {
        Map<String, Object> params = new HashMap<>();
        params.put("orderId", orderId);
        params.put("subOrderList", subOrderList);
        return params;
    }


}
