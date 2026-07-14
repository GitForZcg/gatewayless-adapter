package com.personal.demo.enu.invoice;

import lombok.Getter;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/9/17 13:26
 */
@Getter
public enum InvoiceStatus {
    INVALID_INVOICE_NUMBER("无效的发票号", "0"),
    PENDING_INVOICE("待开票", "1"),
    INVOICE_FAILED("开票失败", "2"),
    INVOICE_SUCCESS("开票成功", "3"),
    CANCELING("取消中", "4"),
    CANCEL_FAILED("取消失败", "5"),
    CANCEL_INVOICE("取消开票", "6"),
    CANCELED("已取消", "7"),
    CANCEL_REVERSAL("取消冲正", "8"),
    INVOICING("开票中", "9"),
    NOT_INVOICED("未开票", "10"),
    RED_REVERSING("红冲中", "11"),
    RED_REVERSE_SUCCESS("红冲成功", "12"),
    RED_REVERSE_FAILED("红冲失败", "13"),
    VOID_SUCCESS("作废成功", "14"),
    VOID_FAILED("作废失败", "15");

    /**
     * -- GETTER --
     * 获取状态描述
     *
     * @return 状态描述
     */
    public final String description;
    /**
     * -- GETTER --
     * 获取状态代码
     *
     * @return 状态代码
     */
    public final String code;

    /**
     * 构造函数
     *
     * @param description 状态描述
     * @param code        状态代码
     */
    InvoiceStatus(String description, String code) {
        this.description = description;
        this.code = code;
    }

    /**
     * 根据状态代码获取对应的枚举
     *
     * @param code 状态代码
     * @return 对应的枚举，如果找不到则返回null
     */
    public static InvoiceStatus getByCode(String code) {
        for (InvoiceStatus status : InvoiceStatus.values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }

    /**
     * 根据状态描述获取对应的枚举
     *
     * @param description 状态描述
     * @return 对应的枚举，如果找不到则返回null
     */
    public static InvoiceStatus getByDescription(String description) {
        for (InvoiceStatus status : InvoiceStatus.values()) {
            if (status.getDescription().equals(description)) {
                return status;
            }
        }
        return null;
    }

    /**
     * 判断是否为成功状态
     *
     * @return true表示成功状态
     */
    public boolean isSuccess() {
        return this == INVOICE_SUCCESS || this == RED_REVERSE_SUCCESS || this == VOID_SUCCESS;
    }

    /**
     * 判断是否为失败状态
     *
     * @return true表示失败状态
     */
    public boolean isFailed() {
        return this == INVOICE_FAILED || this == CANCEL_FAILED ||
                this == RED_REVERSE_FAILED || this == VOID_FAILED;
    }

    /**
     * 判断是否为进行中状态
     *
     * @return true表示进行中状态
     */
    public boolean isInProgress() {
        return this == INVOICING || this == CANCELING || this == RED_REVERSING;
    }

    @Override
    public String toString() {
        return String.format("%s(%d)", description, code);
    }

}
