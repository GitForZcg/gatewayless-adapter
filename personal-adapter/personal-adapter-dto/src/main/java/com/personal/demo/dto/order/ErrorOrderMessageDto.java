package com.personal.demo.dto.order;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Li QianQian
 * describe:
 */
@Data
public class ErrorOrderMessageDto implements Serializable {



    /**
     * 渠道
     */
    private String channel;

    private String orderUuid;

    /**
     * posOrderNo
     */
    private String posOrderNo;

    /**
     * billNum
     */
    private String billNum;
    /**
     * 平台原始单号
     */
    private String thirdBillNum;
    /**
     * 产品信息
     */
    private List<SkuDto> skuList;
}
