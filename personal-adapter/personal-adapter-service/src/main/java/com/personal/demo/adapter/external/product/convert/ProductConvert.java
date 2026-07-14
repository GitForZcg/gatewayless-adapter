package com.personal.demo.adapter.external.product.convert;

import com.personal.demo.pojo.dto.ProdNotificationReqDto;
import com.personal.demo.pojo.dto.response.product.*;
import com.personal.demo.request.product.ProductNotificationParam;
import com.personal.product.external.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 类转换
 *
 * @Author: fxs
 * @Date: 2025/8/13 10:51
 */
@Mapper(componentModel = "spring")
public interface ProductConvert {

    ProductConvert INSTANCE = Mappers.getMapper(ProductConvert.class);


    /**
     * pluAllDtoList 转换为 PluAllResponse
     *
     * @param pluAllDtoList
     * @return
     */
    PluAllResponse pluAllConvert(PluAllDto pluAllDtoList);

    /**
     * pluAllDtoList 列表转换为 PluAllResponse
     *
     * @param pluAllDtoList
     * @return
     */
    List<PluAllResponse> pluAllListConvert(List<PluAllDto> pluAllDtoList);

    /**
     * pluSpecDtoList 列表转换为 PluSpecResponse
     *
     * @param pluSpecDto
     * @return
     */
    PluSpecResponse pluSpecDtoConvert(PluSpecDto pluSpecDto);

    /**
     * pluSpecDtoList 列表转换为 PluSpecResponse
     *
     * @param pluSpecDtoList
     * @return
     */
    List<PluSpecResponse> pluSpecDtoListConvert(List<PluSpecDto> pluSpecDtoList);

    /**
     * pluComboDtoList 列表转换为 PluComboResponse
     *
     * @param pluComboDto
     * @return
     */
    PluComboResponse pluComboDetailDtoConvert(PluComboDto pluComboDto);

    /**
     * pluComboDtoList 列表转换为 PluComboResponse
     *
     * @param pluComboDtoList
     * @return
     */
    List<PluComboResponse> pluComboDetailDtoListConvert(List<PluComboDto> pluComboDtoList);

    /**
     * pluMasterAllDtoList 列表转换为 PluMasterAllResponse
     *
     * @param masterAllDto
     * @return
     */
    PluMasterAllResponse masterAllDtoConvert(PluMasterAllDto masterAllDto);

    /**
     * pluMasterAllDtoList 列表转换为 PluMasterAllResponse
     *
     * @param masterAllDtoList
     * @return
     */
    List<PluMasterAllResponse> masterAllDtoListConvert(List<PluMasterAllDto> masterAllDtoList);


    /**
     * pluMenuDataDtoList 列表转换为 PluMenuDataResponse
     *
     * @param menuDataDto
     * @return
     */
    PluMenuDataResponse menuDataDtoConvert(PluMenuDataDto menuDataDto);

    /**
     * pluMenuDataDtoList 列表转换为 PluMenuDataResponse
     *
     * @param menuDataDtoList
     * @return
     */
    List<PluMenuDataResponse> menuDataDtoListConvert(List<PluMenuDataDto> menuDataDtoList);

    /**
     * pluMenuDataDtoList 列表转换为 PluMenuDataResponse
     *
     * @param notificationDto
     * @return
     */
    ProdNotificationReqDto prodNotificationDtoConvert(ProductNotificationParam notificationDto);

    /**
     * pluMenuDataDtoList 列表转换为 PluMenuDataResponse
     *
     * @param scheduleDtoList
     * @return
     */
    StoreScheduleResponse dailySummaryDtoConvert(StoreScheduleDto scheduleDtoList);

    /**
     * pluMenuDataDtoList 列表转换为 PluMenuDataResponse
     *
     * @param scheduleDtoList
     * @return
     */
    List<StoreScheduleResponse> dailySummaryDtoListConvert(List<StoreScheduleDto> scheduleDtoList);
}
