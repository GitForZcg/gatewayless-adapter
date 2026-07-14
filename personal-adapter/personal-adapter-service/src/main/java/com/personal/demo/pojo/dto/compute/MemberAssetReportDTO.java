package com.personal.demo.pojo.dto.compute;

import com.personal.demo.pojo.base.DemoComputeMd5Param;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * @author sulu
 * @date 2026年03月02日 6:35 PM
 */
@Data
public class MemberAssetReportDTO implements DemoComputeMd5Param, Serializable {

    /**
     * 商户ID
     */
    private String mid;

    /**
     * 用户OpenID
     */
    private String openId;

    /**
     * 会员信息
     */
    private MemberInfoDTO info;



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
