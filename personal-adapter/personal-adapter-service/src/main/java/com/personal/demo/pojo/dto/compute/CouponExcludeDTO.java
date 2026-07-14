package com.personal.demo.pojo.dto.compute;

import com.personal.demo.pojo.base.DemoComputeMd5Param;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * 排除规则列表
 * @author sulu
 * @date 2026年01月23日 3:01 PM
 */
@Data
public class CouponExcludeDTO implements DemoComputeMd5Param, Serializable {
    /**
     * 主键ID
     */
    private String pkId;
    /**
     * 规则名称
     */
    private String name;
    /**
     * 资产组类型
     */
    private Integer assetGroupType;
    /**
     * 排除类型
     */
    private Integer exclusionType;
    /**
     * 排除非抵扣项
     */
    private Integer excludeNonDeduct;
    /**
     * 子规则列表
     */
    private List<ExcludeRuleItemDTO> rules;

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
