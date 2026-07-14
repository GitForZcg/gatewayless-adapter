package com.personal.demo.pojo.dto.payment;

import lombok.Data;

import java.util.List;

@Data
public class InvoiceInfoRespDto {

    /**
     * 销方名称
     */
    private String supplierName;

    /**
     * 销方地址+电话
     */
    private String supplierAddress;

    /**
     * 销方账户(销方开户行+账户）非增值税发票和未验真的发票：该字段内容为空字符串
     */
    private String supplierAccount;

    /**
     * 不含税金额
     */
    private String totalPriceAmount;

    /**
     * 销方税号
     */
    private String supplierTaxNumber;

    /**
     * 购方名称
     */
    private String buyerName;

    /**
     * 购方税号
     */
    private String buyerTaxNumber;

    /**
     * 发票代码
     */
    private String invoiceCode;


    /**
     * 发票号码
     */
    private String invoiceNumber;

    /**
     * 开票日期
     */
    private String issueDate;

    /**
     * 开票日期格式化
     */
    private String issueDateDesc;


    /**
     * 校验码
     */
    private String checkCode;
    /**
     * 抵扣税额合计
     */
    private List<TaxItemsRespDto> taxItems;


    /**
     * 税率
     */
    private String taxRate;

    /**
     * 价税合计
     */
    private String totalPriceAndTax;

    /**
     * 税额
     */
    private String totalTaxAmount;


}
