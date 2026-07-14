package com.personal.demo.adapter.internal.store.convert;

import com.personal.demo.pojo.dto.DemoStoreCompanyDetailsRespDto;
import com.personal.demo.response.store.wrapper.StoreWrapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/8/12 11:55
 */
@Mapper
public interface StoreConverter {

    StoreConverter INSTANCE = Mappers.getMapper(StoreConverter.class);

    StoreWrapper convert(DemoStoreCompanyDetailsRespDto respDto);
}
