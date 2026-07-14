package com.personal.demo.pojo.base;

import lombok.Data;
import lombok.experimental.Accessors;

import static com.personal.demo.consts.PayConstant.PAY_SIGN_TYPE;
import static com.personal.demo.consts.PayConstant.PAY_VERSION;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/11/6 10:16
 */
@Data
@Accessors(chain = true)
public class BasePayRequest {

    private String baId;
    private String appId;
    private String sign;
    private Long timestamp;
    private String bizData;
    private String signType;
    private String version;

    public static BasePayRequest buildParams() {
        return new BasePayRequest().setVersion(PAY_VERSION)
                .setSignType(PAY_SIGN_TYPE);

    }
}
