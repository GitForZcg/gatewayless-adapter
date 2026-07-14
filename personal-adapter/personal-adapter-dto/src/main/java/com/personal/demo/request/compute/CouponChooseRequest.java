package com.personal.demo.request.compute;

import lombok.Data;

import java.io.Serializable;

/**
 * @author sulu
 * @date 2026年03月06日 11:02 AM
 */
@Data
public class CouponChooseRequest implements Serializable {
    /**
     * 优惠券编码
     */
    private String couponCode;

    /**
     * 券模板code
     */
    private String templateCode;

    /**
     * 券类型：菜品券，折扣券，代金券
     */
    private Integer couponType;

}
