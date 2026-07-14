package com.personal.demo.response.payment;

import lombok.Data;

@Data
public class ErrorPayOrderDataResultDto {
    /**
     * 异常级别
     */
    private String level;

    /**
     * 触发校验类型
     */
    private String targetClassify;


    /**
     * 触发校验的那条数据的Code主键，校验类型为FORM时，该字段为空
     */
    private String targetKey;


    /**
     * 校验触发的类型
     */
    private String ruleClassify;


    /**
     * 校验信息
     */
    private String message;

    /**
     * 是否需要理由
     */
    private Boolean needReason;
}
