package com.personal.demo.pojo.dto.response.product;

import lombok.Data;

/**
 * 门店子配料(子规格)信息返回
 *
 * @Author: fxs
 * @Date: 2025/8/11 16:30
 */
@Data
public class PluSubSpecResponse {

    /** 子规格代码 */
    private String code;

    /** 子规格描述（原文） */
    private String descript;

    /** 子规格描述（中文） */
    private String descriptZh;

    /** 子规格价格 */
    private String price;

    /** 税码 */
    private String taxCode;

    /** 销售项目 */
    private String salesItem;
}
