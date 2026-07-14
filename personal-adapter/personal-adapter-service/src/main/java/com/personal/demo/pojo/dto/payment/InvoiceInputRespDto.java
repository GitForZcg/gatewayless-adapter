package com.personal.demo.pojo.dto.payment;

import lombok.Data;

@Data
public class InvoiceInputRespDto {

    /**
     * 发票编码，唯一标识
     */
    private String code;

    /**
     * 发票链接
     */
    private String invoiceUrl;


    /**
     * 发票文件名称
     */
    private String invoiceFileName;


    /**
     * 验真状态，未验证 NOT_VALIDATION, 已验证 VALIDATED, 验证失败 VALIDATION_FAILED
     */
    private String validateStatusDesc;


    /**
     * 发票类型，不同发票类型识票信息不同
     */
    private String invoiceType;


    /**
     * 税率
     */
    private String taxRate;

    /**
     * 核准税额
     */
    private AmountInputRespDto approvedTaxAmount;

    /**
     * 核准抵扣税额
     */
    private AmountInputRespDto approvedDeductionAmount;

    /**
     *识票信息
     */
    private InvoiceInfoRespDto invoiceInfo;
}
