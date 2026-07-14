package com.personal.demo.request.invoice;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/9/12 13:06
 */
@Data
@Accessors(chain = true)
public class InvoiceCancelParams {

    /**
     * 门店id
     */
    private String storeCode;
    /**
     * 取消原因
     */
    private List<InvoiceCancelReasonParam> cancelInfo;

}
