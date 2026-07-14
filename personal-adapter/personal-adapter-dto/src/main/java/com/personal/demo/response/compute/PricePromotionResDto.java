package com.personal.demo.response.compute;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author sulu
 * @date 2026年02月10日 2:48 PM
 */
@Data
public class PricePromotionResDto implements Serializable {

    /**
     * 是否禁用会员卡
     */
    private Boolean disableCard;

    /**
     * 是否禁用优惠券
     */
    private Boolean disableCoupon;

    /**
     * 卖卡卖券立减金额
     */
    private Integer buyCouponDiscount;

    /**
     * 执行唯一标识
     */
    private String executeKey;

    /**
     * 订单总金额（合计总金额=菜品小计+茶位费+服务费+配送费+餐盒费+小费）
     */
    private Integer orderSumAmount;

    /**
     * 订单原始价格(订单小计=商品原价+茶位费+配送费+餐盒费)
     */
    private Integer orderOriginPrice;

    /**
     * 总优惠金额
     */
    private Integer discountAmount;

    /**
     * 菜品小计金额
     */
    private Integer orderAmount;

    /**
     * 菜品原价小计
     */
    private Integer originalPrice;

    /**
     * 菜品会员价小计
     */
    private Integer memberPrice;

    /**
     * 菜品原价之和
     */
    private Integer itemOriginalPriceSum;

    /**
     * 菜品特价小计
     */
    private Integer salePrice;

    /**
     * 总餐盒费
     */
    private Integer totalBoxAmount;

    /**
     * 配送费
     */
    private Integer deliveryAmount;

    /**
     * 服务费
     */
    private Integer serviceAmount;

    /**
     * 茶位费
     */
    private Integer teaAmount;

    /**
     * 小费
     */
    private Integer tipAmount;

    /**
     * 优惠后金额
     */
    private Integer afterAmount;

    /**
     * 最大优惠金额
     */
    private Integer maxDiscount;

    /**
     * 免配送费
     */
    private Integer freeDelivery;

    /**
     * 购买券包待支付金额
     */
    private Integer couponAmount;

    /**
     * 储值免单待支付金额
     */
    private Integer storedAmount;

    /**
     * 本批次实收金额(单位:分)
     */
    private Integer finalAmount;

    /**
     * 余额不足充值后的支付金额
     */
    private Integer balanceAmount;

    /**
     * 会员资产券优惠金额
     */
    private Integer couponDiscountAmount;

    /**
     * 积分抵现金额
     */
    private Integer creditAmount;

    /**
     * 积分使用数量
     */
    private Integer creditCount;

    /**
     * 会员+特价优惠金额
     */
    private Integer preferentialAmount;

    /**
     * 充值支付可节省的金额
     */
    private Integer diffBalance;

    /**
     * 储值免单赠送金额
     */
    private Integer giveAmount;

    /**
     * 默认支付方式
     */
    private String defaultPayType;

    /**
     * 账户余额
     */
    private Integer balance;

    /**
     *
     */
    private Map<String, Object> sale_money_list;

    /**
     * 支付方式列表
     */
    private List<String> paymentList;

    /**
     * 加价购提醒列表
     */
    private List<Object> increaseBuyList;

    /**
     * 买赠列表
     */
    private List<Object> buyPlusList;

    /**
     * 规则券列表
     */
    private List<Object> ruleCouponList;

    /**
     * 可购买的卡包列表
     */
    private List<Object> ruleCardList;

    /**
     * 储值免单列表
     */
    private List<Object> storedFreeList;

    /**
     * 失败的促销活动集合
     */
    private List<Object> failurePromotions;

    /**
     * 查看菜品列表
     */
    private List<ViewDishesItemResDto> viewDishesList;

    /**
     * 已执行的规则列表
     */
    private List<Object> executedRuleList;

    /**
     * 执行信息
     */
    private ExecuteInfoResDto executeInfo;

    /**
     * 可叠加的促销ID列表
     */
    private List<Long> additivityPromotionIdList;

    /**
     * 禁用的第三方支付方式列表
     */
    private List<Object> disableThirds;

    /**
     * 触发提示列表
     */
    private List<Object> noticeList;

    /**
     * 提示消息映射
     */
    private Map<String, Object> noticeMessageList;

    /**
     * 微信是否可以使用会员价
     */
    private Boolean weChatCanUseMemberPrice;

    /**
     * 储值支付是否有余额要求
     */
    private Boolean balanceRequirements;

    /**
     * 高级券核销列表
     */
    private List<Object> highCouponWriteOffList;

    /**
     * 批次汇总列表
     */
    private List<BatchSummaryItemResDto> batchSummaryList;

    /**
     * 优惠券订阅列表
     */
    private List<CouponDishUseDTO> couponSubscribeList;

    /**
     * 代金券订阅列表
     */
    private List<CouponCashInfoResDto> vouchersSubscribeList;

    /**
     * 互斥的促销ID列表
     */
    private List<Object> mutexPromotionIdList;

    /**
     * 互斥的支付菜品列表
     */
    private List<Object> mutexPaymentDishesList;

    /**
     * 排除的规则列表
     */
    private List<Object> excludeRules;

    /**
     * 优惠券类型限制
     */
    private List<Object> couponTypeLimit;

    /**
     * 要增加的SKU ID列表
     */
    private List<Object> toIncSkuIds;

    /**
     * 已使用的优惠券ID列表
     */
    private List<Object> usedCouponIds;

    /**
     * 互斥内容
     */
    private Map<String,Object> mutexContent;

    /**
     * 所有优惠券折扣金额
     */
    private Integer allcouponDiscountAmount;

    /**
     * 本方案不可使用的原因
     */
    private Map<String,String> couponFailReasons;


}
