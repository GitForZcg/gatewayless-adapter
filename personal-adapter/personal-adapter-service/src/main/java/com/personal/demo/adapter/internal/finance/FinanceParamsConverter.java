package com.personal.demo.adapter.internal.finance;

import com.personal.demo.enu.ServiceType;
import com.personal.demo.request.base.BaseParams;
import com.personal.demo.rule.convert.AbstractParamsConverter;
import org.springframework.stereotype.Component;

/**
 * 财务参数转换器
 * @date: 2025/9/10 15:35 
 */
@Component
public class FinanceParamsConverter extends AbstractParamsConverter<BaseParams<?>> {

    public FinanceParamsConverter() {
        super(ServiceType.FINANCE, ServiceType.FINANCE.desc);
    }

    @Override
    protected BaseParams<?> createNewParams() {
        return new BaseParams<>();
    }
}
