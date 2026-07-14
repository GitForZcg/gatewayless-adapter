package com.personal.demo.response.member;

import lombok.Data;



/**
 * 操作结果响应 DTO
 *
 * @Author: fxs
 * @Date: 2026/4/14 13:24
 */
@Data
public class OperationResultDto {

    /**
     * 错误码，0表示成功
     */
    private Integer errcode;

    /**
     * 错误信息
     */
    private String errmsg;

    /**
     * 响应结果
     */
    private Res res;

    /**
     * 内部结果类
     */
    @Data
    public static class Res {

        /**
         * 操作结果（如：SUCCESS）
         */
        private String result;
    }
}
