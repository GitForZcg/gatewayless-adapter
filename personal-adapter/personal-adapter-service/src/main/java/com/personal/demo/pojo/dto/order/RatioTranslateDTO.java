package com.personal.demo.pojo.dto.order;

import com.personal.demo.dto.order.DemoBillDTO;
import com.personal.demo.dto.order.DemoBillDetailedDTO;
import com.personal.order.dto.order.AppletOrderDetailDto;
import com.personal.order.dto.order.PersonalOrderInfoItemDto;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author sulu
 * @date 2026年04月10日 5:19 PM
 */
@Data
public class RatioTranslateDTO implements Serializable {

    /**
     * 账单主表
     */
    private DemoBillDTO billDTO;

    /**
     * 当前明细值
     */
    private PersonalOrderInfoItemDto orderDetail;

    /**
     * 订单数据
     */
    private AppletOrderDetailDto orderDTO;

    /**
     * 商品编码映射
     */
    private Map<String, String> prodCodeMap;

    /**
     * 代金券已分摊的价格
     */
    private BigDecimal wallet = BigDecimal.ZERO;

    /**
     * 代金券分摊比例
     */
    private BigDecimal walletRatio = BigDecimal.ZERO;

    /**
     * 代金券成本值
     */
    private BigDecimal walletPay = BigDecimal.ZERO;

    /**
     * 余额已分摊的价格
     */
    private BigDecimal balance = BigDecimal.ZERO;

    /**
     * 余额分摊比例
     */
    private BigDecimal balanceRatio = BigDecimal.ZERO;

    /**
     * 储值成本值
     */
    private BigDecimal balancePay = BigDecimal.ZERO;

    /**
     * 账单明细表
     */
    private List<DemoBillDetailedDTO> billDetailedDTOList =  new ArrayList<>();

    /**
     * 是否是最后一个商品
     */
    private Boolean lastItem = false;
}
