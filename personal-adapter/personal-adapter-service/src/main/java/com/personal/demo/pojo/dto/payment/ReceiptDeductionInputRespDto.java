package com.personal.demo.pojo.dto.payment;

import lombok.Data;

@Data
public class ReceiptDeductionInputRespDto {

    /**
     * 核销单据code
     */
    private String reimburseCode;

    /**
     * 核销单据内码、主键
     */
    private String reimburseDataCode;

    /**
     * 核销金额
     */
    private AmountInputRespDto deductionAmount;

    /**
     * 核销日期
     */
    private Long deductionDate;

    /**
     * 核销人工号
     */
    private String deductionBy;

    /**
     * 冲销原因
     */
    private String deductReason;
}
