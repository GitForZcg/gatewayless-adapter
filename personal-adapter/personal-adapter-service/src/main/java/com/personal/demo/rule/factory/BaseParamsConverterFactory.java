package com.personal.demo.rule.factory;

import com.personal.demo.enu.ServiceType;
import com.personal.demo.request.base.AbstractBaseParams;
import com.personal.demo.rule.convert.BaseParamsConverter;
import com.common.base.exception.BizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.personal.demo.consts.BizCode.CONVERTER_NOT_FOUND;


/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 参数转换器工厂
 * @date 2025/7/2 10:50
 */
@Component
public class BaseParamsConverterFactory {
    private final List<BaseParamsConverter<?>> converters;

    @Autowired
    public BaseParamsConverterFactory(List<BaseParamsConverter<?>> converters) {
        this.converters = converters;
    }

    @SuppressWarnings("unchecked")
    public <T extends AbstractBaseParams> BaseParamsConverter<T> getConverter(ServiceType type) {
        return (BaseParamsConverter<T>) converters.stream()
                .filter(converter -> converter.supports(type))
                .findFirst()
                .orElseThrow(() -> new BizException(
                        CONVERTER_NOT_FOUND,
                        String.format("未找到适合的调用方式:[%s]", type.getDesc())
                ));
    }
}
