package com.personal.demo.pojo.dto.invoice;

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
public class InvoiceCancelReasonReqDto {

    /**
     * 开票单号
     */
    private String DH;
    /**
     * 原因
     */
    private String reason;
}
