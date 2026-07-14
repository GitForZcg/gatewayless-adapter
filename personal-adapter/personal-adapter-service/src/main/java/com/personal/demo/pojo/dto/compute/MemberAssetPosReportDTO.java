package com.personal.demo.pojo.dto.compute;

import com.personal.demo.pojo.base.DemoComputeMd5Param;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * @author sulu
 * @date 2026年03月17日 2:17 PM
 */
@Data
public class MemberAssetPosReportDTO implements DemoComputeMd5Param, Serializable {
    /**
     * 会员卡号或者手机号，注意 后面计算的时候openid也要使用这个了
     */
    private String  openId;
    /**
     * 商户brandId
     */
    private String bid;
    /**
     * 商户ID
     */
    private String mid;

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
