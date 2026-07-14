package com.personal.demo.response.member.grade;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import java.util.List;

/**
 * 会员等级DTO
 * @author fxs
 * @date 2026/1/12
 */
@Data
public class GradeResultDto {

    /** 等级ID */
    private String id;

    /** 等级名称 */
    private String name;

    /** 卡面背景图 */
    private String bgImg;

    /** 卡描述图 */
    private String cardImg;

    /** 到期时间；会员当前等级时有值 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String endTime;

    /** 有效期类型 1固定 2相对 */
    private Integer setType;

    /** 相对有效期天数 */
    private String days;

    /** 基础权益列表 */
    private List<BaseRightResultDto> base;

    /** 升级权益列表 */
    private List<UpRightResultDto> upRights;

    /** 增值权益列表 */
    private List<HighRightResultDto> highRight;

    /** 还需消费金额（分）升级 */
    private Integer consumes;

    /** 还需储值金额（分）升级 */
    private Integer charges;

    /** 排序号 */
    private Integer order;

    /** 升级方式1：任一满足 2：全部满足 */
    private  String climbType;

    /** 升级条件 */
    private List<ProcessResultDto> process;
}
