package com.personal.demo.serivce;

import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.pojo.base.DemoComputeMd5Param;

import java.util.List;
import java.util.Map;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 抽象门店MD5处理
 * @date 2025/7/24 13:37
 */

public interface AbstractComputeMD5Service {

    <T extends DemoComputeMd5Param> Map<String, Object> executeNoAccess(T reqDto, BaseNode node);
    
    <T extends DemoComputeMd5Param> T executeResult(Map<String, Object> map, Class<T> clazz);
    <T extends DemoComputeMd5Param> List<T> executeResultList(Map<String, Object> map, Class<T> clazz);

    <T extends DemoComputeMd5Param> Boolean executeResultBoolean(Map<String, Object> map);
    
}
