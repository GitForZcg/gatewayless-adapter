package com.personal.demo.response.invoice;

import lombok.Data;

import java.util.Collections;
import java.util.List;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/9/19 15:01
 */
@Data
public class InvoiceOrderInfoResultDto {

    private List<InvoiceOrderInfoResult> list = Collections.emptyList();
    private int totalSize = 0;
}
