package com.personal.demo.pojo.dto.response.product;

import lombok.Data;

import java.util.List;

/**
 *
 * 门店菜项集合返回
 * @Author: fxs
 * @Date: 2025/8/11 15:48
 */
@Data
public class PluAllResponse {

    /** 菜单类别描述 */
    private String sortCodeDescript;

    /** 菜单类别描述（中文） */
    private String sortCodeDescriptZh;

    /** 菜单类别代码 */
    private String sortCode;

    /** 菜品列表 */
    private List<PluResponse> pluList;
}
