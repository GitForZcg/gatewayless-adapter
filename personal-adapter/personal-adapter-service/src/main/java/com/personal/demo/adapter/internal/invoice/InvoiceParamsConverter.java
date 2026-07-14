package com.personal.demo.adapter.internal.invoice;

import com.personal.demo.enu.ServiceType;
import com.personal.demo.request.base.BaseParams;
import com.personal.demo.rule.convert.AbstractParamsConverter;
import org.springframework.stereotype.Component;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 支付参数转换器
 * @date 2025/7/2 10:45
 */
@Component
public class InvoiceParamsConverter extends AbstractParamsConverter<BaseParams<?>> {

    public InvoiceParamsConverter() {
        super(ServiceType.INVOICE, ServiceType.INVOICE.desc);
    }

    @Override
    protected BaseParams<?> createNewParams() {
        return new BaseParams<>();
    }
}
