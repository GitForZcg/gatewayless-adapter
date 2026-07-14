package com.personal.demo.adapter.internal.payment;

import com.personal.demo.enu.ServiceType;
import com.personal.demo.request.base.BaseParams;
import com.personal.demo.rule.convert.AbstractParamsConverter;
import org.springframework.stereotype.Component;

/**
 * 付款单参数转换器
 */
@Component
public class PaymentParamsConverter extends AbstractParamsConverter<BaseParams<?>> {

    public PaymentParamsConverter() {
        super(ServiceType.PAYMENT, ServiceType.PAYMENT.desc);
    }

    @Override
    protected BaseParams<?> createNewParams() {
        return new BaseParams<>();
    }
}
