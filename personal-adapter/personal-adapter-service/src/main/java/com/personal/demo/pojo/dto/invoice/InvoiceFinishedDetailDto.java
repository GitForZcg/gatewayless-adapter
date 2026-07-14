package com.personal.demo.pojo.dto.invoice;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/9/12 16:09
 */

@Data
public class InvoiceFinishedDetailDto {

    /**
     * 1、记录ID
     */
    private Integer id;

    /**
     * 2、店铺ID
     */
    private String sid;

    /**
     * 3、店铺名称（默认为"总部"）
     */
    private String shopName = "总部";

    /**
     * 4、餐饮类型
     */
    private Integer dinType;

    /**
     * 5、发票号码
     */
    private String invoiceNum;

    /**
     * 6、订单号列表
     */
    private List<String> orderNumList;

    /**
     * 7、开票金额
     */
    private Double invoiceAmount;

    /**
     * 8、总金额
     */
    private Double totalAmount;

    /**
     * 9、发票类型（使用 InvoiceTypeSerialize 序列化）
     */
    private String orderType;

    /**
     * 10、发票抬头
     */
    private String invoiceTitle;

    /**
     * 11、销方税号
     */
    private String sellerNum;

    /**
     * 12、销方名称
     */
    private String sellerName;

    /**
     * 13、公司地址
     */
    private String companyAddress;

    /**
     * 14、公司电话
     */
    private String companyPhone;

    /**
     * 15、开户行
     */
    private String bankName;

    /**
     * 16、银行账号
     */
    private String bankNum;

    /**
     * 17、电子邮箱
     */
    private String email;

    /**
     * 18、手机号
     */
    private String phone;

    /**
     * 19、购方税号
     */
    private String buyerTax;

    /**
     * 20、微生活手机号
     */
    private String wshPhone;

    /**
     * 21、微生活会员卡号
     */
    private String wshCardNumber;

    /**
     * 22、开票时间
     */
    private String invoiceTime;

    /**
     * 23、场景通知
     */
    private String sceneNotify;

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
     * 发票类型  01 企业 03 个人
     */
    private String buyerType;
}
