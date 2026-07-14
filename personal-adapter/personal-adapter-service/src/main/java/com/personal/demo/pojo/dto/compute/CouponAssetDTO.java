package com.personal.demo.pojo.dto.compute;

import com.personal.demo.pojo.base.DemoComputeMd5Param;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author sulu
 * @date 2026年03月03日 10:46 AM
 */
@Data
public class CouponAssetDTO  implements DemoComputeMd5Param, Serializable {
    /**
     * 券ID列表
     */
    private List<String> coupon_id;
    
    /**
     * 券模板ID
     */
    private String template_id;

    /**
     * 券类型
     */
    private Integer type;

    /**
     * 券面额(单位:分)
     */
    private Integer money;

    /**
     * 券标题
     */
    private String title;

    /**
     * 适用商品ID列表
     */
    private List<String> products;

    /**
     * 适用门店ID列表
     */
    private List<String> sids;

    /**
     * 券消费类型
     */
    private Integer cetype;

    /**
     * 是否自定义面额
     */
    private Boolean is_diy_deno;

    /**
     * 扩展ID
     */
    private String expand_id;

    /**
     * 失效时间
     */
    private String failureTime;

    /**
     * 售价列表,key为券ID,value为售价(单位:分)
     */
    private Map<String, Integer> sale_money_list;

    /**
     * 混用类型:0可混用,1不可混用,2黑名单,3白名单
     */
    private Integer mix_use_value;

    /**
     * 不可混用的券ID列表
     */
    private List<String> limit_mix_coupon;

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
