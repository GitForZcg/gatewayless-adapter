package com.personal.demo.pojo.dto.member.response.coupon;

import lombok.Data;

import java.util.List;

/**
 * @author Li QianQian
 * describe:
 */
@Data
public class CExtend {
    /**giveCover**/
    private GiveCover giveCover;

    /**expireRemind**/
    private int expireRemind;

    /**periodUseNum**/
    private PeriodUseNum periodUseNum;

    /**
     * discountRule
     **/
    private Integer discountRule;

    /**
     * noHoliday
     **/
    private List<Integer> noHoliday;

    /**
     * consumeType
     **/
    private Integer consumeType;

    /**
     * mallType
     **/
    private Integer mallType;

    /**
     * holidayType
     **/
    private Integer holidayType;

    /**
     * validType
     **/
    private Integer validType;

    /**
     * ceType
     **/
    private String ceType;

    /**
     * redemptionLimitDisplay
     **/
    private Integer redemptionLimitDisplay;


}
