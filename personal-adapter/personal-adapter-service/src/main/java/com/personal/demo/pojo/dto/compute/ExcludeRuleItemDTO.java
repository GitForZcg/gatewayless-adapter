package com.personal.demo.pojo.dto.compute;

import com.personal.demo.pojo.base.DemoComputeMd5Param;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * 子规则列表
 * @author sulu
 * @date 2026年01月23日 3:39 PM
 */
@Data
public class ExcludeRuleItemDTO implements DemoComputeMd5Param, Serializable {

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
