package com.personal.demo.pojo.dto.trade.reponse;

import lombok.Data;

@Data
public class OrderCommitRespDto {

    private String errcode;
    private String errmsg;
    private OrderCommitResData res;
}
