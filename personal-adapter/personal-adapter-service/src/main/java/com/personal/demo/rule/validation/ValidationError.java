package com.personal.demo.rule.validation;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 验证异常
 * @date 2025/7/2 11:00
 */
public record ValidationError(String field, String message) {
    public static ValidationError build(String field, String message) {
        return new ValidationError(field, message);
    }
}
