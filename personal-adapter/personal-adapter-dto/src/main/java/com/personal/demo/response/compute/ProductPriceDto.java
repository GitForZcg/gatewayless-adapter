package com.personal.demo.response.compute;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author sulu
 * @date 2026年02月24日 6:11 PM
 */
@Data
public class ProductPriceDto implements Serializable {

    /**
     * sku编码
     */
    private String skuCode;

    /**
     * 产品售价（含规格）
     */
    private BigDecimal price;

    /**
     * 产品名称
     */
    private String productName;


    /**
     * 产品划线价/原价
     */
    private BigDecimal crossedPrice;

    /**
     * 最终售价
     */
    private BigDecimal salePrice;

    /**
     * 产品code
     */
    @NotBlank(message = "productCode码不能为空")
    private String productCode;
    /**
     * 产品购买数量
     */
    @NotNull(message = "当前订单购买的sku数量不能为空")
    private Integer skuNum;

    /**
     * 产品类型（枚举）
     */
    private Integer productType;

    /**
     * 唯一标识
     */
    private String groupFlag;

    /**
     * 规格加价
     */
    private BigDecimal addPrice;







}
