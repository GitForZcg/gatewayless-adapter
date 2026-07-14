package com.personal.demo.adapter.external.product;

import com.personal.demo.enu.external.ExternalProductNode;
import com.personal.demo.enu.external.base.ExternalBaseNode;
import com.personal.demo.pojo.dto.request.product.ExternalProductCommonRequest;
import com.personal.demo.pojo.dto.request.product.ExternalProductDetailRequest;
import com.personal.demo.rule.handler.AbstractExternalFlowProcessor;
import com.personal.demo.serivce.IExternalProductService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Map;


/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 外部产品执行流程
 * @date 2025/7/4 10:42
 */
@Component
public class ExternalProductFlow extends AbstractExternalFlowProcessor<Object> implements ExternalProductFlowProcessor {

    @Resource
    private IExternalProductService service;


    @Override
    public String getProcessorType() {
        return "PRODUCT";
    }

    @Override
    protected Map<ExternalBaseNode, BaseNodeFunction<Object>> initNodeHandlers() {
        return Map.of(
                ExternalProductNode.PRODUCT_PREHEAT, this::pluSyncPreheat,
                ExternalProductNode.PRODUCT_ALL, this::pluAll,
                ExternalProductNode.PRODUCT_CONDIMENT, this::pluCondiment,
                ExternalProductNode.PRODUCT_COMBO, this::getComboDetail,
                ExternalProductNode.PRODUCT_MASTER_ALL, this::pluMasterAll,
                ExternalProductNode.PRODUCT_MENU, this::getMenuSettings,
                ExternalProductNode.PRODUCT_DAILY, this::getDailySummary
        );
    }

    @Override
    public Object pluSyncPreheat(Object params) {
        return service.pluSyncPreheat((ExternalProductDetailRequest) params);
    }

    @Override
    public Object pluAll(Object params) {
        return service.pluAll((ExternalProductDetailRequest) params);
    }

    @Override
    public Object pluCondiment(Object params) {
        return service.pluCondiment((ExternalProductDetailRequest) params);
    }

    @Override
    public Object getComboDetail(Object params) {
        return service.getComboDetail((ExternalProductDetailRequest) params);
    }

    @Override
    public Object pluMasterAll(Object params) {
        return service.pluMasterAll((ExternalProductCommonRequest) params);

    }

    @Override
    public Object getMenuSettings(Object params) {
        return service.getMenuSettings((ExternalProductCommonRequest) params);

    }

    @Override
    public Object getDailySummary(Object params) {
        return service.getDailySummary((ExternalProductCommonRequest) params);

    }

}
