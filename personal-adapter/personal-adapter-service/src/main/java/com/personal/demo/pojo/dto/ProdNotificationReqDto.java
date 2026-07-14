package com.personal.demo.pojo.dto;

import com.personal.demo.pojo.base.BaseSM2PublicParam;
import lombok.Data;

/**
 * 对接AQW的请求参数
 *
 * @Author: fxs
 * @Date: 2025/8/13 15:02
 */
@Data
public class ProdNotificationReqDto implements BaseSM2PublicParam {

    private int code = 200;
    private String msg = "SUCCESS";

    /**
     * 集团代码
     */
    private String mid;

    /**
     * 品牌代码
     */
    private String bid;

    /**
     * 门店代码
     */
    private String sid;

    /**
     * 批次号
     */
    private String batchNumber;
}
