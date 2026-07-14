package com.personal.demo.response.store.wrapper;

import com.personal.demo.response.store.StoreCompanyResultDto;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Collections;
import java.util.List;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/8/12 10:24
 */
@Data
@Accessors(chain = true)
public class StoreWrapper {
    private List<StoreCompanyResultDto> result;
    private int pageSize;
    private int pageNumber;
    private long totalCount;

    public static StoreWrapper of() {
        return new StoreWrapper();
    }

    public static StoreWrapper of(List<StoreCompanyResultDto> result, int pageSize, int pageNumber, long totalCount) {
        return new StoreWrapper()
                .setResult(result)
                .setPageSize(pageSize)
                .setPageNumber(pageNumber)
                .setTotalCount(totalCount);
    }

    public static StoreWrapper emptyList() {
        return new StoreWrapper()
                .setResult(Collections.emptyList())
                .setPageSize(0)
                .setPageNumber(0)
                .setTotalCount(0L);
    }
}
