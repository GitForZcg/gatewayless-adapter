package com.personal.demo.controller;

import com.personal.demo.api.IAdapterUnifiedApi;
import com.personal.demo.request.base.BaseUnifiedRequest;
import com.personal.demo.rule.factory.BaseFlowManagerFactory;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 适配器统一接口
 * @date 2025/7/2 10:47
 */
@RestController
@RequestMapping("/adapter")
public class AdapterUnifiedController implements IAdapterUnifiedApi {

    private final BaseFlowManagerFactory flowManagerFactory;

    @Autowired
    public AdapterUnifiedController(BaseFlowManagerFactory paymentFlowManager) {
        this.flowManagerFactory = paymentFlowManager;
    }

    @Override
    @PostMapping("/execute")
    public Object executePFlow(@RequestBody @Valid BaseUnifiedRequest request) throws Exception {
        return flowManagerFactory.executeFlow(
                request.getType(), request.getNode(), request.getParams()
        );

    }
}
