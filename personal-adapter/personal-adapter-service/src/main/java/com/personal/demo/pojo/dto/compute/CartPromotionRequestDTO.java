package com.personal.demo.pojo.dto.compute;

import com.personal.demo.pojo.base.DemoComputeMd5Param;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * 购物车算价请求参数
 * @author sulu
 * @date 2026年02月09日 4:09 PM
 */

@Data
public class CartPromotionRequestDTO implements DemoComputeMd5Param, Serializable {

    /**
     * 商户ID
     */
    private String mid;

    /**
     * 门店UUID
     */
    private String sid;

    /**
     * 品牌ID（微生活 id）
     */
    private String bid;

    /**
     * Demo ID
     */
    private String demoId;

    /**
     * 用户OpenID
     */
    private String openId;
    /**
     * 主版本号，默认为0
     */
    private Integer majorVersion;

    /**
     * 1小程序堂食，2POS堂食，3小程序+POS堂食，4外卖；固定 1
     */
    private Integer source;

    /**
     * 茶位费（单位：分）
     */
    private Integer teaPrice;

    /**
     * 服务费（单位：分）
     */
    private Integer servicePrice;

    /**
     * deliveryPrice
     */
    private Integer deliveryPrice;

    /**
     * 小费（单位：分）
     */
    private Integer tipPrice;

    /**
     * 是否登录标识
     */
    private String isLogin;

    /**
     * 是否计算券：0不计算，1计算
     */
    private Integer isUseCoupon;
    /**
     * 菜品列表
     */
    private List<DishesDTO> dishesList;
    /**
     * 会员资产列表
     */
    private List<String> couponList;

    /**
     * 优惠券列表
     */
    private List<WXCouponsDTO> weixinCoupons;

    /**
     * 支付方式列表（微信支付、储值余额）
     */
    private List<String> paymentList;

    @Override
    public Set<String> needSign() {
        return null;
    }

    @Override
    public Set<String> needSignParam() {
        return null;
    }

    @Override
    public String orderId() {
        return null;
    }
}
