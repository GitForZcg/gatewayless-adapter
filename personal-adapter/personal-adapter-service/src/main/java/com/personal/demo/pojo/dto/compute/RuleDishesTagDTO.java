package com.personal.demo.pojo.dto.compute;

import com.personal.demo.pojo.base.DemoComputeMd5Param;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * @author sulu
 * @date 2026年01月23日 2:58 PM
 */
@Data
public class RuleDishesTagDTO implements DemoComputeMd5Param, Serializable {
    /**
     * 标签名称
     */
    private String tagName;
    /**
     * 标签值
     */
    private String tagValue;

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
