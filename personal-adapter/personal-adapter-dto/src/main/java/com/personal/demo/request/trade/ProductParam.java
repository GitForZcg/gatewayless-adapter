package com.personal.demo.request.trade;

import lombok.Data;

import java.util.List;

/**
 * @author Li QianQian
 * describe:
 */
@Data
public class ProductParam {

    /**
     * 菜品(产品)名称，不能为空
     */
    private String name;

    /**
     * 菜品(产品)id；此字段不传的话，会导致无法匹配产品买赠活动
     */
    private String id;

    /**
     * 菜品分类id，is_product_credit传 true 时，该字段要传，不传不送积分
     */
    private String cid;

    /**
     * 菜品(产品)编号
     */
    private String no;

    /**
     * 菜品(产品)数量，不能为0
     */
    private Float num;

    /**
     * 菜品(产品)单价，单位（分）可以为0
     */
    private Integer price;

    /**
     * 【原价】菜品(产品)单价，单位（分）
     */
    private Integer ori_price;

    /**
     * 是否参加活动(已无用，不能传0，请求时传1或2均可，无影响)
     */
    private Integer is_activity;

    /**
     * 菜品标签
     */
    private List<String> tags;

    /**
     * 菜品抵扣券ID(使用场景：券多菜品时使用的券用该字段)
     */
    private List<String> coupons_ids;

    /**
     * 菜品抵扣券ID（使用场景：菜品多价格时，各价格使用的券使用该字段）
     */
    private List<String> product_use_coupon;
}
