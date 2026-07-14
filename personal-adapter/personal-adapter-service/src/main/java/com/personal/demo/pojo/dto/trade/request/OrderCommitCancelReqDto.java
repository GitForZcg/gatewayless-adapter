package com.personal.demo.pojo.dto.trade.request;

import com.personal.demo.pojo.base.BaseTradePublicParam;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class OrderCommitCancelReqDto implements BaseTradePublicParam {

    /**
     * 撤销结果
     */
    private String biz_id;
    private int cashier_id = -1;

    @Override
    public String orderId() {
        return createOrderId(this.biz_id);
    }
}
