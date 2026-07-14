package com.personal.demo.pojo.dto.member.response.coupon;

import com.personal.demo.response.member.coupon.PageOptions;
import lombok.Data;

import java.util.List;

/**
 * 会员优惠卷列表参数
 *
 * @Author:
 * @Date:
 */
@Data
public class CouponListRespDto {

    /**
     * 券数据对象
     */
    private List<CouponRespDto> data;

    /**
     * 分页对象
     */
    private PageOptions pageOptions;
}
