package com.personal.demo.pojo.dto.invoice;

import lombok.Data;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/9/12 16:55
 */
@Data
public class InvoiceCompanyInfoRespDto {

    /**
     * 1、抬头
     */
    private String name;

    /**
     * 2、税号
     */
    private String taxId;

    /**
     * 3、开户行
     */
    private String bank;

    /**
     * 4、开户行账号
     */
    private String bankAccount;

    /**
     * 5、企业注册地址
     */
    private String location;

    /**
     * 6、企业注册电话
     */
    private String mobilePhone;

    /**
     * 7、城市
     */
    private String city;

    /**
     * 8、区
     */
    private String county;

    /**
     * 9、省
     */
    private String province;

    /**
     * 10、公司索引
     */
    private String companyIndex;

    /**
     * 11、税务机关代码
     */
    private String taxAuthorityCode;

    /**
     * 12、税务机关名称
     */
    private String taxAuthorityName;

    /**
     * 13、频次
     */
    private Integer frequency;

    /**
     * 14、评分
     */
    private String score;
}
