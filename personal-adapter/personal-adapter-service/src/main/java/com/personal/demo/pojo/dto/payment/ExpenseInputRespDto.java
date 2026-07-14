package com.personal.demo.pojo.dto.payment;

import lombok.Data;

import java.util.List;

@Data
public class ExpenseInputRespDto {

    /**
     * 费用内码，全局唯一
     */
    private String code;

    /**
     * 费用类型编码
     */
    private String expenseTypeBizCode;


    /**
     * 费用类型名称
     */
    private String expenseTypeName;


    /**
     * 消费金额（消费币种）
     */
    private AmountInputRespDto consumeAmount;

    /**
     * 消费本币金额（公司抬头本币币种）
     */
    private AmountInputRespDto consumeBaseAmount;

    /**
     * 	审批通过本币金额（公司抬头本币币种，修改后金额）
     */
    private AmountInputRespDto approvedBaseAmount;

    /**
     * 发票
     */
    private List<InvoiceInputRespDto> invoiceList;

    /**
     * 发票状态
     */
    private Integer invoiceStatus;

    /**
     * 发票到票时间
     */
    private Long invoiceSubmitTime;


    /**
     * 发票预计到票时间
     */
    private Long invoiceExpectSubmitTime;

    /**
     * 是否对公费用
     */
    private Boolean corpExpense;


    /**
     * 核销记录（当前预付未到票场景的“到票记录”）
     */
    private  List<ReceiptDeductionInputRespDto> receiptedDeductionList;

}
