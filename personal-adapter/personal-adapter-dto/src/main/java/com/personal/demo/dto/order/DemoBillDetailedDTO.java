package com.personal.demo.dto.order;

import lombok.Data;

/**
 * @author sulu
 * @date 2025年08月08日 11:29 AM
 */
@Data
public class DemoBillDetailedDTO implements Cloneable {
    /**
     * 优惠标识：
     * 0-没有优惠信息
     * 1-赠送
     * 2-单品折扣
     * 3-再买优惠
     * 4-订单折扣
     * 5-满减优惠
     * 6-会员优惠（会员折扣）
     * 7-特价
     */
    private Integer benefitflag;
    /**
     * 优惠金额
     */
    private Double benefitamount;
    /**
     * 免单金额
     */
    private Double freebillamount;
    /**
     * 菜品金额
     */
    private Double dishesamount;
    /**
     * 订单明细主键id（必传）
     */
    private String billdetailedid;
    /**
     * 对应订单id
     */
    private String billid;
    /**
     * 门店id（必传）
     */
    private String ognid;
    /**
     * 菜品id
     */
    private Integer dishesid;
    /**
     * 菜品所属菜谱id
     */
    private Integer menuid;
    /**
     * 菜品单位id
     */
    private Integer dnid;
    /**
     * 菜品优惠后单价
     */
    private Double price;
    /**
     * 菜品原单价
     */
    private Double pricea;
    /**
     * 套餐明细菜品初始价格
     */
    private Double packdetailprice;
    /**
     * 菜品会员价
     */
    private Double memberprice;
    /**
     * 特别是套餐主项辅菜加价金额（明细菜加价合计）（必传）
     */
    private Double rpjiajiaamount;
    /**
     * 菜品数量
     */
    private Double count;
    /**
     * 是否称重菜品：0-不称重 1-称重
     */
    private Integer isnoweighing;
    /**
     * 称重数量
     */
    private Double czcount;
    /**
     * 菜品计价重量
     */
    private Double valuationcount;
    /**
     * 状态：等叫，启菜
     */
    private Integer detailstatus;
    /**
     * 更改状态时间（格式：yyyy-MM-dd HH:mm）
     */
    private String detailstatustime;
    /**
     * 原帐单id
     */
    private String sourcebillid;
    /**
     * 原桌台号
     */
    private String sourcetableno;
    /**
     * 菜品备注
     */
    private String remark;
    /**
     * 点菜员id
     */
    private Integer useepid;
    /**
     * 操作员id
     */
    private Integer waiterepid;
    /**
     * 点菜时间（格式：yyyy-MM-dd HH:mm）
     */
    private String sendtime;
    /**
     * 赠送标识：0-不赠送 1-赠送
     */
    private Integer freeflag;
    /**
     * 是否已执行单菜品折扣：0-未执行 1-执行
     */
    private Integer dandiscountflag;
    /**
     * 折扣比
     */
    private Double discount;
    /**
     * 发送标识
     */
    private Integer sendflag;
    /**
     * 比例服务费标识：0-不计算 1-计算
     */
    private Integer serviceflag;
    /**
     * 最低消费标识：0-不计算 1-计算
     */
    private Integer lowestflag;
    /**
     * 是否是临时菜：0-不是 1-是
     */
    private Integer changnameflag;
    /**
     * 套餐标识：0-单菜品 1-套餐
     */
    private Integer packageflag;
    /**
     * 套餐主菜帐单明细id
     */
    private String packageitemid;
    /**
     * 套餐主菜和辅菜标识：
     * 0-非套餐主辅菜
     * 1-套餐主菜
     * 2-套餐辅菜
     */
    private Integer packagezfflag;
    /**
     * 点菜员提成
     */
    private Double orderprice;
    /**
     * 传菜员提成
     */
    private Double deliveryprice;
    /**
     * 配送员提成
     */
    private Double takeawayprice;
    /**
     * 厨师提成
     */
    private Double chefprice;
    /**
     * 配菜员提成
     */
    private Double cookercommission;
    /**
     * 厨师id
     */
    private Integer chefepid;
    /**
     * 做法id（多个做法逗号间隔）
     */
    private String practiceid;
    /**
     * 做法加价类型：0-按数量 1-按重量
     */
    private Integer practicetype;
    /**
     * 菜品加价金额
     */
    private Double practiceamount;
    /**
     * 菜品所属二级id
     */
    private Integer rcid;
    /**
     * 菜品所属出品部门id
     */
    private Integer cdid;
    /**
     * 点菜序号
     */
    private Integer serialnumber;
    /**
     * 辅菜分组id
     */
    private Integer rpdid;
    /**
     * 点菜设备编号
     */
    private String deviceno;
    /**
     * 价格可修改标识：0-不可修改 1-可修改
     */
    private Integer changpriceflag;
    /**
     * 菜品制作时长（分钟）
     */
    private Integer lengthtime;
    /**
     * 菜品成本
     */
    private Double ratedcost;
    /**
     * 是否外卖：0-否 1-是
     */
    private Integer isnotakeaway;
    /**
     * 包装费
     */
    private Double packing;
    /**
     * 是否是特价菜品：0-否 1-是
     */
    private Integer specialOffer;
    /**
     * 买赠菜品id
     */
    private Integer byDishesId;
    /**
     * 买赠菜品编号
     */
    private String byDishesNo;
    /**
     * 买赠菜品标识：0-买 1-赠
     */
    private Integer byfreeflag;
    /**
     * 满减标识：0-不参加 1-参加
     */
    private Integer fullcutflag;
    /**
     * 优惠前金额
     */
    private Double receivableprice;
    /**
     * 优惠后金额
     */
    private Double freeafterprice;
    /**
     * 满减均摊金额
     */
    private Double fullcutprice;
    /**
     * 非实收的支付及抹零优惠（OMP弃用）
     */
    private Double norealprice;
    /**
     * 菜品实收金额（OMP弃用）
     */
    private Double realprice;
    /**
     * 套餐平账字段
     */
    private Double weightprice;
    /**
     * 套餐原价平账字段
     */
    private Double weightpricea;
    /**
     * 套餐平账字段-折后价
     */
    private Double weightpricediscount;
    /**
     * 是否移动pos划菜：0-否 1-是
     */
    private Integer ismobileposserve;
    /**
     * 原菜谱价格
     */
    private Double originalprice;
    /**
     * 改价授权人id
     */
    private Integer acceptuser;
    /**
     * 菜品名称
     */
    private String dishesname;
    /**
     * 菜品编号
     */
    private String dishesno;
    /**
     * 是否已被退：0-否 1-退
     */
    private Integer backflag;
    /**
     * 退菜菜品原菜品明细id
     */
    private String involveid;
    /**
     * 是否餐标菜：0-否 1-是 2-餐标明细
     */
    private Integer mealmark;
    /**
     * 是否微生活活动券引起单品折扣：0-否 1-是
     */
    private Integer expandflag;
    /**
     * 营销方案id
     */
    private Integer programid;
    /**
     * 单品折扣理由
     */
    private String dandiscountreason;
    /**
     * 折扣方案id
     */
    private Integer dsid;
    /**
     * 是否执行过在买优惠：
     * 0-没有执行
     * 1-执行了再买优惠的原菜品
     * 2-执行了再买优惠的赠送菜品
     */
    private Integer buyagainflag;
    /**
     * 再买优惠折扣
     */
    private Double buyagaindiscount;
    /**
     * 明细批次
     */
    private String itembatch;
    /**
     * 排序
     */
    private Integer itemorder;
    /**
     * 门店显示类id
     */
    private Integer storecategoryid;
    /**
     * 是否存在配料：0-不存在 1-存在
     */
    private Integer toppingflag;
    /**
     * 配料原价额
     */
    private Double toppingpricea;
    /**
     * 门票标识：0-否 1-是
     */
    private Integer istick;
    /**
     * 菜品原始名称
     */
    private String originaldishesname;
    /**
     * 整单备注
     */
    private String wholenote;
    /**
     * 拼菜方案id
     */
    private String spellfooddetailedid;
    /**
     * 小程序优惠标识：0-否 1-是
     */
    private Integer appoffersflag;
    /**
     * 小程序优惠金额均摊
     */
    private Double appoffersprice;
    /**
     * 收银账单号（必传）
     */
    private String billno;

    /**
     * 配料金额（注意：套餐主项需要存明细菜配料金额之和）（必传）
     */
    private Integer tpamount;
    /**
     * 门店营业日（必传）
     */
    private String paydate;


    @Override
    public Object clone() throws CloneNotSupportedException {
        DemoBillDetailedDTO cloned = (DemoBillDetailedDTO) super.clone();
        return super.clone();
    }
}
