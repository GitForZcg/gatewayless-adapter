package com.personal.demo.pojo.dto.order;

import com.personal.demo.dto.order.DemoBillDTO;
import com.personal.demo.dto.order.DemoBillDetailedDTO;
import com.personal.demo.dto.order.DemoBillDetailedTpsDTO;
import com.personal.demo.dto.order.DemoBillPayDTO;
import com.personal.demo.pojo.base.DemoOrderMd5Param;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Set;

/**
 * @author sulu
 * @date 2025年08月13日 11:14 AM
 */
@Accessors(chain = true)
@Data
public class DemoOrderInfoDTO implements DemoOrderMd5Param {

    /**
     * 订单主表
     */
    private List<DemoBillDTO> bills;

    /**
     * 订单产品明细
     */
    private List<DemoBillDetailedDTO> billdetaileds;


    /**
     * 订单支付表
     */
    private List<DemoBillPayDTO> billpays;

    /**
     * 订单产品配料表
     */
    private List<DemoBillDetailedTpsDTO> billdetailedtps;


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
