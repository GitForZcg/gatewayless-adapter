package com.personal.demo.pojo.dto.compute;

import com.personal.demo.pojo.base.DemoComputeMd5Param;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * @author sulu
 * @date 2026年01月23日 2:51 PM
 */
@Data
public class RuleFlagDomainDTO implements DemoComputeMd5Param, Serializable {

    /**
     * 标识ID
     */
    private String flagId;
    /**
     * 标识名称
     */
    private String flagName;
    /**
     * 标识值
     */
    private String flagValue;

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
