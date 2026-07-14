package com.personal.demo.request.pay;

import com.personal.demo.request.group.ValidationGroups;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TestPayParams {


    @NotEmpty(message = "支付id不能为空", groups = ValidationGroups.payGroup.class)
    private String payId;


    @NotEmpty(message = "金额不能为空", groups = ValidationGroups.payGroup.class)
    private String amount;
}
