package com.personal.demo.enu.internal.base;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 服务动作规范
 * @date 2025/7/2 10:39
 */
public interface BaseNode {

    String name();

    String url();

    default String method() {
        return "";
    }

    default String channel() {
        return "";
    }
    default String nodeDesc() {
        return "";
    }

}
