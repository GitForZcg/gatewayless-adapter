package com.personal.demo.pojo.dto.payment;

import lombok.Data;

import java.util.List;

@Data
public class PaymentClassificationReqDto {

    /**
     *需要导入的对应员工的工号
     */
    private String employeeId;

    /**
     *费用列表
     */
    private List<ExpenseReqDto> expenseList;
}
