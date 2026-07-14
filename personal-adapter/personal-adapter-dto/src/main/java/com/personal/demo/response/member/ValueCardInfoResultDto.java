package com.personal.demo.response.member;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 储值卡信息业务数据
 *
 * @author fxs
 * @date 2025/01/13
 */
@Data
public class ValueCardInfoResultDto {

    /**
     * 储值卡卡号
     */
    private String vcNo;
    /**
     * 储值卡面额（分）
     */
    private Integer vcFacePrice;

    /**
     * 充值有效期
     */
    private String valueEndTime;

    /**
     * 储值卡状态 1正常 2已出售 3已使用
     */
    private Integer vcStatus;

    /**
     * 储值卡到期类型 1年 2月 3日 4永久有效
     */
    private Integer vcExpiredType;

    /**
     * 储值卡到期时间
     */
    private String vcEndTime;
}
