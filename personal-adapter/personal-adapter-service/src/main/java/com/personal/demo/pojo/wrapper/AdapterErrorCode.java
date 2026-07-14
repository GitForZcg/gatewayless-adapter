package com.personal.demo.pojo.wrapper;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/7/21 15:48
 */
public enum AdapterErrorCode implements BaseResponse {
    SIGN_RULE_ERROR("参数类必须实现%s接口", "SIGN_RULE_ERROR", "001"),
    NODE_NOT_FOUND_ERROR("当前请求调用节点不存在", "NODE_NOT_FOUND", "002"),
    FLOW_NOT_SUPPORT("不支持的调用流程类型:%s", "FLOW_NOT_SUPPORT", "003"),
    PRIVATE_KEY_NOT_FOUND("加签获取私钥异常", "PRIVATE_KEY_NOT_FOUND", "004"),
    SIGN_VERIFY_STRATEGY_NOT_SUPPORT("不支持的验签策略[%s]", "SIGN_STRATEGY_NOT_SUPPORT", "005"),
    VALID_GROUP_MISSING("找不到对应的验证组[%s]", "VALID_GROUP_MISSING", "006"),
    SIGN_CALCULATE_NOT_SUPPORT("不支持的加签策略[%s]", "SIGN_CALCULATE_NOT_SUPPORT", "007"),

    ;

    final public String message;
    final public String code;
    final public String type;

    AdapterErrorCode(String message, String code, String type) {
        this.message = message;
        this.code = code;
        this.type = type;
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
