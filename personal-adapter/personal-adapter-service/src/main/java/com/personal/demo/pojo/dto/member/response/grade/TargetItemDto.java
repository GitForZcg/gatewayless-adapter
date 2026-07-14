package com.personal.demo.pojo.dto.member.response.grade;

import lombok.Data;

/**
 * 升级任务项DTO
 * @author fxs
 * @date 2025/01/13
 */
@Data
public class TargetItemDto {

    /** 任务类型 percent/notpercent */
    private String type;

    /** 任务描述 */
    private String target;

    /** 完成百分比；0表示无百分比 */
    private Integer value;

    /** 图标URL；可能空 */
    private String url;
}
