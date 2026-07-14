package com.personal.demo.pojo.base;

import lombok.Data;
import lombok.experimental.Accessors;

import java.nio.charset.StandardCharsets;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/7/15 13:36
 */
@Data
@Accessors(chain = true)
public class BaseResponse {

    private String encoding;
    private String returnCode;
    private String respCode;
    private String biz_content;
    private String sign;

    public static BaseResponse buildHeader() {
        return new BaseResponse()
                .setEncoding(StandardCharsets.UTF_8.toString());

    }
}
