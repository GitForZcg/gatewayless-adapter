package com.personal.demo.pojo.dto.member.response.coupon;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author Li QianQian
 * describe:
 */
@Data
public class InvalidCouponListRespDto {
    /**
     * 券模板id
     */
    private int template_id;
    /**
     * 券别名
     */
    private String other_name;

    /**
     * 券类型 1 代金券；2礼品券；4券包
     */
    private int type;

    /**
     * 券名称
     */
    private String title;

    /**
     * 券别名
     */
    private String alias_name;

    /**
     * 代金券面值
     */
    private String deno;

    /**
     * 券id列表
     */
    private List<String> coupon_ids;

    /**
     * 可用门店
     */
    private Map<String, String> shop_ids;

    /**
     *
     */
    private List<Long> sids;

    /**
     *
     */
    private List<String> products;

    /**
     * 当前使用时段 1. 周(use_week_day ) 2. 月(use_month_day ) 3.节假日(use_holiday_day ) 备注:同时只满足一种类型
     */
    private int cycle_type;

    /**
     * 按周使用时段 星期几可用1 ： ‘周一’, 2 ： ‘周二’, 3 ： ‘周三’, 4 ： ‘周四’, 5 ： ‘周五’, 6 ： ‘周六’, 0 ： ‘周日’
     */
    private List<String> use_week_day;

    /**
     * 按月使用时段 { "type":1,"value":"1,3,6,8,23" }, type: 1. 可用 2. 不可用
     */
    private UseMonthDay use_month_day;

    /**
     * 按节假日使用时段 ["春节", "国庆节"]
     */
    private List<String> use_holiday_day;

    /**
     * 周期限制券数量 {"type": 1,"num": 3} ,type: 1.天 2.周 3.月 4.年
     */
    private PeriodUseNum period_use_num;

    /**
     * 不可用节假日，1-元旦 2-春节 3-清明 4-五一 5-端午 6-中秋 7-国庆,没有设置为空数组
     */
    private List<Integer> cant_use_holiday;

    /**
     * 不可用节假日日期,没有设置为空数组
     */
    private List<Long> cant_use_holiday_dates;

    /**
     * 券图片
     */
    private CouponBg coupon_bg;

    /**
     * 折扣券的折扣比例
     */
    private Integer discountRate;

    private String effective_time;

    private String failure_time;

    private Boolean is_all_shop;

    private Boolean give_friend;

    private Integer limit_type;

    private String products_ext;

    /**
     *
     * 存放的是对象，对象内容如下：
     * "name": "全天可用",
     *  "start": "00:00",
     *  "end": "23:59"
     *
     *
     */
    //private List<String> valid_use_times;

    private Integer use_scope;

    private Integer attribute;

    private Integer give_channel;

    private Integer sid;

    private String issued_time;



    /**
     * wxProductId
     **/
    private String wxProductId;

    /**
     * 券扩展字段
     */
    private CExtend cExtend;

    /**
     * 富文本描述
     */
    private String cSummary;


}
