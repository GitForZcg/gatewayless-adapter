package com.personal.demo.pojo.wrapper;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/7/21 15:33
 */
@Data
@Getter
@Setter
@Accessors(chain = true)
public class AdapterDemoResponse<T> {

    public Integer code;
    public String msg;
    public T data;

    private static final String SUCCESS = "SUCCESS";

    public AdapterDemoResponse() {
    }


    public AdapterDemoResponse(String respMsg, T bizContent) {
        this.msg = respMsg;
        this.data = bizContent;
        this.code = 200;
    }

    public static <T> AdapterDemoResponse<T> success(T bizContent) {
        AdapterDemoResponse<T> response = successResponse();
        return response.setData(bizContent);
    }

    private static <T> AdapterDemoResponse<T> successResponse() {
        return new AdapterDemoResponse<T>()
                .setCode(200)
                .setMsg(SUCCESS);
    }

    private static <T> AdapterDemoResponse<T> failResponse(String message) {
        return new AdapterDemoResponse<T>()
                .setCode(200)
                .setData(null)
                .setMsg(message);
    }

    public static <T> AdapterDemoResponse<T> fail(String message) {
        return failResponse(message);
    }

}
