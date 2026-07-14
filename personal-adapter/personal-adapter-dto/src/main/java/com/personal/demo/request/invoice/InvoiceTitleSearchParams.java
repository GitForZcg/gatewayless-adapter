package com.personal.demo.request.invoice;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/9/12 16:50
 */
@Data
@Accessors(chain = true)
public class InvoiceTitleSearchParams {

    /**
     * 2、门店uuid（必填）
     */
    private String storeCode;

    /**
     * 3、销方名称（必填）
     */
    private String name;
}
