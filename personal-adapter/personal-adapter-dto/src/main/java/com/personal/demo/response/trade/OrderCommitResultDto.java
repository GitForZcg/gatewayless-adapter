package com.personal.demo.response.trade;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class OrderCommitResultDto {

    private String errcode;
    private String errmsg;
    private OrderCommitResDto res;
    
}
