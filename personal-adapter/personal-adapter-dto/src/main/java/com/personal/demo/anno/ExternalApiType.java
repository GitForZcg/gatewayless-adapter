package com.personal.demo.anno;

import com.personal.demo.enu.external.ExternalChannel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/7/7 10:38
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExternalApiType {
    String value(); // API类型名称

    ExternalChannel channel();
}