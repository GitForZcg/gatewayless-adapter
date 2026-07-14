package com.personal.demo.dto.order;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author sulu
 * @date 2025年08月08日 11:24 AM
 */
@Data
public class DemoOrderDTO implements Serializable {

    /**
     * 订单主表
     */
    @SerializedName("bills")
    private List<DemoBillDTO> bills;

    /**
     * 订单产品明细
     */
    @SerializedName("billdetaileds")
    private List<DemoBillDetailedDTO> billdetaileds;


    /**
     * 订单支付表
     */
    @SerializedName("billpays")
    private List<DemoBillPayDTO> billpays;

    /**
     * 订单产品配料表
     */
    @SerializedName("billdetailedtps")
    private List<DemoBillDetailedTpsDTO> billdetailedtps;

}
