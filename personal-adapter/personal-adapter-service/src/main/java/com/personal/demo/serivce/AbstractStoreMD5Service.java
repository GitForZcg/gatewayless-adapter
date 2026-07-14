package com.personal.demo.serivce;

import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.pojo.base.DemoStoreMd5Param;

import java.util.Map;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 抽象门店MD5处理
 * @date 2025/7/24 13:37
 */

public interface AbstractStoreMD5Service {

    <T extends DemoStoreMd5Param> Map<String, Object> execute(T reqDto, BaseNode node);
    
    <T extends DemoStoreMd5Param> T executeResult(Map<String, Object> map, Class<T> clazz);
    
}
