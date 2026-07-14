package com.personal.demo.pojo.dto.payment;

import lombok.Data;

@Data
public class PayeeAccountReqDto {

    /**
     * 账户名
     */
    private String bankAcctName;

    /**
     * 银行账号（支付类型为银行账户时必填,支付类型为支付宝时必填，为用户手机号）
     */
    private String bankAcctNumber;

    /**
     * 账户类型;ALIPAY-支付宝,BANK-银行账户,CASH-现金
     */
    private String paymentType;


    /**
     * 收款账户类型
     */
    private String accountType;

}
