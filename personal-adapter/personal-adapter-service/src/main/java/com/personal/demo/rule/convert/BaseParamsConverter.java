package com.personal.demo.rule.convert;


import com.personal.demo.enu.ServiceType;
import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.request.base.AbstractBaseParams;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 参数转换器规则
 * @date 2025/7/2 10:49
 */
public interface BaseParamsConverter<T extends AbstractBaseParams> {
    /**
     * 转换参数
     *
     * @param originalParams 原始参数
     */
    T convert(ServiceType serviceType, BaseNode node, Object originalParams);

    /**
     * 检查是否支持当前检索流程
     *
     * @param serviceType 流程类型
     * @return 是否支持
     */
    boolean supports(ServiceType serviceType);
}