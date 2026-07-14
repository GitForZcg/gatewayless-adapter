package com.personal.demo.pojo.wrapper;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/7/21 15:46
 */
public interface BaseResponse {

    String message();

    String code();

    default String returnCode() {
        return "SUCCESS";
    }
}
