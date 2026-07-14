package com.personal.demo.pojo.base;

import java.util.Map;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/7/8 10:48
 */
public interface SignatureValidationParam {

    /**
     * 获取签名
     */
    String getSignature();

    /**
     * 获取应用ID
     */
    String getAppId();

    /**
     * 获取用于验签的所有参数
     */
    Map<String, Object> getSignatureParams();

    Map<String, Object> getOriginalParams();

}
