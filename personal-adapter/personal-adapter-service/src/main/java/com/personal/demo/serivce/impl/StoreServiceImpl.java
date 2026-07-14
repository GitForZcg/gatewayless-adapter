package com.personal.demo.serivce.impl;

import com.personal.demo.adapter.internal.store.convert.StoreConverter;
import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.pojo.dto.DemoStoreCompanyDetailReqDto;
import com.personal.demo.pojo.dto.DemoStoreCompanyDetailsRespDto;
import com.personal.demo.request.store.TestStoreParams;
import com.personal.demo.serivce.AbstractStoreMD5Service;
import com.personal.demo.serivce.IStoreService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/8/12 09:48
 */
@Service
public class StoreServiceImpl implements IStoreService {

    private final AbstractStoreMD5Service md5Service;

    public StoreServiceImpl(AbstractStoreMD5Service md5Service) {
        this.md5Service = md5Service;
    }

    @Override
    public Object storeCompanyDetails(TestStoreParams params, BaseNode node) {

        DemoStoreCompanyDetailReqDto reqDto = new DemoStoreCompanyDetailReqDto()
                .setPageNumber(params.getPageNumber())
                .setPageSize(params.getPageSize());
        Map<String, Object> dataMap = md5Service.execute(reqDto, node);
        DemoStoreCompanyDetailsRespDto respDto = md5Service.executeResult(dataMap, DemoStoreCompanyDetailsRespDto.class);
        return StoreConverter.INSTANCE.convert(respDto);
    }
}
