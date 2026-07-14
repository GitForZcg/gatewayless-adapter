package com.personal.demo.pojo.dto.payment;

import lombok.Data;

import java.util.List;

@Data
public class PaymentOrderReqDto {
    /**
     *有值时会使用该值作为单据号
     */
    private String formCode;

    /**
     *报销单事由
     */
    private String reimburseName;


    /**
     *表单类型的业务编号
     */
    private String formSubTypeBizCode;


    /**
     *提单人工号
     */
    private String submittedUserEmployeeId;


    /**
     *公司抬头编码
     */
    private String legalEntityBizCode;

    /**
     *承担人工号
     */
    private String coverUserEmployeeId;


    /**
     *承担部门编码
     */
    private String coverDepartmentBizCode;

    /**
     *收款信息
     */
    private PayeeAccountReqDto payeeAccount;

    /**
     * 费用的供应商内码Code列表
     */
    private List<String> expenseCodes;

    /**
     *自定义字段
     */
    private CustomObjectReqDto customObject;

    /**
     *往来单位编码
     */
    private String tradingPartnerBizCode;

    /**
     *不需要校验合同责任人,true-不校验，false-校验（默认校验）
     */
    private Boolean nonCheckContractAgent;

}
