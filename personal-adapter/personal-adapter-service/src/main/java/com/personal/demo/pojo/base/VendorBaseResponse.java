package com.personal.demo.pojo.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/10/9 10:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendorBaseResponse <T> implements Serializable {

    private String code;

    private String message;

    private String errorCode;

    private Boolean success;

    private T data;

}
