package com.personal.demo.request.pay;

import com.personal.demo.request.group.ValidationGroups;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 预支付参数
 * @date 2025/10/21 14:26
 */

@Data
@Accessors(chain = true)
public class PayPrepareRequest {


    @NotBlank(message = "支付号不能为空", groups = ValidationGroups.payGroup.class)
    private String baid;
    /**
     * 1、用户支付场景场景值 -Y
     */
    @NotNull(message = "用户支付场景不能为空", groups = ValidationGroups.payGroup.class)
    private int scene;
    /**
     * 2、调用方的订单号，一个订单号只能用一次，具体规则见详情 -Y
     */
    @NotBlank(message = "订单号不能为空", groups = ValidationGroups.payGroup.class)
    private String bizOrderNo;
    /**
     * 3、商品名，对应支付宝的subject字段 微信的body字段.最长50个汉字 -Y
     */
    @NotBlank(message = "商品名称不能为空", groups = ValidationGroups.payGroup.class)
    private String productName;
    /**
     * 4、商品价格，单位：分 -Y
     */
    @NotNull(message = "商品价格不能为空", groups = ValidationGroups.payGroup.class)
    private Long amount;

    /**
     * 5、购买用户标识 -Y
     */
    @NotBlank(message = "用户标识不能为空", groups = ValidationGroups.payGroup.class)
    private String buyerId;

    /**
     * 6、支付结果通知回调地址 -N
     */
    @NotBlank(message = "回调地址不能为空", groups = ValidationGroups.payGroup.class)
    private String notifyUrl;

    /**
     * 7、业务方自定义参数 -N
     */
    private String bizAttach;

    /**
     * 8、商户门店编号 -N
     */
    private String storeId;

    /**
     * 9、门店名称 -N
     */
    private String storeName;

    /**
     * 10、终端号设备号 -R
     */
    private String deviceInfo;

    /**
     * 11、商家扫用户场景时的用户条码 -Y
     */
    private String authCode;

    /**
     * 12、用户支付场景值 -N
     */
    private String mchScanUserScene;

    /**
     * 13、微信用统一下单参数 订单优惠标记 -N
     */
    private String goodsTag;

    /**
     * 14、支付宝用参与优惠计算的金额 单位为分 -N
     */
    private long discountableAmount;

    /**
     * 15、用于支付宝小程序，进行商户自运营支付宝券的相关营销逻辑控制 -N
     */
    private String foodOrderType;

    /**
     * 16、通道接口版本 -N
     */
    private String channelApiVersion;

    /**
     * 17、商品详情 -N
     */
    private String detail;

    /**
     * 18、客户端IP -N
     */
    private String clientip;

    /**
     * 19、订单失效时间 -N
     */
    private int timeExpire;

    /**
     * 20、转账收款方baid -N
     */
    private String payeeId;

}
