package com.personal.demo.pojo.dto.response.product;

import lombok.Data;

/**
 * 品牌级的菜项数据
 *
 * @Author: fxs
 * @Date: 2025/8/11 16:55
 */
@Data
public class PluMasterAllResponse {

    /** 商品代码 */
    private String pluCode;

    /** 菜品描述（中文） */
    private String descript;

    /** 菜品描述（原文） */
    private String descriptZh;

    /** 菜类代码(二级分类-系列) */
    private String sortCode;

    /** 菜类名称（中文） */
    private String sortCodeDescript;

    /** 菜类中文名称（原文） */
    private String sortCodeDescriptZh;

    /** 菜品分类代码(一级分类-产品分类)  */
    private String majorGroup;

    /** 菜品分类名称（中文） */
    private String majorGroupDescript;

    /** 菜品分类名称中文名称（原文） */
    private String majorGroupDescriptZh;

    /** 菜项图片 */
    private String pluPic;
}
