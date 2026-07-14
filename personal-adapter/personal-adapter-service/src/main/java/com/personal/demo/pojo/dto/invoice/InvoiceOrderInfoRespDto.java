package com.personal.demo.pojo.dto.invoice;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/9/12 14:18
 */

@Data
@Accessors(chain = true)
public class InvoiceOrderInfoRespDto {
    /**
     * 门店名称
     */
    private String shopName;
    /**
     *
     */
    private String dinType;
    /**
     * 账单号
     */
    private String billNum;
    /**
     * 销方税号
     */
    private String sellerNum;
    /**
     * 销方名称
     */
    private String sellerName;
    /**
     * 订单时间
     */
    private String orderTime;
    /**
     * 订单实收金额
     */
    private BigDecimal orderAmount;

    private String billAmount;
    /**
     * 菜品图片URL列表
     */
    private String dishesUrl;
    /**
     * 数量
     */
    private Integer count;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 门店UUID
     */
    private String shopId;
    /**
     * 品牌UUID
     */
    private String brandId;
    /**
     * 发票状态
     */
    private String invoiceStatus;
}
