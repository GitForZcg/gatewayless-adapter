package com.personal.demo.adapter.internal.trade;

import com.personal.demo.enu.ServiceType;
import com.personal.demo.request.base.BaseParams;
import com.personal.demo.rule.convert.AbstractParamsConverter;
import org.springframework.stereotype.Component;

/**
 * trade参数转换器
 *
 * @Author:
 * @Date:
 */
@Component
public class TradeParamsConverter extends AbstractParamsConverter<BaseParams<?>> {

    public TradeParamsConverter() {
        super(ServiceType.TRADE, ServiceType.TRADE.desc);
    }

    @Override
    protected BaseParams<?> createNewParams() {
        return new BaseParams<>();
    }
}
