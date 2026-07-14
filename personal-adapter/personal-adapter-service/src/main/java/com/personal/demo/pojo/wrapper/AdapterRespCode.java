package com.personal.demo.pojo.wrapper;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 签名相关异常code
 * @date 2025/7/21 15:48
 */
public enum AdapterRespCode implements BaseResponse {

    ILLEGAL_API_ERROR("非法api请求", "ILLEGAL_REQUEST"),
    SIGN_MISSING("缺少sign签名信息", "SIGN_MISSING"),
    APPID_MISSING("缺少appid签名信息", "APPID_MISSING"),
    APPID_NOT_MATCH("appid不匹配", "APPID_NOT_MATCH"),
    APPID_ERROR("appid错误或appid过期", "APPID_ERROR"),
    PARAM_ERROR("请求参数不正确,[%s]", "PARAM_ERROR"),
    API_SIGN_VERIFY_FAIL("API签名验证失败", "API_SIGN_VERIFY_FAIL"),
    SIGN_VERIFY_FAIL("业务数据签名验证失败", "SIGN_VERIFY_FAIL"),
    SIGN_VERIFY_ERROR("签名验证异常", "SIGN_VERIFY_ERROR"),
    SIGN_PARAM_MISSING("缺少API签名参数", "SIGN_PARAM_MISSING"),
    REQUEST_TIMESTAMP_ERROR("请求时间戳已过期", "REQUEST_TIMESTAMP_EXPIRED"),
    SIGN_CALCULATE_ERROR("生成签名异常", "SIGN_CALCULATE_ERROR"),
    UNKNOWN_SYSTEM_ERROR("系统异常", "SYSTEM_ERROR"),

    ;

    final public String message;
    final public String code;

    AdapterRespCode(String message, String code) {
        this.message = message;
        this.code = code;
    }

    @Override
    public String message() {
        return this.message;
    }

    @Override
    public String code() {
        return this.code;
    }

}
