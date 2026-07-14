package com.personal.demo.rule.validation;

import com.common.base.exception.BizException;
import lombok.Getter;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 基础验证异常
 * @date 2025/7/2 10:59
 */
@Getter
public class BaseValidationException extends BizException {
    private final ValidationError error;

    public BaseValidationException(ValidationError error) {
        super("500", String.format("请求参数不正确:[%s]", error.message()));
        this.error = error;
    }
}