package com.personal.demo.request.order;

import com.personal.demo.request.group.ValidationGroups;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author sulu
 * @date 2025年08月07日 10:31 AM
 */
@Data
@Accessors(chain = true)
public class PushOrderParams {

    @NotEmpty(message = "订单号不能为空", groups = ValidationGroups.orderGroup.class)
    private String orderNo;

    @NotEmpty(message = "会员code不能为空", groups = ValidationGroups.orderGroup.class)
    private String memberCode;
}
