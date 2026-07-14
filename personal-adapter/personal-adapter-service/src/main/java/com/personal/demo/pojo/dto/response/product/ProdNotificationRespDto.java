package com.personal.demo.pojo.dto.response.product;

import com.personal.demo.pojo.base.BaseSM2PublicParam;
import lombok.Data;

/**
 * 对接AQW的请求参数
 *
 * @Author: fxs
 * @Date: 2025/8/13 15:02
 */
@Data
public class ProdNotificationRespDto implements BaseSM2PublicParam {

    private int code;

    private String msg;

}
