package com.personal.demo.anno;

import java.lang.annotation.*;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 签名注解
 * @date 2025/7/8 10:46
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Signature {

    /**
     * 加签/验签策略类型
     */
    String strategy();

    /**
     * 是否必须加签/验签
     */
    boolean required() default true;

    /**
     * 签名字段名称
     */
    String signField() default "";

    /**
     * app_id字段名称
     */
    String appIdField() default "";
}
