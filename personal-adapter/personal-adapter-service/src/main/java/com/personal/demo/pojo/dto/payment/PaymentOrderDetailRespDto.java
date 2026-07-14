package com.personal.demo.pojo.dto.payment;

import lombok.Data;

import java.util.List;

@Data
public class PaymentOrderDetailRespDto {
    /**
     * 单据内码
     */
    private String formDataCode;

    /**
     * 单据号
     */
    private String formCode;


    /**
     * 单据小类编码
     */
    private String formSubTypeBizCode;


    /**
     * 单据小类名称
     */
    private String formSubTypeName;


    /**
     * 报销事由
     */
    private String reimburseName;


    /**
     * 承担人姓名
     */
    private String coverEmployeeName;


    /**
     * 承担人工号
     */
    private String coverEmployeeId;


    /**
     * 部门业务编码
     */
    private String departmentBizCode;

    /**
     * 公司抬头信息
     */
    private String legalEntityBizCode;


    /**
     * 公司抬头名称
     */
    private String legalEntityName;


    /**
     * 往来单位编码
     */
    private String tradingPartnerBizCode;

    /**
     * 往来单位名称
     */
    private String tradingPartnerName;


    /**
     * 报销金额（收款币种）
     */
    private AmountInputRespDto amount;
    /**
     * 报销本币金额（公司抬头本币币种）
     */
    private AmountInputRespDto baseAmount;


    /**
     * 提单人姓名
     */
    private String reimEmployeeName;


    /**
     * 提单人工号
     */
    private String reimEmployeeId;


    /**
     * 填单人姓名
     */
    private String fillEmployeeName;


    /**
     * 填单人工号
     */
    private String fillEmployeeId;


    /**
     * 单据状态
     */
    private String formStatus;

    /**
     * 应付金额（收款币种）
     */
    private AmountInputRespDto paymentAmount;

    /**
     * 应付本币金额（公司抬头本币币种）
     */
    private AmountInputRespDto paymentBaseAmount;

    /**
     * 支付时间
     */
    private Long settledAt;


    /**
     * 第一次单据创建时间（若单据先暂存，则值为第一次暂存时间）
     */
    private Long createdAt;


    /**
     * 单据税额抵扣本币金额
     */
    private AmountInputRespDto taxDeductBaseAmount;


    /**
     * 单据税额抵扣收款金额
     */
    private AmountInputRespDto taxDeductAcceptAmount;

    /**
     * 提单人部门编码
     */
    private String submitUserDepartmentBizCode;


    /**
     * 提单人部门名称
     */
    private String submitUserDepartmentName;

    /**
     * 自定义字段
     */
    private CustomObjectRespDto customObject;

    /**
     * 费用列表
     */
    private List<ExpenseInputRespDto> expenseList;
}
