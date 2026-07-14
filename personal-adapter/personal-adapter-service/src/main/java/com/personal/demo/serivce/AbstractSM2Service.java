package com.personal.demo.serivce;

import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.pojo.base.BaseSM2PublicParam;

import java.util.Map;

/**
 * @author zhangjunyi
 * @description: 抽象统一sm2验签服务
 * @Date: 2025/8/13 15:17
 */
public interface AbstractSM2Service {

    <T extends BaseSM2PublicParam> Map<String, Object> executeSign(T requDto, BaseNode node);


    <T extends BaseSM2PublicParam> T executeResult(Map<String, Object> signResult, BaseNode node, Class<T> resClazz);


}
