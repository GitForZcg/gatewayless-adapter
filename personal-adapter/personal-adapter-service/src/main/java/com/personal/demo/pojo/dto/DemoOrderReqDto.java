package com.personal.demo.pojo.dto;

import com.personal.demo.pojo.base.DemoOrderMd5Param;
import com.google.common.collect.ImmutableSet;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Set;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/7/24 10:28
 */
@Setter
@Getter
@Accessors(chain = true)
public class DemoOrderReqDto implements DemoOrderMd5Param {




    private String aceOrderId;

    private String aceOrderAmount;




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
        return createOrderId(this.aceOrderId);
    }
}
