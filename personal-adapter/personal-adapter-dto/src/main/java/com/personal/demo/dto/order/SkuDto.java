package com.personal.demo.dto.order;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Li QianQian
 * describe:
 */
@Data
public class SkuDto implements Serializable {

    /**
     * 产品code
     */
    private String productCode;

    /**
     * 产品名称
     */
    private String productName;
    /**
     * skuCode
     */
    private String skuCode;

    /**
     * skuNum
     */
    private Integer skuNum;

    /**
     * skuVersion
     */
    private Integer skuVersion;


    private List<String> subSpecCodeList;
}
