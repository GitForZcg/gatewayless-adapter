package com.personal.demo.rule.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/7/4 10:49
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DemoException extends RuntimeException {

    private String msg;
    private String code;

    public DemoException(String code, String message) {
        super(message);
        this.msg = message;
        this.code = code;
    }

    public DemoException(String code, String message, Throwable cause) {
        super(message, cause);
        this.msg = message;
        this.code = code;
    }
}
