package com.personal.demo.pojo.dto.response.product;

import lombok.Data;

/**
 * 配料(子规格)信息
 *
 * @Author: fxs
 * @Date: 2025/8/11 15:43
 */
@Data
public class TpResponse {

    /**
     *  配料代码
     */
    private String tpCode;

    /**
     * 配料价格
     */
    private String tpPrice;

    /**
     *是否默认
     */
    private String isDefault;
}
