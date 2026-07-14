package com.personal.demo.request.invoice;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/9/12 13:10
 */
@Data
@Accessors(chain = true)
public class InvoiceCancelReasonParam {

    private String invoiceNo;
}
