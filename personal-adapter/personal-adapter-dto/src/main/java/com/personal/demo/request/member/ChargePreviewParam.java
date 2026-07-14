package com.personal.demo.request.member;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 储值提交请求DTO
 * @author fxs
 * @date 2025/01/13
 */
@Data
public class ChargePreviewParam {

    /** 用户卡号 */
    private String cno;

    /** 门店id */
    private Integer shopId;

    /** 收银员id；-1=API默认 */
    private Integer cashierId;

    /** 储值实收金额（分） */
    private BigDecimal money;

    /** 储值赠送金额（分） */
    private BigDecimal rewardMoney;

    /** 储值支付方式 1现金 2银行卡 3微信 4支付宝 5微信店内 99混合 */
    private Integer chargeType;

    /** 储值备注 */
    private String remark;

    /** 是否自定义金额储值 */
    private Boolean isDiy;

    /** 推荐人员工工号 */
    private String recommendCode;

    /** 储值业务号（32位） */
    private String bizId;

    /** 混合支付详情JSON */
    private String chargeTypeDetail;

    /** 储值规则id */
    private String ruleId;

    /** 自定义有效期；-1永久，>0 n天后到期 */
    private Integer expiryDay;

    /** 是否使用在线储值规则 */
    private Boolean isOnline;

    /** 储值倍数 */
    private Integer multiple;

    /** 储值业务门店；shop_id=999999999时必填 */
    private Integer sourceShopId;

    /** 次规则 1-20 */
    private Integer times;
}
