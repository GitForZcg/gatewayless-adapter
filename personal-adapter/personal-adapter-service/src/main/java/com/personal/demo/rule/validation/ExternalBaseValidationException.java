package com.personal.demo.rule.validation;

import com.personal.demo.pojo.wrapper.AdapterRespCode;
import com.personal.demo.rule.exception.AdapterBaseException;
import lombok.Getter;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 基础验证异常
 * @date 2025/7/4 10:59
 */
@Getter
public class ExternalBaseValidationException extends AdapterBaseException {

    private final ValidationError error;

    public ExternalBaseValidationException(ValidationError error) {
        super(AdapterRespCode.PARAM_ERROR.code, String.format(AdapterRespCode.PARAM_ERROR.message, error.message()));
        this.error = error;
    }
}