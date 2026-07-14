package com.personal.demo.serivce.impl;

import com.personal.demo.adapter.external.product.convert.ProductConvert;
import com.personal.demo.conf.ScheduleProperties;
import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.pojo.dto.request.product.ExternalProductCommonRequest;
import com.personal.demo.pojo.dto.request.product.ExternalProductDetailRequest;
import com.personal.demo.pojo.dto.response.product.*;
import com.personal.demo.request.product.ProductNotificationParam;
import com.personal.demo.serivce.IExternalProductService;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.personal.product.external.IPersonalProductExternalApi;
import com.personal.product.external.dto.*;
import com.personal.product.external.request.ExternalParam;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 调用产品服务的实现类
 *
 * @Author:
 */
@Service
public class ExternalProductServiceImpl implements IExternalProductService {


    private final IPersonalProductExternalApi externalApi;

    @Resource
    private ScheduleProperties scheduleProperties;

    public ExternalProductServiceImpl(IPersonalProductExternalApi externalApi) {
        this.externalApi = externalApi;
    }

    @Override
    public Boolean pluSyncPreheat(ExternalProductDetailRequest request) {

        ExternalParam param = getExternalParam(request);

        return externalApi.pluSyncPreheat(param);
    }

    @Override
    public List<PluAllResponse> pluAll(ExternalProductDetailRequest request) {

        ExternalParam param = getExternalParam(request);

        List<PluAllDto> pluAllDtoList = externalApi.pluAll(param);

        return ProductConvert.INSTANCE.pluAllListConvert(pluAllDtoList);
    }


    @Override
    public List<PluSpecResponse> pluCondiment(ExternalProductDetailRequest request) {

        ExternalParam param = getExternalParam(request);

        List<PluSpecDto> pluSpecDtoList = externalApi.pluCondiment(param);

        return ProductConvert.INSTANCE.pluSpecDtoListConvert(pluSpecDtoList);
    }

    @Override
    public List<PluComboResponse> getComboDetail(ExternalProductDetailRequest request) {

        ExternalParam param = getExternalParam(request);

        List<PluComboDto> pluComboDtoList = externalApi.getComboDetail(param);

        return ProductConvert.INSTANCE.pluComboDetailDtoListConvert(pluComboDtoList);
    }


    @Override
    public List<PluMasterAllResponse> pluMasterAll(ExternalProductCommonRequest request) {

        ExternalParam param = createExternalParam(request);

        List<PluMasterAllDto> masterAllDtoList = externalApi.pluMasterAll(param);

        return ProductConvert.INSTANCE.masterAllDtoListConvert(masterAllDtoList);
    }

    @Override
    public List<PluMenuDataResponse> getMenuSettings(ExternalProductCommonRequest request) {

        ExternalParam param = createExternalParam(request);

        List<PluMenuDataDto> menuDataDtoList = externalApi.getMenuSettings(param);

        return ProductConvert.INSTANCE.menuDataDtoListConvert(menuDataDtoList);
    }

    @Override
    public List<StoreScheduleResponse> getDailySummary(ExternalProductCommonRequest request) {

        ExternalParam param = createExternalParam(request);

        List<StoreScheduleDto> scheduleDtoList = externalApi.getStoreSchedule(param);

        List<StoreScheduleResponse> scheduleResponses = ProductConvert.INSTANCE.dailySummaryDtoListConvert(scheduleDtoList);

        if (CollectionUtils.isNotEmpty(scheduleResponses)) {
            scheduleResponses.forEach(storeSchedule -> {

                if (ObjectUtils.isNotEmpty(scheduleProperties.getDayCarryover())) {
                    if (StringUtils.isNotBlank(scheduleProperties.getDayCarryover().getIsDay())) {
                        storeSchedule.getDayCarryover().setIsDay(scheduleProperties.getDayCarryover().getIsDay());
                    }

                    if (ObjectUtils.isNotEmpty(scheduleProperties.getDayCarryover().getDailyType())) {
                        storeSchedule.getDayCarryover().setDailyType(scheduleProperties.getDayCarryover().getDailyType());
                    }

                }

                if (ObjectUtils.isNotEmpty(scheduleProperties.getPeriodSchemeTime())) {
                    if (ObjectUtils.isNotEmpty(scheduleProperties.getPeriodSchemeTime().getIsDay())) {
                        storeSchedule.getPeriodSchemeTime().setIsDay(scheduleProperties.getPeriodSchemeTime().getIsDay());
                    }

                    if (ObjectUtils.isNotEmpty(scheduleProperties.getPeriodSchemeTime().getDailyType())) {
                        storeSchedule.getPeriodSchemeTime().setDailyType(scheduleProperties.getPeriodSchemeTime().getDailyType());
                    }
                }

            });
        }


        return scheduleResponses;
    }


    /**
     * 根据外部产品详情请求创建并配置外部参数对象
     *
     * @param request 外部产品详情请求对象，包含产品相关信息
     * @return 配置完成的外部参数对象
     */
    private static ExternalParam getExternalParam(ExternalProductDetailRequest request) {

        ExternalParam param = new ExternalParam();

        param.setMid(request.getMid());

        param.setBid(request.getBid());

        param.setBatchNumber(request.getBatchNumber());
        // 处理子类特有的字段
        param.setSid(request.getSid());

        param.setSortCode(request.getSortCode());
        return param;
    }


    /**
     * 创建外部参数对象
     *
     * @param request 外部产品通用请求对象，用于提取参数信息
     * @return ExternalParam 外部参数对象，包含从请求中提取的mid、bid和批次号信息
     */
    private static ExternalParam createExternalParam(ExternalProductCommonRequest request) {
        ExternalParam param = new ExternalParam();
        param.setMid(request.getMid());
        param.setBid(request.getBid());
        param.setBatchNumber(request.getBatchNumber());
        return param;
    }


}
