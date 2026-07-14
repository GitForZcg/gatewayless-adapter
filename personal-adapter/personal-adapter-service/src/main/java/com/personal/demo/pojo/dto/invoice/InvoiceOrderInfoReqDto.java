package com.personal.demo.pojo.dto.invoice;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/9/12 14:18
 */

@Data
@Accessors(chain = true)
public class InvoiceOrderInfoReqDto {

    /**
     * 1、会员卡号（非必填）
     */
    private String memberNo;

    /**
     * 2、会员手机号（非必填）
     */
    private String memberPhone;

    /**
     * 3、商户ID（必填）
     */
    private String mid;

    /**
     * 4、纳税人识别号（非必填）
     */
    private String sellerNum;

    /**
     * 5、查询类型（必填）：1-点餐，2-商城，3-礼品卡，4-充值
     */
    private String type = "1";

    /**
     * 6、商品名称（非必填）
     */
    private String productName;

    /**
     * 7、订单号（非必填，模糊匹配）
     */
    private String orderNum;

    /**
     * 8、订单号列表（非必填，精确匹配）
     */
    private List<String> orderNumList;

    /**
     * 9、页码（非必填，默认值：1）
     */
    private Integer page;

    /**
     * 10、每页大小（非必填，默认值：10）
     */
    private Integer size;
}
