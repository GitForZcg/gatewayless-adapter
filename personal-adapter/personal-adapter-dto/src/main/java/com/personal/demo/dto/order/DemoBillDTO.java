package com.personal.demo.dto.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author sulu
 * @date 2025年08月08日 11:26 AM
 */
@Data
public class DemoBillDTO implements Serializable {

    /**
     *订单菜品总成本
     */
    private Double ratedcostamount;
    /**
     * 账单类型
     */
    private Integer billtypes;
    /**
     * 特价菜数量
     */
    private Integer specialoffercount;
    /**
     * 单菜品折扣数量
     */
    private Integer dandiscount;
    /**
     * 赠送笔数
     */
    private Integer freecount;
    /**
     * 再买数量
     */
    private Integer buyagaincount;
    /**
     * 订单主键id（必传）
     */
    private String billid;
    /**
     * 外部订单号（必传）
     */
    private String outorderid;
    /**
     * 点餐后付小程序单号
     */
    private String payOutorderid;
    /**
     * 作废状态：0 未作废 1 作废 2 业务作废
     */
    private Integer isDel;
    /**
     * 门店id
     */
    private String ognid;
    /**
     * 收银订单编号
     */
    private String billno;
    /**
     * 桌台ID
     */
    private Integer tableid;
    /**
     * 人数
     */
    private Integer member;
    /**
     * 是否有最低消费：0-否，1-是
     */
    private Integer lowestflag;
    /**
     * 最低消费金额
     */
    private Double lowest;
    /**
     * 是否有服务费：0-否，1-是
     */
    private Integer serviceflag;
    /**
     * 服务费比例
     */
    private Double service;
    /**
     * 服务费金额
     */
    private Double serviceamount;
    /**
     * 是否茶位费：0-否，1-是
     */
    private Integer teaflag;
    /**
     * 茶位费数量
     */
    private Double tea;
    /**
     * 茶位费金额
     */
    private Double teaamount;
    /**
     * 折扣方案/会员营销方案ID
     */
    private Integer dsid;
    /**
     * 折扣比
     */
    private Double discount;
    /**
     * 折扣额
     */
    private Double discountamount;
    /**
     * 单菜品折扣额
     */
    private Double dandiscountamount;
    /**
     * 不计入最低消费不可打折金额
     */
    private Double nodisamount;
    /**
     * 不计入最低消费可打折金额
     */
    private Double candisamount;
    /**
     * 计入最低消费可打折金额
     */
    private Double lowcandisamount;
    /**
     * 计入最低消费不可打折金额
     */
    private Double lownodisamount;
    /**
     * 消费合计
     */
    private Double consume;
    /**
     * 应收金额
     */
    private Double totalconsume;
    /**
     * 抹零金额
     */
    private Double changeamount;
    /**
     * 非菜品销售金额
     */
    private Double noconsume;
    /**
     * 赠送金额
     */
    private Double freeAmount;
    /**
     * 备注
     */
    private String remark;
    /**
     * 桌台留言
     */
    private String message;
    /**
     * 开台员id
     */
    private Integer wuseepid;
    /**
     * 值台员id
     */
    private Integer useepid;

    private String datetime;
    /**
     * 付款标识
     */
    private Integer payflag;
    /**
     * 印单标识
     */
    private Integer printflag;
    /**
     * 印单次数
     */
    private Integer printedlines;
    /**
     * 结帐次数
     */
    private Integer paylines;
    /**
     * 用餐时间(min)
     */
    private Integer billtime;
    /**
     * 会员卡号
     */
    private String memberno;
    /**
     * 会员名称
     */
    private String membername;
    /**
     * 会员等级名称
     */
    private String membertype;
    /**
     * 是否使用会员 0 没有使用会员 1 使用会员
     */
    private Integer ismemberbill;
    /**
     * 是否开发票：0-不开 1-开
     */
    private Integer invoiceflag;
    /**
     * 应开发票额
     */
    private Double grantinvoice;
    /**
     * 开票金额
     */
    private Double invoice;
    /**
     * 开票人id
     */
    private Integer invoiceuserid;
    /**
     * 桌数
     */
    private Integer tables;
    /**
     * 预定单id
     */
    private Integer billorderid;
    /**
     * 销售员id
     */
    private Integer salesepid;
    /**
     * 是否使用会员价：0-否，1-是
     */
    private Integer memberpriceflag;
    /**
     * 特价菜品差价金额
     */
    private Double pricediff;
    /**
     * 开台餐段id
     */
    private Integer openptid;
    /**
     * 结账餐段id
     */
    private Integer payptid;
    /**
     * 订单归属营业日（必传，格式：yyyy-MM-dd）
     */

    private String paydate ;
    /**
     * 订单支付时间（格式：yyyy-MM-dd HH:mm）
     */
    private String paytime;

    private String cleantime;
    /**
     * 收银员id
     */
    private Integer payuserid;
    /**
     * 结账班次
     */
    private Integer payshift;
    /**
     * 审帐标识
     */
    private Integer checkflag;
    /**
     * 审帐人id
     */
    private Integer checkerid;
    /**
     * 折扣理由
     */
    private String discountreason;
    /**
     * 会员等级id
     */
    private Integer membertypeid;
    /**
     * 金额精度抹零金额
     */
    private Double percentchangeamount;
    /**
     * 营业外收入
     */
    private Double vouchernoconsume;
    /**
     * 包装费
     */
    private Double packing;
    /**
     * 订单来源：0普通订单 1扫付订单 2微信订单
     */
    private Integer source;
    /**
     * 折扣类型：0- 1-订单折扣，3-会员营销
     */
    private Integer dstype;
    /**
     * 会员营销类型：null-全部使用 0:全部使用 1:仅适用会员价 2:仅适用全单折扣
     */
    private Integer memberpricetype;
    /**
     * 特价优惠金额
     */
    private Double specialoffer;
    /**
     * 发票抬头
     */
    private String invoiceheader;
    /**
     * 外卖订单类型：1-美团，2-百度，3-饿了么，4-微信外卖
     */
    private Integer tatype;
    /**
     * 电子发票地址
     */
    private String qrcode;
    /**
     * 发票请求流水号
     */
    private String invoicelsh;
    /**
     * 发票号码
     */
    private String invoicecode;
    /**
     * 会员手机号
     */
    private String memberphone;
    /**
     * 纳税人识别号
     */
    private String nsrsbh;
    /**
     * 桌台占用方式：1 开台 2 搭台
     */
    private Integer usingtype;
    /**
     * 先付对应的主桌台账单的id
     */
    private String fatherbillid;
    /**
     * 开台使用设备：1 PC-POS 2 移动POS 3 点菜宝 4 感应餐盘 5 微信点菜
     */
    private Integer devicetype;
    /**
     * 支付模式：0 后付模式 1 先付模式
     */
    private Integer choosepayment;
    /**
     * 结账后是否做了清台操作：0 否 1 是
     */
    private Integer iscleantable;
    /**
     * 订单实收金额
     */
    private Double realprice;
    /**
     * 支付宝口碑可参与优惠金额（-1代表无限制）
     */
    private Double alipayprice;
    /**
     * 美团券可抵扣金额（-1代表无限制）
     */
    private Double couponsprice;
    /**
     * 会员代金券可抵扣金额（-1代表无限制）
     */
    private Double membercouponsprice;
    /**
     * 纸质代金券可抵扣金额（-1代表无限制）
     */
    private Double papercouponsprice;
    /**
     * 可参与其他优惠金额（-1代表无限制）
     */
    private Double otherdiscountprice;
    /**
     * 可参与优惠金额-打印、报表用（-1代表不打印、不显示）
     */
    private Double favorableamount;
    /**
     * 不可参与优惠金额-打印、报表用（-1代表不打印、不显示）
     */
    private Double unfavorableamount;
    /**
     * 人为设置的订单人数，区分菜品销量统计人数用
     */
    private Integer setmember;
    /**
     * 预结时间（格式：yyyy-MM-dd HH:mm）
     */
    private String pretime;
    /**
     * 订单上传状态
     */
    private Integer uploadstatus;
    /**
     * 是否是餐标账单：0、否 1、是
     */
    private Integer mealmarkflag;
    /**
     * 餐标id
     */
    private Integer mealmarkid;
    /**
     * 是否是餐标：0、否 1、管理菜品的餐标 2、不管理菜品的餐标
     */
    private Integer mealflag;
    /**
     * 餐标总人数
     */
    private Integer mealtotalnum;
    /**
     * 微生活活动券id
     */
    private String expandid;
    /**
     * 微生活活动券模板id
     */
    private String expandtempletid;
    /**
     * 评价url
     */
    private String evaluateurl;
    /**
     * 是否异常订单-财务一键异常审核用：0-非异常 1-异常
     */
    private Integer abnormal;
    /**
     * 茶位费出品部门
     */
    private Integer teacdid;
    /**
     * 服务费出品部门id
     */
    private Integer servicecdid;
    /**
     * 营业外收入出品部门id
     */
    private Integer noconsumecdid;
    /**
     * 是否品智微信点菜订单：1是，0否
     */
    private Integer ispzwexin;
    /**
     * 支付方式类型
     */
    private Integer pmtype;
    /**
     * 1取餐号 2台卡号
     */
    private Integer notype;
    /**
     * 再买优惠金额
     */
    private Double buyagainamount;
    /**
     * 班次id
     */
    private String billshiftid;
    /**
     * 1早班 2中班 3晚班
     */
    private Integer stid;
    /**
     * 外带账单餐盒费
     */
    private Double boxtotalamount;
    /**
     * 茶位费实收金额
     */
    private Double tearealprice;
    /**
     * 茶位费非实收金额
     */
    private Double teanorealprice;
    /**
     * 服务费实收金额
     */
    private Double servicerealprice;
    /**
     * 服务费非实收金额
     */
    private Double servicenorealprice;
    /**
     * 营业外收入实收金额
     */
    private Double noconsumerealprice;
    /**
     * 营业外收入非实收金额
     */
    private Double noconsumenorealprice;
    /**
     * 餐盒费收入出品部门id
     */
    private Integer packingcdid;
    /**
     * 餐盒费实收金额
     */
    private Double packingrealprice;
    /**
     * 餐盒费非实收金额
     */
    private Double packingnorealprice;
    /**
     * 折扣券优惠金额
     */
    private Double expandsale;
    /**
     * 小费出品部门id
     */
    private Integer gratuitycdid;
    /**
     * 小费金额
     */
    private Double gratuity;
    /**
     * 小费非实收
     */
    private Double gratuitynorealprice;
    /**
     * 小费实收
     */
    private Double gratuityrealprice;
    /**
     * 订单折扣理由id
     */
    private Integer discountreasonid;
    /**
     * 门票超时出品部门id
     */
    private Integer timeoutcdid;
    /**
     * 门票超时出品部门
     */
    private Double timeoutprice;
    /**
     * 门票超时非实收金额
     */
    private Double timeoutnorealprice;
    /**
     * 门票超时实收金额
     */
    private Double timeoutrealprice;
    /**
     * 门票超时时间(min)
     */
    private Integer tickdelay;
    /**
     * 渠道：0.未知渠道 1. 收银点餐 2. 小程序自助结账 3 品智H5自助结账 4 外卖订单
     */
    private Integer channels;
    /**
     * 是否计算开台率：0 不计入，1计入
     */
    private Integer isnoopentable;
    /**
     * 营业区id
     */
    private Integer blid;
    /**
     * 订单抹零理由id
     */
    private Integer smallchangereasonid;
    /**
     * 订单抹零理由
     */
    private String smallchangereason;
    /**
     * 折扣券优惠范围：0-菜品 1-菜类
     */
    private Integer expandscope;
    /**
     * 美团一体支付订单id
     */
    private Integer meituanAllinPayId;
    /**
     * 美团一体支付订单二维码地址
     */
    private String meituanAllinPayCode;

    private String calendate;
    /**
     * 订单是否是包含微信点餐：0否 1是
     */
    private Integer iswxorder;
    /**
     * 账单中是否包含退菜：1 包含 0 不包含
     */
    private Integer billbackflag;
    /**
     * 账单中是否包含临时菜：1包含
     */
    private Integer haschangenameflag;
    /**
     * 是否是免单标示：0 否 1 是
     */
    private Integer billfreeflag;
    /**
     * 业务类型：1堂食2外卖3外带
     */
    private String businesstype;
    /**
     * 账单金额，包括菜品合计+茶位费+服务费+外带餐盒费+营业外收入+超时费+小费(元)
     */
    private Double bill_amount;
    /**
     * 账单退菜金额合计(元)
     */
    private Double return_amount;
    /**
     * OMP账单号（必传）
     */
    private String bill_num;
    /**
     * 冲减单对应的原始账单号（冲减单必传）
     */
    private String copy_bill_num;
    /**
     * 收银账单号
     */
    private String third_bill_num;
    /**
     * 账单状态：原始账单 此值为空；反结单 CJ01；部分退冲减单（必传）
     */
    private String bill_state;
    /**
     * 账单来源，传PZ
     */
    private String order_source;
    /**
     * 台卡号
     */
    private String tainumber;
    /**
     * 取餐号
     */
    private String rbillno;
}
