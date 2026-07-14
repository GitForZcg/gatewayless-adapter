package com.personal.demo.pojo.dto;

import com.personal.demo.pojo.base.BaseSM2PublicParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 接口返回对象
 * @Author: fxs
 * @Date: 2026/1/12 16:15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> implements BaseSM2PublicParam {
    /**
     * 错误码
     */
    private Integer errcode;

    /**
     * 错误信息
     */
    private String errmsg;

    /**
     * 业务对象
     */
    private T res;
}
