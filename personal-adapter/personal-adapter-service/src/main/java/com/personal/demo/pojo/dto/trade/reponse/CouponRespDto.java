package com.personal.demo.pojo.dto.trade.reponse;

import lombok.Data;

/**
 * @author Li QianQian
 * describe:
 */
@Data
public class CouponRespDto  {

    /**
     * 券模板id
     */
    private Long couponid;

    /**
     * 券类型
     */
    private Integer type;

    /**
     * 券名称
     */
    private String name;

    /**
     * 券面额(单位:元)
     */
    private Integer amount;

    /**
     * 使用条件与限制
     */
    private String summary;

    /**
     * 赠送数量
     */
    private Integer num;

}
