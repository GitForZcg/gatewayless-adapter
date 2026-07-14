package com.personal.demo.response.member.coupon;

import com.personal.demo.response.member.coupon.CouponDetailResultDto;
import com.personal.demo.response.member.coupon.PageOptions;
import lombok.Data;

import java.util.List;

/**
 * @author Li QianQian
 * describe:
 */
@Data
public class UnUsedCouponListResultDto {

    /**
     * 未使用的优惠券列表数据
     */
    List<CouponDetailResultDto> couponDetailResultDtoList;

    /**
     * 分页对象
     */
    private PageOptions pageOptions;


}
