package com.personal.demo.rule.convert;

import com.personal.demo.enu.ServiceType;
import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.request.base.AbstractBaseParams;
import com.common.base.exception.BizException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

import static com.personal.demo.consts.AdapterConstant.BIZ_DATA;
import static com.personal.demo.consts.BizCode.PARAMS_CONVERT_ERROR;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 抽象参数转换器
 * @date 2025/7/9 10:48
 */
public abstract class AbstractParamsConverter<T extends AbstractBaseParams> implements BaseParamsConverter<T> {


    @Autowired
    private DataAdapterConvert dataAdapterConvert;
    private final ServiceType supportedServiceType;
    private final String systemName;

    protected AbstractParamsConverter(ServiceType supportedServiceType, String systemName) {
        this.supportedServiceType = supportedServiceType;
        this.systemName = systemName;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T convert(ServiceType type, BaseNode node, Object originalParams) {
        if (originalParams instanceof AbstractBaseParams) {
            return (T) originalParams;
        }
        if (originalParams instanceof Map) {
            Map<String, Object> params = (Map<String, Object>) originalParams;
            T convertedParams = createNewParams();
            Object bizData = params.get(BIZ_DATA);
            if (bizData instanceof Map) {
                convertedParams.setBizData(dataAdapterConvert.convertData(type, node, (Map<String, Object>) bizData));
            }
            return convertedParams;
        }
        throw new BizException(PARAMS_CONVERT_ERROR, String.format("无法转换%s调用参数：%s", systemName, originalParams));
    }

    @Override
    public boolean supports(ServiceType flowType) {
        return flowType == supportedServiceType;
    }

    /**
     * 创建新的参数对象
     */
    protected abstract T createNewParams();
}