package com.personal.demo.api;


import com.personal.demo.request.base.BaseUnifiedRequest;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@FeignClient(name = "personal-adapter", path = "/personal-adapter/adapter", contextId = "adapter")
public interface IAdapterUnifiedApi {

    @PostMapping("/execute")
    Object executePFlow(@RequestBody @Valid BaseUnifiedRequest request) throws Exception;

}
