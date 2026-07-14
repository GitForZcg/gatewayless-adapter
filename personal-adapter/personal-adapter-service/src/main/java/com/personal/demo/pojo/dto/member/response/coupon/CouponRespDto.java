package com.personal.demo.pojo.dto.member.response.coupon;

import com.personal.demo.request.member.EmptyParam;
import com.personal.demo.response.member.coupon.PageOptions;
import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author Li QianQian
 * describe:
 */
@Data
public class CouponRespDto {

    /**
     * 模板id
     */
    private String template_id;

    /**
     * 券别名
     */
    private String other_name;

    /**
     * 券id列表
     */
    private List<String> coupon_ids;

    /**
     * 券码，可能为空，某些活动发放的会有券码
     */
    private List<String> coupon_codes;

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
     * 可用门店列表
     */
    private Map<String, String> shop_ids;

    /**
     * 可用门店id列表
     */
    private List<Long> sids;

    /**
     * 菜品(产品)id列表
     */
    private List<String> products;

    /**
     * 券生效时间
     */
    private String effective_time;

    /**
     * 券失效时间
     */
    private String failure_time;

    /**
     * 使用条件与限制
     */
    private List<String> limitations;

    /**
     * 启用金额限制类型
     */
    private Integer limit_type;

    /**
     * 启用金额，结合启用金额限制类型，例如：总消费金额满100元可用 （单位：元） 0为不限制
     */
    private Float enable_amount;

    /**
     * 同一种券一次最多可以使用几张
     */
    private Integer max_use;

    /**
     * 代表是否支持与其他券混合使用
     */
    private Boolean mix_use;

    /**
     * 代表券的混用情况
     */
    private Integer mix_use_value;

    /**
     * 配合mix_use_value字段使用，代表不可以混用的券列表
     */
    private List<Long> limit_mix_coupon;

    /**
     * 是否支持自定义面值，仅礼品券支持自定义面值
     */
    private Boolean is_diy_deno;

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
     * 当前使用时段
     */
    private Integer cycle_type;

    /**
     * 使用时段
     */
    private List<Integer> use_week_day;

    /**
     * 按月使用时段
     *
     */
    // todo 适配样例给的空对象 待确定
    private EmptyParam use_month_day;

    /**
     * 按节假日使用时段
     */
    private List<String> use_holiday_day;

    /**
     * 周期限制券数量
     */
    private Map<String, Integer> period_use_num;

    /**
     * 不可用节假日
     */
    private List<Integer> cant_use_holiday;

    /**
     * 不可用节假日日期
     */
    private List<Integer> cant_use_holiday_dates;

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
     * 券使用时段
     */
    private List<ValidUseTimesRespDto> valid_use_times;

    /**
     * 富文本描述
     */
    private String cSummary;

    /**
     * 分页参数
     */
    private PageOptions pageOptions;

    /**
     * 券扩展字段
     */
    private List<CExtend> cExtend;
}
