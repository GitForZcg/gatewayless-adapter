package com.personal.demo.pojo.dto.payment;

import lombok.Data;

@Data
public class CustomObjectReqDto {

    /**
     * 结算方式
     */
    private String CF2;

    /**
     * 文件url
     */
    private FileReqDto CF4;
    
    /**
     * 门店编码
     */
    private String CF31;

}
