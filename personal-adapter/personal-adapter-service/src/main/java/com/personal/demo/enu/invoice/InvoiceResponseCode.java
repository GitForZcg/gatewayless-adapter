package com.personal.demo.enu.invoice;

import lombok.Getter;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 开票响应码枚举类
 * @date 2025/9/11 15:06
 */
@Getter
public enum InvoiceResponseCode {

    // ==================== 基础响应码 ====================

    /**
     * 成功
     */
    SUCCESS("0000", "成功"),

    /**
     * 失败
     */
    FAILURE("4444", "失败"),

    /**
     * 其他错误 (包含代码错误异常信息反馈等等)
     */
    OTHER_ERROR("4447", "其他错误 (包含代码错误异常信息反馈等等)"),

    // ==================== 业务错误码 ====================

    /**
     * 开票参数para为空
     */
    PARAM_EMPTY("0001", "开票参数para为空"),

    /**
     * 参数长度异常
     */
    PARAM_LENGTH_ERROR("0002", "参数长度异常"),

    /**
     * 购买方类型不能为空
     */
    BUYER_TYPE_EMPTY("0003", "购买方类型不能为空"),

    /**
     * 购买方纳税人识别号不能为空
     */
    BUYER_TAX_ID_EMPTY("0004", "购买方纳税人识别号不能为空"),

    /**
     * 购买方手机或邮箱选填一个
     */
    BUYER_CONTACT_REQUIRED("0005", "购买方手机或邮箱选填一个"),

    /**
     * 订单号不能为空
     */
    ORDER_NUM_EMPTY("0006", "订单号不能为空"),

    /**
     * 合并开票不在一个纳税主体下
     */
    MERGE_INVOICE_TAX_ENTITY_ERROR("0009", "合并开票不在一个纳税主体下");

    // ==================== 枚举属性 ====================

    /**
     * 响应码
     * -- GETTER --
     *  获取响应码
     *
     * @return 响应码

     */
    private final String code;

    /**
     * 响应消息
     * -- GETTER --
     *  获取响应消息
     *
     * @return 响应消息

     */
    private final String message;

    // ==================== 构造方法 ====================

    /**
     * 构造方法
     *
     * @param code    响应码
     * @param message 响应消息
     */
    InvoiceResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    // ==================== Getter方法 ====================

    // ==================== 工具方法 ====================

    /**
     * 根据响应码获取枚举对象
     *
     * @param code 响应码
     * @return 枚举对象，找不到时返回null
     */
    public static InvoiceResponseCode getByCode(String code) {
        if (code == null) {
            return null;
        }

        for (InvoiceResponseCode responseCode : values()) {
            if (responseCode.getCode().equals(code)) {
                return responseCode;
            }
        }
        return null;
    }

    /**
     * 根据响应码获取响应消息
     *
     * @param code 响应码
     * @return 响应消息，找不到时返回默认消息
     */
    public static String getMessageByCode(String code) {
        InvoiceResponseCode responseCode = getByCode(code);
        return responseCode != null ? responseCode.getMessage() : "未知错误码: " + code;
    }

    /**
     * 判断是否成功
     *
     * @param code 响应码
     * @return 是否成功
     */
    public static boolean isSuccess(String code) {
        return SUCCESS.getCode().equals(code);
    }

    /**
     * 判断当前枚举是否成功
     *
     * @return 是否成功
     */
    public boolean isSuccess() {
        return this == SUCCESS;
    }

    /**
     * 判断是否失败
     *
     * @param code 响应码
     * @return 是否失败
     */
    public static boolean isFailure(String code) {
        return FAILURE.getCode().equals(code) || OTHER_ERROR.getCode().equals(code);
    }

    /**
     * 判断当前枚举是否失败
     *
     * @return 是否失败
     */
    public boolean isFailure() {
        return this == FAILURE || this == OTHER_ERROR;
    }

    /**
     * 判断是否业务错误
     *
     * @param code 响应码
     * @return 是否业务错误
     */
    public static boolean isBusinessError(String code) {
        InvoiceResponseCode responseCode = getByCode(code);
        if (responseCode == null) {
            return false;
        }

        return responseCode == PARAM_EMPTY
                || responseCode == PARAM_LENGTH_ERROR
                || responseCode == BUYER_TYPE_EMPTY
                || responseCode == BUYER_TAX_ID_EMPTY
                || responseCode == BUYER_CONTACT_REQUIRED
                || responseCode == ORDER_NUM_EMPTY
                || responseCode == MERGE_INVOICE_TAX_ENTITY_ERROR;
    }

    /**
     * 判断当前枚举是否业务错误
     *
     * @return 是否业务错误
     */
    public boolean isBusinessError() {
        return this == PARAM_EMPTY
                || this == PARAM_LENGTH_ERROR
                || this == BUYER_TYPE_EMPTY
                || this == BUYER_TAX_ID_EMPTY
                || this == BUYER_CONTACT_REQUIRED
                || this == ORDER_NUM_EMPTY
                || this == MERGE_INVOICE_TAX_ENTITY_ERROR;
    }

}
