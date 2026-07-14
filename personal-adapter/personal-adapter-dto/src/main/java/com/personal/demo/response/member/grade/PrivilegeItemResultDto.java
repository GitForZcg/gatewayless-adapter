package com.personal.demo.response.member.grade;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * 特权项DTO
 * @author fxs
 * @date 2025/01/13
 */
@Data
public class PrivilegeItemResultDto {

    /** 特权ID */
    private String itemId;

    /** 图标 */
    private String pic;

    /** 是否已享 1是 0否 */
    private String enjoy;

    /** 特权内容 */
    private String value;

    /** 说明 */
    private String explain;
}
