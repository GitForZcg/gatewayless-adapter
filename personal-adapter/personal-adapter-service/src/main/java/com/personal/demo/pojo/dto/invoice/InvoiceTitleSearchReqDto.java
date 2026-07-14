package com.personal.demo.pojo.dto.invoice;

import lombok.Data;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/9/12 16:50
 */
@Data
public class InvoiceTitleSearchReqDto {

    /**
     * 1、商户uuid（必填）
     */
    private String tenancyId;

    /**
     * 2、门店uuid（必填）
     */
    private String storeId;

    /**
     * 3、销方名称（必填）
     */
    private String name;

    /**
     * 4、通知场景（必填，用json传输）------默认为消费，1.储值; 2.商城消费; 3.消费
     */
    private int sceneNotify = 3;
}
