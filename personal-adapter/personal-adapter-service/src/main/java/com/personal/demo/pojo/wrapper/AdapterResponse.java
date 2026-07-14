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
public class AdapterResponse<T> {

    public String returnCode;
    public String respCode;
    public String errorCode;
    public String respMsg;
    public T bizContent;

    private static final String SUCCESS = "SUCCESS";
    private static final String FAIL = "FAIL";

    public AdapterResponse() {
    }


    public AdapterResponse(String code, String respMsg, T bizContent) {
        this.respCode = code;
        this.respMsg = respMsg;
        this.bizContent = bizContent;
        this.returnCode = SUCCESS;
    }

    public static <T> AdapterResponse<T> success(T bizContent) {
        AdapterResponse<T> response = successResponse();
        return response.setBizContent(bizContent);
    }

    private static <T> AdapterResponse<T> successResponse() {
        return new AdapterResponse<T>()
                .setRespCode(SUCCESS)
                .setReturnCode(SUCCESS)
                .setRespMsg("成功");
    }

    private static <T> AdapterResponse<T> failResponse(String code, String message) {
        return new AdapterResponse<T>()
                .setRespCode(FAIL)
                .setErrorCode(code)
                .setReturnCode(SUCCESS)
                .setRespMsg(message);
    }

    public static <T> AdapterResponse<T> fail(String code, String message) {
        return failResponse(code, message);
    }

}
