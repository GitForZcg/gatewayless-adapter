package com.personal.demo.pojo.dto.invoice;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/9/12 13:06
 */
@Data
@Accessors(chain = true)
public class InvoiceCancelReqDto {

    /**
     * 门店id
     */
    @SerializedName("store_id")
    private String storeId;

    /**
     * 商户id
     */
    @SerializedName("tenancy_id")
    private String tenancyId;

    /**
     * 功能类型 (代表取消) CANCLE_LECTRONIC_INVOICE
     */
    @SerializedName("type")
    private String type = "CANCLE_LECTRONIC_INVOICE";

    /**
     * 取消原因
     */
    private List<InvoiceCancelReasonReqDto> data;

}
