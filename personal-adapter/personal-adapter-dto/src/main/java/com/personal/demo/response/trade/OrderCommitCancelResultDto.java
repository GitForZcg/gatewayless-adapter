package com.personal.demo.response.trade;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class OrderCommitCancelResultDto {
    /**
     * 交易id
     */
    private String result;

}
