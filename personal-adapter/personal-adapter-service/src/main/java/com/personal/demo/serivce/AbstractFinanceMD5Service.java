package com.personal.demo.serivce;

import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.pojo.base.DemoFinanceMd5Param;

import java.util.List;
import java.util.Map;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 抽象财务MD5处理
 * @date 2025/7/24 13:37
 */

public interface AbstractFinanceMD5Service {

    <T extends DemoFinanceMd5Param> Map<String, Object> execute(T reqDto, BaseNode node);

    <T extends DemoFinanceMd5Param> Map<String, Object> execute(List<T> reqDto, BaseNode node);

    <T extends DemoFinanceMd5Param> T executeResult(Map<String, Object> map, Class<T> clazz);

    <T extends DemoFinanceMd5Param> List<T> checkImportVoucherExecuteResult(Map<String, Object> map, Class<T> clazz);

}
