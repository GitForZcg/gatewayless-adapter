package com.personal.demo.pojo.dto.member.response.coupon;

import lombok.Data;


/**
 * @author Li QianQian
 * describe:
 */
@Data
public class CouponBg {

    /**
     * 券图片
     */
    private String logo;


    /**
     * 券背景图片
     */
    private String background;

    /**
     * int	是否是默认
     */
    private Integer is_default;


}
