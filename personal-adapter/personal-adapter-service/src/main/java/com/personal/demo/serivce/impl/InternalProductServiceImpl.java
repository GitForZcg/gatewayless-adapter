package com.personal.demo.serivce.impl;

import com.personal.demo.adapter.external.product.convert.ProductConvert;
import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.pojo.dto.ProdNotificationReqDto;
import com.personal.demo.pojo.dto.request.product.ExternalProductCommonRequest;
import com.personal.demo.pojo.dto.response.product.ProdNotificationRespDto;
import com.personal.demo.request.product.ProductNotificationParam;
import com.personal.demo.serivce.AbstractSM2Service;
import com.personal.demo.serivce.IInternalProductService;
import com.personal.product.external.IPersonalProductExternalApi;
import com.personal.product.external.dto.*;
import com.personal.product.external.request.ExternalParam;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 调用产品服务的实现类
 *
 * @Author:
 */
@Service("productServiceImpl")
public class InternalProductServiceImpl implements IInternalProductService {




    private final AbstractSM2Service abstractSM2Service;


    public InternalProductServiceImpl(AbstractSM2Service abstractSM2Service) {

        this.abstractSM2Service = abstractSM2Service;
    }


    @Override
    public Object notification(ProductNotificationParam bizData, BaseNode node) {

        ProdNotificationReqDto prodNotificationDto = ProductConvert.INSTANCE.prodNotificationDtoConvert(bizData);

        Map<String, Object> dataMap = abstractSM2Service.executeSign(prodNotificationDto, node);

        ProdNotificationRespDto respDto = abstractSM2Service.executeResult(dataMap, node, ProdNotificationRespDto.class);

        return respDto.getCode() == 200 ? Boolean.TRUE : Boolean.FALSE;
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
