package com.personal.demo.pojo.dto.trade.request;

import com.personal.demo.pojo.base.BaseSM2PublicParam;
import lombok.Data;

/**
 * @author Li QianQian
 * describe:
 */
@Data
public class DiyGiftCouponPayReqDto implements BaseSM2PublicParam {
    /**
     * 用户券id
     */
    private String userCouponId;

    /**
     * 券面值 单位:分
     */
    private Integer deno;
}
