package com.personal.demo.response.invoice;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/9/17 10:41
 */
@Data
@Accessors(chain = true)
public class InvoiceFinishedResult {

    /**
     * 2、店铺ID
     */
    private String storeCode;

    /**
     * 3、店铺名称（默认为"总部"）
     */
    private String storeName = "总部";
    /**
     * 5、发票号码
     */
    private String invoiceNum;
    /**
     * 6、订单号列表
     */
    private List<String> ordeNoList;
    /**
     * 7、开票金额
     */
    private Double invoiceAmount;

    /**
     * 8、总金额
     */
    private Double payableAmount;

    /**
     * 9、发票类型（使用 InvoiceTypeSerialize 序列化）
     */
    private String orderType;

    /**
     * 10、发票抬头
     */
    private String title;

    /**
     * 11、销方税号
     */
    private String ownSellerNo;

    /**
     * 12、销方名称
     */
    private String ownSellerName;

    /**
     * 13、公司地址
     */
    private String companyAddress;

    /**
     * 14、公司电话
     */
    private String telephone;

    /**
     * 15、开户行
     */
    private String bankName;

    /**
     * 16、银行账号
     */
    private String bankAccount;
    /**
     * 17、电子邮箱
     */
    private String userEmail;
    /**
     * 18、手机号
     */
    private String phone;

    /**
     * 19、购方税号
     */
    private String buyerTax;
    /**
     * 22、开票时间
     */
    private String invoiceTime;
    /**
     * 24、发票状态
     */
    private String invoiceStatus;

    /**
     * 25、发票编号
     */
    private String invoiceNo;

    /**
     * 26、发票代码
     */
    private String invoiceCode;

    /**
     * 27、是否删除（1：已删除，其他：未删除）
     */
    private Integer isDelete;
    /**
     * 发票类型
     */
    private String type;
}
