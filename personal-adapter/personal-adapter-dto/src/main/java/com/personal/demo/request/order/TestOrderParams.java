package com.personal.demo.request.order;

import com.personal.demo.request.group.ValidationGroups;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TestOrderParams {



    @NotEmpty(message = "订单id不能为空", groups = ValidationGroups.orderGroup.class)
    private String orderId;


    @NotEmpty(message = "金额不能为空", groups = ValidationGroups.orderGroup.class)
    private String amount;
}
