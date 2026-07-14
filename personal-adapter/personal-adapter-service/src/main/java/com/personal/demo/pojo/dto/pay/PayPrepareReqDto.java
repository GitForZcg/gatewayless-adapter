package com.personal.demo.pojo.dto.pay;

import com.personal.demo.pojo.base.BasePayPublicParams;
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
public class PayPrepareReqDto implements BasePayPublicParams {

    /**
     * 1、用户支付场景场景值 -Y
     */
    private int scene;
    /**
     * 2、调用方的订单号，一个订单号只能用一次，具体规则见详情 -Y
     */
    private String bizOrderNo;
    /**
     * 3、商品名，对应支付宝的subject字段 微信的body字段.最长50个汉字 -Y
     */
    private String productName;
    /**
     * 4、商品价格，单位：分 -Y
     */
    private Long amount;
    /**
     * 5、购买用户标识 -Y
     */
    private String buyerId;
    /**
     * 6、支付结果通知回调地址 -N
     */
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

    @Override
    public String orderId() {
        return createOrderId(this.bizOrderNo);
    }
}
