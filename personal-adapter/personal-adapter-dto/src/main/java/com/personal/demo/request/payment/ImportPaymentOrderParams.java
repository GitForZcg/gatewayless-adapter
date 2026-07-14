package com.personal.demo.request.payment;


import com.personal.demo.dto.payment.GoodsDetailFileDto;
import com.personal.demo.dto.payment.ImportPaymentClassificationDto;
import com.personal.demo.request.group.ValidationGroups;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * @author czs
 */
@Data
public class ImportPaymentOrderParams {

    /**
     * 付款单号
     */
    @NotEmpty(message = "付款单号不能为空", groups = ValidationGroups.paymentGroup.class)
    private String paymentOrderNo;

    /**
     * 门店编码
     */
    @NotEmpty(message = "门店编码不能为空", groups = ValidationGroups.paymentGroup.class)
    private String storeCode;

    /**
     * 门店名称
     */
    @NotEmpty(message = "门店名称不能为空", groups = ValidationGroups.paymentGroup.class)
    private String storeName;

    /**
     * 结转方式
     */
    @NotEmpty(message = "结转方式不能为空", groups = ValidationGroups.paymentGroup.class)
    private String settlementMethod;

    /**
     * 付款单员工工号
     */
    @NotEmpty(message = "付款单员工工号不能为空", groups = ValidationGroups.paymentGroup.class)
    private String payOrderEmployeeNo;

    /**
     * 公司抬头
     */
    @NotEmpty(message = "公司抬头不能为空", groups = ValidationGroups.paymentGroup.class)
    private String businessHeader;


    /**
     * 收款方账户
     */
    @NotEmpty(message = "收款方账户不能为空", groups = ValidationGroups.paymentGroup.class)
    private String payeeAccount;

    /**
     * 收款方银行账号
     */
    @NotEmpty(message = "收款方银行账号不能为空", groups = ValidationGroups.paymentGroup.class)
    private String payeeBankAccountNumber;


    /**
     * 供应商费用的内码Code列表
     */
    @NotEmpty(message = "供应商费用的内码Code列表", groups = ValidationGroups.paymentGroup.class)
    private List<String> expenseCodes;

    /**
     * 财务分类明细
     */
    private List<ImportPaymentClassificationDto> everyPaymentClassifications;

    /**
     * 先款后货的应付货物明细
     */
    @Valid
    @NotNull(message = "应付货物明细不能为空", groups = ValidationGroups.paymentGroup.class)
    private GoodsDetailFileDto fileDto;

     /**
     * 结算期间月份（yyyyMM格式）
     */
    @NotEmpty(message = "结算期月份不能为空", groups = ValidationGroups.paymentGroup.class)
    private String settlementPeriodMonth;

}
