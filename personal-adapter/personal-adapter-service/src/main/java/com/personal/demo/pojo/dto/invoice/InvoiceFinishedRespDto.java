package com.personal.demo.pojo.dto.invoice;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/9/12 15:59
 */
@Data
@Accessors(chain = true)
public class InvoiceFinishedRespDto {

    private int total;

    private List<InvoiceFinishedDetailDto> list;
}
