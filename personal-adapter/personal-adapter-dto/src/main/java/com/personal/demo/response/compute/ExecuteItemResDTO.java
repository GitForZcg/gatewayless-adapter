package com.personal.demo.response.compute;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author sulu
 * @date 2026年02月10日 3:53 PM
 */
@Data
public class ExecuteItemResDTO implements Serializable {
    /**
     * 菜品ID
     */
    private String dishesId;

    /**
     * 菜品编号
     */
    private String dishesNo;

    /**
     * 价格类型:1-原价 2-会员价 3-特价 4-加价购 5-赠品 6-买赠
     */
    private String mathFlag;

    /**
     * 规格ID
     */
    private Long sizeId;

    /**
     * 规格编号：测试环境固定使用010  正式环境是001
     */
    private String sizeNo;

    /**
     * 规格编码
     */
    private String dnno;

    /**
     * 单价（单位：分）
     */
    private Integer price;

    /**
     * 子项（配料）加价（单位：分）
     */
    private Integer addPrice;

    /**
     * 餐盒费（单位：分）
     */
    private Integer boxPrice;

    /**
     * 数量
     */
    private Integer count;

    /**
     * 批次号
     */
    private Integer batchNum;

    /**
     * 分组标识
     */
    private String groupFlag;

    /**
     * 菜品数量限制
     */
    private Object maxCount;

    /**
     * 规则列表
     */
    private List<Object> ruleList;

    /**
     * 折扣前价格
     */
    private Integer discountBeforePrice;

    /**
     * 唯一键
     */
    private String uniqueKey;

    /**
     * 理触发菜品的引用活动规则ID
     */
    private String quoteRuleId;

    /**
     * 规则ID
     */
    private String ruleId;

    /**
     * 加料列表
     */
    private List<Object> toppings;

    /**
     * 做法列表
     */
    private List<Object> practices;

    /**
     * 做法列表
     */
    private List<Object> subItems;

    /**
     * 菜品标记，"0"表示普通菜品
     */
    private String dishFlag;

    /**
     * 类别ID
     */
    private Integer categoryId;

    /**
     * 类别编码
     */
    private String categoryNo;

    /**
     * 是否是称重菜品(已废弃)
     */
    private Boolean weighing;

    /**
     * 称重菜品重量
     */
    private Object weight;

    /**
     * 单价
     */
    private Integer unitPrice;

    /**
     * 称重菜会员单价（单位：分）
     */
    private Integer memberUnitPrice;

    /**
     * 菜品类型：0普通菜 1称重菜 2不定价套餐
     */
    private Integer dishesType;

    /**
     * 特价单价价格（单位：分）
     */
    private Integer specialUnitPrice;

    /**
     * 折扣率
     */
    private String discountRate;

    /**
     * 非称重但有小数值
     */
    private Integer notWeightButHavaDecimal;

    /**
     * 所有SKU键列表
     */
    private List<String> allSkuKeys;
    /**
     * SKU键
     */
    private String skuKey;
}
