package com.personal.demo.pojo.dto.member.response.coupon;

import lombok.Data;

/**
 * @author Li QianQian
 * describe:
 */
@Data
public class GiveCover {

    private Integer giveCoverEnable;

    private Integer giveCoverSet;

    private String giveCoverUrl;

    private String giveCoverDesc;

    private String receiveImg;

    private String receiveColor;
}
