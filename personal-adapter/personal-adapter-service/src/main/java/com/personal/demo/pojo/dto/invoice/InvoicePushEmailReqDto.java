package com.personal.demo.pojo.dto.invoice;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 邮箱推送
 * @date 2025/9/15 09:43
 */
@Data
@Accessors(chain = true)
public class InvoicePushEmailReqDto {

    /**
     * 1、商户ID（必填）
     */
    private String tenancyId;

    /**
     * 2、门店ID（必填）
     */
    private String storeId;

    /**
     * 3、场景值（必填）（不同场景对应不同的开票类型）
     */
    private String sceneNotify;

    /**
     * 4、销方税号（必填）
     */
    private String taxNo;

    /**
     * 5、发票代码（非必填，纸电票必填）
     */
    private String invoiceCode;

    /**
     * 6、发票号码（非必填，纸电票必填）
     */
    private String invoiceNo;

    /**
     * 7、推送目标邮箱地址（必填）
     */
    private String pushEmail;

    /**
     * 8、数电发票号码（非必填，全电票必填）
     */
    private String digitInvoiceNo;
}
