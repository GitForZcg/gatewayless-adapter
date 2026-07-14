package com.personal.demo.request.invoice;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/9/15 09:43
 */
@Data
@Accessors(chain = true)
public class InvoicePushEmailParams {

    /**
     * 2、门店ID（必填）
     */
    private String storeCode;

    /**
     * 销方税号（必填）
     */
    private String sellerNum;

    /**
     * 7、推送目标邮箱地址（必填）
     */
    private String pushEmail;

    /**
     * 发票号码（非必填，纸电票必填）
     */
    private String invoiceNo;

    private String invoiceCode;

}
