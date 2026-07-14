package com.personal.demo.pojo.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/8/12 10:25
 */
@Data
@Setter
@Getter
public class StoreCompanyDetailRespDto implements Serializable {

    /**
     * 公司组ID
     */
    private String corpgroupid;

    /**
     * 公司组名称
     */
    private String corpgroupname;

    /**
     * 公司ID
     */
    private String corpid;

    /**
     * 公司名称
     */
    private String corpname;

    /**
     * 创建人ID
     */
    private String createrid;

    /**
     * 创建人姓名
     */
    private String creatername;

    /**
     * 创建时间（时间戳）
     */
    private Long creattime;

    /**
     * 创建时间（字符串格式）
     */
    private String creattimeString;

    /**
     * 主键ID
     */
    private String id;

    /**
     * 是否删除（0-未删除，1-已删除）
     */
    private Integer isdeleted;

    /**
     * 项目ID
     */
    private String itemid;

    /**
     * 项目名称
     */
    private String itemname;

    /**
     * 最后修改时间（时间戳）
     */
    private Long lastmodifytime;

    /**
     * 最后修改时间（字符串格式）
     */
    private String lastmodifytimeString;

    /**
     * 公司名称
     */
    private String name;

    /**
     * 编号
     */
    private String number;

    /**
     * 支付币种ID
     */
    private String paycurrencyid;

    /**
     * 会计期间名称
     */
    private String periodname;

    /**
     * 期间类型
     */
    private String periodtype;

    /**
     * 备注
     */
    private String remark;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 时间戳
     */
    private Long ts;

    /**
     * 时间戳（字符串格式）
     */
    private String tsString;

    /**
     * 自定义字段1
     */
    private String vdef1;
}
