package com.personal.demo.serivce;

import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.pojo.base.BaseMemberPublicParam;
import com.personal.demo.pojo.base.BaseTradePublicParam;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface AbstractMemberMD5Service {

    <T extends BaseMemberPublicParam> LinkedHashMap<String, Object> executeSign(T reqDto, BaseNode node);

    <R> R executeResult(LinkedHashMap<String, Object> dataMap, BaseNode node, Class<R> clazz);
    <R> R executeResult(LinkedHashMap<String, Object> dataMap, BaseNode node, Type type);

    <R> List<R> executeResultList(LinkedHashMap<String, Object> dataMap, BaseNode node, Class<R> clazz);
    Map<String, Object> executeResultMap(LinkedHashMap<String, Object> dataMap, BaseNode node);

    <T extends BaseTradePublicParam> LinkedHashMap<String, Object> executeSign(T reqDto, BaseNode node);
}
