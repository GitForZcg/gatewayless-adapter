package com.personal.demo.response.invoice;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/9/16 14:42
 */
@Data
@Accessors(chain = true)
public class InvoiceTitleDetailResultDto {

    /**
     * 银行账号
     */
    private String bankAccount;
    /**
     * 开户行名称
     */
    private String bankName;
    /**
     * 公司地址
     */
    private String companyAddress;
    /**
     * 纳税识别号
     */
    private String taxNumber;
    /**
     * 电话
     */
    private String telephone;
    /**
     * 抬头
     */
    private String title;
    /**
     * 类型
     */
    private String type;
}
