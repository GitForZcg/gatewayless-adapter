package com.personal.demo.rule.exception;

import com.personal.demo.pojo.wrapper.AdapterErrorCode;
import com.personal.demo.pojo.wrapper.BaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/7/21 16:35
 */
@EqualsAndHashCode(callSuper = true)
public class AdapterException extends AdapterBaseException {

    public AdapterException(String code, String message, String type) {
        super(code, message, type);
    }

    public AdapterException(AdapterErrorCode errorCode) {
        super(errorCode.code, errorCode.message, errorCode.type);
    }
}
