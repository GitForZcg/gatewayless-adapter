package com.personal.demo.request.product;

import com.personal.demo.request.group.ValidationGroups;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * 通知接口请求参数
 *
 * @Author: fxs
 * @Date: 2025/8/13 11:43
 */
@Data
public class ProductNotificationParam {

    /**
     * 集团代码
     */
    @NotEmpty(message = "集团代码不能为空", groups = ValidationGroups.productGroup.class)
    private String mid;

    /**
     * 品牌代码
     */
    @NotEmpty(message = "品牌代码不能为空", groups = ValidationGroups.productGroup.class)
    private String bid;

    /**
     * 门店代码
     */
    @NotEmpty(message = "门店代码不能为空", groups = ValidationGroups.productGroup.class)
    private String sid;

    /**
     * 批次号
     */
    @NotEmpty(message = "批次号不能为空", groups = ValidationGroups.productGroup.class)
    private String batchNumber;


}
