package com.personal.demo.pojo.dto;

import com.personal.demo.pojo.base.DemoStoreMd5Param;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Set;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/8/12 09:59
 */
@Data
@Accessors(chain = true)
public class DemoStoreCompanyDetailsRespDto implements DemoStoreMd5Param {

    private List<StoreCompanyDetailRespDto> result;
    private int pageSize;
    private int pageNumber;
    private long totalCount;

    @Override
    public Set<String> needSign() {
        return null;
    }

    @Override
    public Set<String> needSignParam() {
        return null;
    }

    @Override
    public String orderId() {
        return null;
    }
}
