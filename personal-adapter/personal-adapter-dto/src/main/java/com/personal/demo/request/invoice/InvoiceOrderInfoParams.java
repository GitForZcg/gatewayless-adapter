package com.personal.demo.request.invoice;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/9/12 14:18
 */

@Data
@Accessors(chain = true)
public class InvoiceOrderInfoParams {

    /**
     * 会员卡号（非必填）
     */
    private String memberPhone;

    private String memberNo;

    private Integer pageSize;

    private Integer current;

}
