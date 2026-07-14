package com.personal.demo.adapter.internal.store;

import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.enu.internal.node.StoreNode;
import com.personal.demo.request.base.BaseParams;
import com.personal.demo.request.store.TestStoreParams;
import com.personal.demo.rule.handler.AbstractFlowProcessor;
import com.personal.demo.serivce.IStoreService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 门店执行流程
 * @date 2025/7/2 10:44
 */
@SuppressWarnings("rawtypes")
@Component
public class StoreFlow extends AbstractFlowProcessor<BaseParams> implements StoreFlowProcessor {

    @Resource
    IStoreService storeService;

    @Override
    public String getProcessorType() {
        return "STORE";
    }

    @Override
    protected Map<BaseNode, BaseNodeFunction<BaseParams>> initNodeHandlers() {
        return Map.of(
                StoreNode.STORE_COMPANY_QUERY, this::companyDetails
        );
    }

    @Override
    public Object companyDetails(BaseParams params, BaseNode node) throws Exception {
        return storeService.storeCompanyDetails((TestStoreParams) params.getBizData(), node);
    }

}
