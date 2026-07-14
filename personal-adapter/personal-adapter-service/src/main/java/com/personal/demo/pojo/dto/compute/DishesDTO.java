package com.personal.demo.pojo.dto.compute;

import com.personal.demo.pojo.base.DemoComputeMd5Param;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * @author sulu
 * @date 2026年02月09日 5:30 PM
 */

@Data
public class DishesDTO implements DemoComputeMd5Param, Serializable {

    /**
     * 菜品ID
     */
    private Long dishesId;

    /**
     * 菜品名称
     */
    private String dishesName;

    /**
     * 菜品编号
     */
    private String dishesNo;

    /**
     * 规格编号
     */
    private String sizeNo;

    /**
     * 规格ID
     */
    private Long sizeId;

    /**
     * 规格名称
     */
    private String sizeName;

    /**
     * 商品SKU ID
     */
    private String skuId;

    /**
     * 菜品数量
     */
    private Integer count;

    /**
     * 菜品价格（单位：分）
     */
    private Integer price;

    /**
     * 菜品原价（单位：分）
     */
    private Integer originalPrice;

    /**
     * 菜品分类ID
     */
    private Integer categoryId;

    /**
     * 会员价格（单位：分）
     */
    private Integer memberPrice;

    /**
     * 销售价格（单位：分）
     */
    private Integer salePrice;

    /**
     * 加价金额（单位：分）
     */
    private Integer addPrice;

    /**
     * 分组标识（唯一标识）
     */
    private String groupFlag;

    /**
     * 商品类型（1：普通商品，可根据业务补充其他类型说明）
     */
    private Integer goodsType;

    @Override
    public Set<String> needSign() {
        return null;
    }

    @Override
    public Set<String> needSignParam() {
        return null;
    }

    @Override
    public String orderId() {
        return null;
    }
}
