package com.personal.demo.serivce;

import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.pojo.base.VendorBaseResponse;
import com.personal.demo.pojo.base.VendorPublicParam;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/9/26 09:50
 */
public interface AbstractVendorAuthService {

    Map<String, Object> executeAuthParam(BaseNode node);

    VendorBaseResponse<?> executeResult(Map<String, Object> authRespMap, String requestJson, BaseNode node);
}
