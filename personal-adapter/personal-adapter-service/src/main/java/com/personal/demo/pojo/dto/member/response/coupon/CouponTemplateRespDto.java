package com.personal.demo.pojo.dto.member.response.coupon;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author Li QianQian
 * describe:
 */
@Data
public class CouponTemplateRespDto {

    private static final long serialVersionUID = 1L;

    /**
     * 券模板id
     **/
    private Integer template_id;

    /**
     * 券名称
     **/
    private String name;


    /**
     * 券别名
     **/
    private String alias_name;

    private Integer discountRule;
    /**
     * 券描述
     **/
    private String summary;

    /**
     * 券类型 1:代金券 2:礼品券
     **/
    private Integer type;

    /**
     * 券有效期
     **/
    private String valid_data;

    /**
     * 创建券的管理员名称
     **/
    private String creator;

    /**
     * 券适用门店
     **/
    private String shopList;

    /**
     * 菜品(产品)id列表
     **/
    private List<String> products;

    /**
     * 创建日期
     **/
    private String created;

    /**
     * 面值
     **/
    private String deno;

    /**
     * 启用金额
     **/
    private Integer enable_amount;

    /**
     * 最多使用多少张
     **/
    private Integer max_use;

    /**
     * 结算金额（分）
     **/
    private Integer saleMoney;

    /**
     * 当前使用时段 1. 周(use_week_day ) 2. 月(use_month_day ) 3.节假日(use_holiday_day )
     **/
    private Integer cycle_type;

    /**
     * 使用时段
     **/
    private List<String> use_week_day;

    /**
     * 可能是对象数据结构，暂时接口未定义，返回看不懂字段
     **/
    //private List<Object> use_month_day;

    /**
     * 按节假日使用时段
     **/
    private List<String> use_holiday_day;

    /**
     * 周期限制券数量
     **/
    private Map<String, Integer> period_use_num;

    private Integer use_scope;
    /**
     * 菜品扩展id
     **/
    private String products_ext;

    /**
     * 使用范围 &值 1 门店消费，2 商城消费，4 外卖消费， 8 预定消费
     **/
    private Integer useScope;

    /**
     * 是否可以转增 true 可以，false 不可以
     **/
    private Boolean give_friend;

    /**
     * 混用类型 0 可以混用，1 不可以混用，2部分混用
     **/
    private Integer mix_use;

    /**
     * 部分混用时限制混用的券
     **/
    private Map<String, String> limit_mix_coupon;

    /**
     * 礼品券券折扣 0表示无折扣。 折扣区间1-99
     **/
    private Integer discountRate;

    /**
     * 启用时间
     **/
    private Integer enable_time;

    /**
     * 时段设置
     **/
    private List<String> period_sets;

    /**
     * 节假日不可以类型，默认为0 0 无限制， 1 法定节假日当天不可用，2 法定节假日假期不可用
     **/
    private Integer holiday_type;

    /**
     * 节假日 1-元旦 2-春节 3-清明 4-五一 5-端午 6-中秋 7-国庆
     **/
    private List<Integer> holiday_info;

    /**
     * 启用金额限制方式:0 表示满足enable_amount 可以用券。1表示 每满enable_amount可以用一张
     **/
    private Integer limit_type;

    /**
     * 券状态 1 正常，2  归档
     **/
    private Integer status;

    /**
     * 不可用节假日，1-元旦 2-春节 3-清明 4-五一 5-端午 6-中秋 7-国庆,没有设置为空数组
     **/
    private List<Integer> cant_use_holiday;

    /**
     * 不可用节假日日期,没有设置为空数组
     **/
    private List<String> cant_use_holiday_dates;

    /**
     * 券属性， 1 营销活动券。
     **/
    private Integer attribute;

    /**
     * 是否支持自定义面值，仅礼品券支持自定义面值
     **/
    private Boolean is_diy_deno;

    /**
     * 券样式
     **/
    private CouponBg coupon_bg;

    /**
     * 1 微信商家券
     **/
    private Integer sync_busifavor;

}
