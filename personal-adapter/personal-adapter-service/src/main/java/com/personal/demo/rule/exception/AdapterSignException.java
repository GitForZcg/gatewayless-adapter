package com.personal.demo.rule.exception;

import com.personal.demo.pojo.wrapper.BaseResponse;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/7/21 16:35
 */
public class AdapterSignException extends AdapterBaseException {

    public AdapterSignException(BaseResponse response) {
        super(response.code(), response.message());
    }

    public AdapterSignException(String message) {
        super(message);
    }

    public AdapterSignException(String code, String message) {
        super(code, message);
    }
}
