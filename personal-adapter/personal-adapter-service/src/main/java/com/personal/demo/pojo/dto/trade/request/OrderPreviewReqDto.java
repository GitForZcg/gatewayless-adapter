package com.personal.demo.pojo.dto.trade.request;

import com.personal.demo.pojo.base.BaseTradePublicParam;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author Li QianQian
 * describe:订单预览请求参数
 */
@Data
public class OrderPreviewReqDto implements BaseTradePublicParam {

    /**
     * 用户卡号
     */
    private String cno;

    /**
     * 消费门店id，h5在线消费is_mall =1时门店可以不传默认总部门店，其它情况必传
     */
    private Integer shop_id;

    /**
     * 收银员id【注：传-1则为API默认收银员】，门店为总部门店时收银必须是-1
     */
    private Integer cashier_id;

    /**
     * 消费总金额(单位:分)
     */
    private Integer consume_amount;

    /**
     * 实际支付金额(单位:分) 不传默认为0
     */
    private Integer payment_amount;

    /**
     * 支付方式
     */
    private Integer payment_mode;

    /**
     * 消费使用储值金额(单位:分)
     */
    private Integer sub_balance;

    /**
     * 储值使用类型：0、1 或者空则保持原来逻辑(按比例扣减实收和奖励)，2、只是用储值本金；3、只使用储值赠送；4、先使用储值本金在使用值赠送；5、先使用赠送在使用储值本金
     */
    private Integer sub_balance_type;

    /**
     * 积分扣抵金额，单位元。不在表示抵扣的积分个数
     */
    private Integer sub_credit;

    /**
     * 自定义消费赠送积分(单位:分)
     */
    private Integer credit_amount;

    /**
     * 使用的代金券列表
     */
    private List<String> deno_coupon_ids;

    /**
     * 使用的礼品券列表
     */
    private List<String> gift_coupons_ids;

    /**
     * 使用的可自定义金额的礼品券列表（券面值单位:分）
     */
    private List<DiyGiftCouponPayReqDto> diy_gift_coupon_pay;

    /**
     * 参加的活动列表
     */
    private List<String> activity_ids;

    /**
     * 参加累计次数活动，本次交易应累计的次数
     */
    private Integer count_num;

    /**
     * 交易备注
     */
    private String remark;

    /**
     * 交易标签
     */
    private List<String> tags;

    /**
     * 菜品(产品)明细
     */
    private List<ProductReqDto> products;

    /**
     * 桌台信息/桌台ID
     */
    private String table_id;

    /**
     * 交易业务号，收银方保证全部门店唯一，提交交易需要biz_id
     */
    private String biz_id;

    /**
     * 实际支付金额的支付方式组成，{"支付方式1":支付金额(分)，"支付方式2":支付金额(分)}，此数组金额累加要与实际支付金额payment_amount字段的值相等
     */
    private Map<String, Integer> pay_info;

    /**
     * 参与消费返券活动的金额,-1表示不参与消费返券
     */
    private Integer activity_amount;

    /**
     * 员工信息
     */
    private List<EmployeeReqDto> employees;

    /**
     * 默认0,h5在线消费设置为1，当为1是门店可以不传，此时默认为总部门店,总部门店时收银员要传-1
     */
    private Integer is_mall;

    /**
     * 使用的折扣券
     */
    private List<DiscountCouponReqDto> discount_coupons_ids;

    /**
     * 默认0，16:微信（不需要交易验证）
     */
    private Integer source;

    /**
     * 不传默认为3。3. 门店 16. 商城 17. 外卖(16,17 不需要交易验证)
     */
    //private Byte channelType;

    /**
     * 不传默认为0。0: 到店消费 16: 超客商城  17: 外卖   15: 美团闪惠买单   18: 新零售(三方)
     */
    private Byte secondary_channel_type;

    /**
     * 是否发送消费通知 0不发送 1发送(默认)
     */
    private Integer send_notification;

    /**
     * 要扣减的积分个数(与sub_credit不可同时使用)
     */
    private Integer deduct_credit_num;

    /**
     * 要扣减的积分抵扣的金额（单位：分）(与sub_credit不可同时使用)
     */
    private Integer deduct_credit_amount;

    /**
     * 是否按菜品分类赠送积分 默认 false,true为按菜品分类赠送积分
     */
    private Boolean is_product_credit;

    /**
     * 是否不检查 会员的互斥逻辑（券，积分，储值的互斥，券与券的互斥）
     */
    private Integer notCheckAssetsClash;

    /**
     * 订单总金额（单位:分）
     */
    private Integer order_amount;

    /**
     * 订单优惠金额（单位:分）
     */
    private Integer order_discount_amount;

    @Override
    public String orderId() {
        return null;
    }
}
