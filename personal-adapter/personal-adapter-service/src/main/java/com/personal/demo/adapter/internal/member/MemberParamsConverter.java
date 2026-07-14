package com.personal.demo.adapter.internal.member;

import com.personal.demo.enu.ServiceType;
import com.personal.demo.request.base.BaseParams;
import com.personal.demo.rule.convert.AbstractParamsConverter;
import org.springframework.stereotype.Component;

/**
 * 会员参数转换器
 *
 * @Author: fxs
 * @Date: 2026/1/12 15:40
 */
@Component
public class MemberParamsConverter extends AbstractParamsConverter<BaseParams<?>> {

    public MemberParamsConverter() {
        super(ServiceType.MEMBER, ServiceType.MEMBER.desc);
    }

    @Override
    protected BaseParams<?> createNewParams() {
        return new BaseParams<>();
    }
}
