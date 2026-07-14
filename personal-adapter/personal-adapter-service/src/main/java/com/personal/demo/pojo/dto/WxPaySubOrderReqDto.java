package com.personal.demo.pojo.dto;

import com.personal.demo.request.group.ValidationGroups;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.Accessors;


/**
 * 订单请求体
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class WxPaySubOrderReqDto {

    private String subMerId;

    @NotEmpty(message = "subOrderId为空", groups = ValidationGroups.externalDemoProductGroup.class)
    private String subOrderId;

}
