package com.personal.demo.pojo.dto.member.response.grade;

import lombok.Data;

/**
 * 升级进度
 *
 * @Author: fxs
 * @Date: 2026/3/14 12:58
 */
@Data
public class ProcessDto {

    /** 升级条件类型: 6.累计消费金额   7.累计消费次数    5.累计充值次数   4.累计充值金额 */
    private Integer ruleType;

    /** 条件设置的数值 */
    private Integer ruleValue;

    /** 当前达到的数值 */
    private Integer value;

    /** 条件设置的时间限制  过往时间x天  0.表示无限制 */
    private Integer ruleDay;
}
