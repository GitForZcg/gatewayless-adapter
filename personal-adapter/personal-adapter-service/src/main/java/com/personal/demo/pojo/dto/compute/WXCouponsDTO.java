package com.personal.demo.pojo.dto.compute;

import com.personal.demo.pojo.base.DemoComputeMd5Param;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * @author sulu
 * @date 2026年02月09日 6:00 PM
 */

@Data
public class WXCouponsDTO implements DemoComputeMd5Param, Serializable {
    /**
     * 券码
     */
    private String id;

    /**
     * PM标识ID（可根据实际业务补充更精准的含义，如“产品模型ID/项目ID”等）
     */
    private Integer pmid;

    /**
     * 模板ID
     */
    private String templateId;

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
