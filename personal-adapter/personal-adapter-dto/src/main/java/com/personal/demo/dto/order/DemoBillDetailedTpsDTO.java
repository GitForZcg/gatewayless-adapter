package com.personal.demo.dto.order;

import lombok.Data;

import java.io.Serializable;

/**
 * @author sulu
 * @date 2025年08月08日 11:32 AM
 */
@Data
public class DemoBillDetailedTpsDTO implements Serializable {
    /**
     * 免单金额
     */
    private Double freebillamount;
    /**
     * 门店id（必传）
     */
    private String ognid;
    /**
     * 配料明细表id（必传）
     */
    private String billdetailedid;
    /**
     * 订单所属营业日（必传，格式：yyyy-MM-dd HH:mm）
     */
    private String paydate;
    /**
     * 订单id（必传）
     */
    private String billid;
    /**
     * 配料id（必传）
     */
    private Integer tpid;
    /**
     * 配料单位ID（必传）
     */
    private Integer dnid;
    /**
     * 配料编码（必传）
     */
    private String tpno;
    /**
     * 配料名称（必传）
     */
    private String tpname;
    /**
     * 实际销售价
     */
    private Double tpprice;
    /**
     * 原价金额
     */
    private Double tppricea;
    /**
     * 销售数量
     */
    private Integer count;
    /**
     * 非实收金额
     */
    private Double norealprice;
    /**
     * 实收金额
     */
    private Double realprice;
    /**
     * 优惠标识：
     * 0-没有优惠信息
     * 1-赠送
     * 2-单品折扣
     * 3-再买优惠
     * 4-订单折扣
     * 5-满减优惠
     * 6-会员优惠（会员折扣）
     */
    private Integer benefitflag;
    /**
     * 授权人id（不需要授权时，存放操作员id）
     */
    private Integer userid;
    /**
     * 折扣比
     */
    private Double discount;
    /**
     * 配料优惠金额
     */
    private Double benefitamount;
    /**
     * 操作员id
     */
    private Integer operatorid;
    /**
     * 理由id（手动填写的，理由ID为-2）
     */
    private Integer reasonid;
    /**
     * 理由（如：客人生日）
     */
    private String remark;
    /**
     * 配料二级菜类ID（必传）
     */
    private Integer rcid;
    /**
     * 出品部门ID
     */
    private Integer cdid;
    /**
     * 点菜时间（必传，格式：yyyy-MM-dd HH:mm）
     */
    private String sendtime;
    /**
     * 操作时间（退菜、赠菜，如果没有特殊的与点菜时间相同）（必传）
     */
    private String operatetime;
    /**
     * 配料销售记录id
     */
    private String billdetailedtpid;
    /**
     * 套餐标识：
     * 1-普通菜
     * 2-套餐主项
     * 3-套餐明细（必传）
     */
    private Integer packflag;
    /**
     * 账单结账自然日（必传，格式：yyyy-MM-dd）
     */
    private String calendate;
    /**
     * 是否配料：0-不是 1-是
     */
    private Integer ismaterial;

}
