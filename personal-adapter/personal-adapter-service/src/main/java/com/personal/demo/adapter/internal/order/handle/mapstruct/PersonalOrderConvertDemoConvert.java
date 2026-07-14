package com.personal.demo.adapter.internal.order.handle.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Li QianQian
 * describe:
 */
@Mapper(componentModel = "spring")
public interface PersonalOrderConvertDemoConvert {

    PersonalOrderConvertDemoConvert INSTANCE = Mappers.getMapper(PersonalOrderConvertDemoConvert.class);
}
