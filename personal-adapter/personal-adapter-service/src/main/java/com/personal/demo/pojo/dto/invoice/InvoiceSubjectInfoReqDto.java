package com.personal.demo.pojo.dto.invoice;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 发票主体信息
 * @date 2025/9/11 13:35
 */
@Data
@Accessors(chain = true)
public class InvoiceSubjectInfoReqDto {

    /**
     * 1、商户uuid
     * 必填字段
     */
    private String strTenancyId;

    /**
     * 2、开票类型
     * 1 拆分开票
     * 2 合并开票
     * 3 正常开票
     * 必填字段
     */
    private Integer invoiceType;

    /**
     * 3、类型
     * 1 就餐
     * 2 商城
     * 3 礼品卡
     * 4 储值
     * 必填字段
     */
    private Integer dinType;

    /**
     * 4、通知场景
     * 默认为消费
     * 1.储值
     * 2.商城消费
     * 3.消费
     * 非必填字段
     */
    private String sceneNotify;

    /**
     * 5、总开票金额
     * 只针对合并场景
     * 必填字段
     */
    private BigDecimal totalInvoiceAmount;

    /**
     * 6、请求信息
     * json数组
     * 非必填字段
     */
    private List<InvoiceDetailInfoReqDto> info;

    /**
     * 7、加密串
     * 必填字段
     */
    private String signKey;
}
