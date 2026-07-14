package com.personal.demo.serivce;

import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.request.store.TestStoreParams;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/8/12 09:48
 */
public interface IStoreService {

    Object  storeCompanyDetails(TestStoreParams params, BaseNode node);
}
