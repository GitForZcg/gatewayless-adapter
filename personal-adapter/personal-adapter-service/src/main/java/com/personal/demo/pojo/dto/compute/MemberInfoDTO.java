package com.personal.demo.pojo.dto.compute;

import com.personal.demo.pojo.base.DemoComputeMd5Param;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * @author sulu
 * @date 2026年03月02日 6:38 PM
 */
@Data
public class MemberInfoDTO implements DemoComputeMd5Param, Serializable {

    /**
     * 用户ID
     */
    private String uid;

    /**
     * 会员等级ID
     */
    private String grade;

    /**
     * 登录状态:"true"表示登录,"false"表示未登录
     */
    private String isLogin;

    /**
     * 会员储值余额(单位:分)
     */
    private long balance;

    /**
     * 会员券列表
     */
    List<CouponAssetDTO> coupons;



    @Override
    public Set<String> needSign() {
        return null;
    }

    @Override
    public Set<String> needSignParam() {
        return null;
    }

    @Override
    public String orderId() {
        return null;
    }
}
