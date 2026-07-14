package com.personal.demo.pojo.dto.compute;

import com.personal.demo.pojo.base.DemoComputeMd5Param;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * @author sulu
 * @date 2026年01月19日 5:41 PM
 */
@Data
public class PromotionRequestDTO implements DemoComputeMd5Param,Serializable  {
    /**
     * 主版本号，默认为0
     */
    private Integer majorVersion;

    /**
     * 商户ID
     */
    private String mid;

    /**
     * 门店UUID
     */
    private String sid;

    /**
     * 会员等级ID
     */
    private String gradeId;

    /**
     * 用户OpenID
     */
    private String openId;

    /**
     * 渠道标识
     */
    private String channel;

    /**
     * 是否登录标识
     */
    private String isLogin;

    /**
     * 是否展示会员价全部活动人群
     */
    private String isAllPeople;

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
