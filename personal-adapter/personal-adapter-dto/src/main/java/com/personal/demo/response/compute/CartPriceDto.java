package com.personal.demo.response.compute;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author sulu
 * @date 2026年02月24日 6:09 PM
 */
@Data
public class CartPriceDto implements Serializable {
    /**
     * 总计减免金额
     */
    private BigDecimal totalReducedAmount;

    /**
     * 购物车sku总金额
     */
    private BigDecimal totalAmount;
    /**
     * 预计到手金额
     */
    private BigDecimal payableAmount;

    /**
     * 产品列表
     */
    private List<ProductPriceDto> productPriceDtoList;

}
