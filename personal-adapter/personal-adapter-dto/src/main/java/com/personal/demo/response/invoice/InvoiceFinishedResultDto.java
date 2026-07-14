package com.personal.demo.response.invoice;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/9/17 11:24
 */
@Data
@Accessors(chain = true)
public class InvoiceFinishedResultDto {

    private List<InvoiceFinishedResult> list;

    private int totalSize;
}
