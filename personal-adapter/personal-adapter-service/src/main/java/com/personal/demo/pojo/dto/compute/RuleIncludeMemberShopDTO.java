package com.personal.demo.pojo.dto.compute;

import com.personal.demo.pojo.base.DemoComputeMd5Param;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * 特价权益卡列表
 *
 * @author sulu
 * @date 2026年01月23日 2:55 PM
 */
@Data
public class RuleIncludeMemberShopDTO implements DemoComputeMd5Param, Serializable {
    /**
     * 店铺ID
     */
    private String shopId;
    /**
     * 店铺名称
     */
    private String shopName;
    /**
     * 会员价（单位：分）
     */
    private Integer memberPrice;

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
