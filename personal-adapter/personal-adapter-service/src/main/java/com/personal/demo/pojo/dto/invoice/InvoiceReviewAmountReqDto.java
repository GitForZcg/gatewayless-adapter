package com.personal.demo.pojo.dto.invoice;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/9/15 18:08
 */
@Data
@Accessors(chain = true)
public class InvoiceReviewAmountReqDto {

    /**
     * 1、商户ID（必填）
     */
    private String mid;

    /**
     * 2、门店ID（非必填）
     */
    private String sid;

    /**
     * 4、多个订单号（非必填）
     */
    private List<String> orderNumList;

    /**
     * 5、场景（非必填）
     */
    private String sceneNotify = "3";

    /**
     * 7、是否新版开票（非必填，默认值：0）
     */
    private Integer invoiceFlag = 0;

}
