package com.personal.demo.pojo.dto.payment;

import lombok.Data;

import java.util.List;

@Data
public class ExpenseReqDto {

    /**
     * 付款金额
     */
    private ConsumeAmountReqDto consumeAmount;

    /**
     *	费用类型业务编码
     */
    private  String expenseTypeBizCode;

    /**
     *	是否对公费用，需要填写true
     */
    private  Boolean corpExpense;

    /**
     *	业务场景
     */
    private  String corpType;

    /**
     *	责任人/业务经办人
     */
    private List<String> corpExpenseResponsibleEmpIds;

    /**
     * 未到票金额
     */
    private ConsumeAmountReqDto nonReceiptAmount;

    /**
     *预计到票时间
     */
    private Long forecastReceiptDate;

    //todo 消费地点


    /**
     *	往来单位业务编码
     */
    private  String tradingPartnerBizCode;

    /**
     *自定义字段
     */
    private CustomObjectReqDto customObject;
}
