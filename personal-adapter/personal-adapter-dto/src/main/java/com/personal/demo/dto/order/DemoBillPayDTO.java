package com.personal.demo.dto.order;

import lombok.Data;

import java.io.Serializable;

/**
 * @author sulu
 * @date 2025年08月08日 11:30 AM
 */
@Data
public class DemoBillPayDTO implements Serializable {
    /**
     * 订单号（必传）
     */
    private String bill_num;
    /**
     * 支付平台
     */
    private Integer payplatform;
    /**
     * 付款明细id
     */
    private String billpayid;
    /**
     * 账单id（必传）
     */
    private String billid;
    /**
     * 门店id（必传）
     */
    private String ognid;
    /**
     * 金额（必传）
     */
    private Double amount;
    /**
     * 折合金额
     */
    private Double sourceamount;
    /**
     * 汇率
     */
    private Double ratio;
    /**
     * 支付方式id
     */
    private Integer pmid;
    /**
     * 支付方式名称
     */
    private String pmname;
    /**
     * 会员号
     */
    private String memberno;
    /**
     * 会员名称
     */
    private String membername;
    /**
     * 会员等级
     */
    private String membertype;
    /**
     * 会员交易流水号
     */
    private String serialno;
    /**
     * 备注
     */
    private String remark;
    /**
     * 会员消费明细id
     */
    private Integer consumeid;
    /**
     * 是否实收（必传）：0-非实收 1-实收
     */
    private Integer paymode;
    /**
     * 实收比列（默认100）
     */
    private Double shishoupercent;
    /**
     * 收银员id
     */
    private Integer payuserid;
    /**
     * 结帐日期（必传）
     */
    private String paydate;
    /**
     * 结帐时间
     */

    private String paytime;
    /**
     * 结账班次
     */
    private Integer payshift;
    /**
     * 找零
     */
    private Double changeamount;
    /**
     * 支付方式类型
     */
    private Integer pmtype;
    /**
     * 是否参与积分
     */
    private Integer isnointegralflag;
    /**
     * 是否可以找零
     */
    private Integer isnochangeflag;
    /**
     * 是否可以开钱箱
     */
    private Integer isnoCashboxflag;
    /**
     * 是否可以开发票
     */
    private Integer isnoinvoiceflag;
    /**
     * 是否下载
     */
    private Integer isdownload;
    /**
     * 实收金额（分，必传）
     */
    private Integer storedpay;
    /**
     * 会员预存实收
     */
    private Integer storedsalepay;
    /**
     * 会员预存折扣
     */
    private Integer freepay;
    /**
     * 券金额
     */
    private Double couponAmount;
    /**
     * 补录标识：0-正常支付 1-支付中补录 2-支付后补录
     */
    private Integer buluflag;
    /**
     * 微生活tcid
     */
    private String tcid;
    /**
     * 预存用-本次交易可开发票额（单位：分；-1代表微生活访问失败）
     */
    private Integer invoiceprice;
    /**
     * 支付方式备注
     */
    private String payremark;
    /**
     * 小费金额
     */
    private Double gratuity;
    /**
     * 付款时间
     */
    private String paymenttime;
    /**
     * 支付方式编号
     */
    private Integer pmnum;
    /**
     * 支付方式通道：0-未知通道 1-原生 2-美团 3-小程序 4-ECO 5-汇付
     */
    private Integer passages;
    /**
     * 收银账单号
     */
    private String billno;
    /**
     * 拆账标识（必传）：未拆账存0，拆账存1
     */
    private Integer issplit;
}
