package com.personal.demo.serivce;

import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.pojo.base.BasePayPublicParams;
import com.personal.demo.pojo.base.BasePayRequest;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/11/6 10:39
 */
public interface AbstractPayMD5Service {

    <T extends BasePayPublicParams> BasePayRequest executeSign(T reqDto, BaseNode node, String baid);

    <T extends BasePayPublicParams> T execute(BasePayRequest requestContent, BaseNode node, String orderId, Class<T> clazz);

}
