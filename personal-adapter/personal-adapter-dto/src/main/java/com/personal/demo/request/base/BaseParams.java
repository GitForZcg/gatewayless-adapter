package com.personal.demo.request.base;

import com.personal.demo.request.base.AbstractBaseParams;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/7/2 11:13
 */
@Data
@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
public class BaseParams<T> extends AbstractBaseParams {

    private T bizData;

    @Override
    public Object getBizData() {
        return this.bizData;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setBizData(Object bizData) {
        this.bizData = (T) bizData;
    }
}
