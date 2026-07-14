package com.personal.demo.response.payment;

import lombok.Data;

import java.util.List;

/**
 * @author czs
 */
@Data
public class PaymentClassificationResultDto {
    /**
     * code
     */
    private String code;

    /**
     * message
     */
    private String message;

    /**
     * success
     */
    private Boolean success;

    /**
     * 财务分类费用失败信息
     */
    private List<ErrorDataResultDto> errorData;

    /**
     * 财务分类费用编码
     */
    private List<SuccessDataResultDto> successData;
}
