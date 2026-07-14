package com.personal.demo.pojo.dto.compute;

import com.personal.demo.pojo.base.DemoComputeMd5Param;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * 菜品资产互斥列表
 * @author sulu
 * @date 2026年01月23日 3:41 PM
 */

@Data
public class DishesCouponExcludeDTO implements DemoComputeMd5Param, Serializable {
    /**
     * 菜品ID
     */
    private String dishesId;
    /**
     * 菜品名称
     */
    private String dishesName;
    /**
     * 排除非抵扣项
     */
    private Integer excludeNonDeduct;

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
