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
public class AdapterBaseException extends RuntimeException {

    private String msg;
    private String code;
    private String type;

    public AdapterBaseException(String code, String message) {
        super(message);
        this.msg = message;
        this.code = code;
    }

    public AdapterBaseException(String code, String message, String type) {
        super(message);
        this.msg = message;
        this.code = code;
        this.type = type;
    }

    public AdapterBaseException(String code, String message, Throwable cause) {
        super(message, cause);
        this.msg = message;
        this.code = code;
    }

    public AdapterBaseException(String message) {
        super(message);
        this.msg = message;
    }
}
