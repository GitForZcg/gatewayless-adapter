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
public class InvoiceFinishedReqDto {

    /**
     * 1、会员卡号（非必填）
     */
    private String memberNo;

    /**
     * 2、会员手机号（非必填）
     */
    private String memberPhone;

    /**
     * 3、商户ID（必填）
     */
    private String mid;

    /**
     * 4、订单号列表（非必填）
     */
    private List<String> orderNumList;

    /**
     * 5、页码（非必填）
     */
    private Integer page;

    /**
     * 6、每页数量（非必填）
     */
    private Integer size;
}
