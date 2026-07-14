package com.personal.demo.pojo.dto.member.response.coupon;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author Li QianQian
 * describe:
 */
@Data
public class CouponDetailRespDto  {

    private static final long serialVersionUID = 1L;

    /**
     * 券id
     **/
    private String user_coupon_id;

    /**
     * 券模板id
     **/
    private String template_id;

    /**
     * 券类型
     **/
    private String type;

    /**
     * 券名称
     **/
    private String name;

    /**
     * 券别名
     **/
    private String other_name;

    /**
     * 券金额
     **/
    private String price;

    /**
     * 券开始时间
     **/
    private String start;

    /**
     * 券结束时间
     **/
    private String end;

    /**
     * 券状态
     **/
    private Integer status;

    /**
     * 券使用时间
     **/
    private String use_time;

    /**
     * 券使用流水
     **/
    private String use_trans_id;

    /**
     * 券发放时间
     **/
    private String create;

    /**
     * status=9时，接收人的卡号。status=1时（仅为别人赠送的券时）是赠送人的卡号
     **/
    private String cno;

    /**
     * 发放门店id,默认 0
     **/
    private Integer send_sid;

    /**
     * 券销售金额
     */
    private Integer sale_money;


}
