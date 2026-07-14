package com.personal.demo.adapter.internal.compute;

import com.personal.demo.enu.ServiceType;
import com.personal.demo.request.base.BaseParams;
import com.personal.demo.rule.convert.AbstractParamsConverter;
import org.springframework.stereotype.Component;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 订单参数转换器
 * @date 2025/7/2 10:44
 */
@Component
public class ComputeParamsConverter extends AbstractParamsConverter<BaseParams<?>> {

    public ComputeParamsConverter() {
        super(ServiceType.COMPUTE, ServiceType.COMPUTE.desc);
    }

    @Override
    protected BaseParams<?> createNewParams() {
        return new BaseParams<>();
    }
}
