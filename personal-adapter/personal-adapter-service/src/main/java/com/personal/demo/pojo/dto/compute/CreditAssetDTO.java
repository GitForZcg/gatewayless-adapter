package com.personal.demo.pojo.dto.compute;

import com.personal.demo.pojo.base.DemoComputeMd5Param;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * @author sulu
 * @date 2026年03月03日 10:41 AM
 */
@Data
public class CreditAssetDTO implements DemoComputeMd5Param, Serializable {

    /**
     * 总积分数
     */
    private Integer total;

    /**
     * 积分抵扣比例(如100表示100积分抵1元)
     */
    private Integer ratio;

    /**
     * 单次最大可用积分抵扣金额(单位:分)
     */
    private Integer useMaxCreditMoney;

    /**
     * 本次实际抵扣积分数
     */
    private Integer creditDeduct;


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
