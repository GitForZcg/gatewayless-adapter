package com.personal.demo.adapter.internal.order.handle.mapstruct;

import com.personal.demo.dto.order.DemoBillDTO;
import com.personal.demo.order.dto.ShShbillDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Li QianQian
 * describe:
 */
@Mapper(componentModel = "spring")
public interface DemoOrderConvertPersonalConvert {

    DemoOrderConvertPersonalConvert INSTANCE = Mappers.getMapper(DemoOrderConvertPersonalConvert.class);

    DemoBillDTO demoBillConvertPersonal(ShShbillDto shShbillDto );
}
