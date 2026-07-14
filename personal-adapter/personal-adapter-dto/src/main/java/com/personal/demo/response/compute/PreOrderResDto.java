package com.personal.demo.response.compute;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author sulu
 * @date 2026年03月13日 11:34 AM
 */
@Data
public class PreOrderResDto implements Serializable {

    /**
     * 算价方案列表
     */
    private List<PricePromotionResDto> pricePromotionListDTO;

    /**
     * 券列表
     */
    private List<UnusedCouponListDto>  couponList;
}
