package com.personal.demo.response.compute;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author sulu
 * @date 2026年03月13日 11:38 AM
 */
@Data
public class UnusedCouponListDto implements Serializable {
    /**
     * 模板id
     */
    private String template_id;

    /**
     * 券id列表
     */
    private List<String> coupon_ids;

    /**
     * 券码，可能为空，某些活动发放的会有券码
     */
    private List<String> coupon_codes;

    /**
     * 券别名
     */
    private String other_name;

    /**
     * 券名称
     */
    private String title;

    /**
     * 券面值，单位(元)
     */
    private String deno;

    /**
     * 券类型
     */
    private Integer type;

    /**
     * 券生效时间
     */
    private String effective_time;

    /**
     * 券失效时间
     */
    private String failure_time;

    /**
     * 各适用范围的和，该值 & 对应范围的值等于范围值说明适应改范围
     */
    private Integer use_scope;

    /**
     * 券状态
     */
    private Integer status;

    /**
     * 礼品券券折扣
     */
    private Integer discountRate;


    /**
     * 模板加入的券包
     */
    private String coupon_pack;

    /**
     * 是否可以转赠
     */
    private Boolean give_friend;

    /**
     * 是否所有门店
     */
    private Boolean is_all_shop;

    /**
     * 菜品扩展字段
     */
    private String products_ext;


    /**
     * 富文本描述
     */
    private String summary;

    /**
     * ceType-取扩展字段第一条
     **/
    private String subType;

}
