package com.personal.demo.request.store;

import com.personal.demo.request.group.ValidationGroups;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TestStoreParams {


    @NotEmpty(message = "分页参数pageNumber不能为空", groups = ValidationGroups.storeGroup.class)
    private String pageNumber;


    @NotEmpty(message = "分页参数pageSize不能为空", groups = ValidationGroups.storeGroup.class)
    private String pageSize;
}
