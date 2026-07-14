package com.personal.demo.utils;

import com.personal.demo.conf.GsonFactory;
import com.personal.demo.consts.Separator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;
import java.util.*;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: api参数组装工具
 * @date 2025/7/8 11:00
 */
@Slf4j
public class ApiParamUtil {

    private static final Gson gsonWithNulls;
    private static final Gson gson;

    static {
        gsonWithNulls = new GsonBuilder().serializeNulls().create();
        gson = GsonFactory.getInstance();
    }

    public static <T> String buildSignContent(T obj, Type type, boolean includeNulls) {
        Gson selectedGson = includeNulls ? gsonWithNulls : gson;
        Map<String, String> sortedParams = selectedGson.fromJson(selectedGson.toJson(obj), type);
        try {
            List<String> keyList = new ArrayList<>(sortedParams.keySet());
            Collections.sort(keyList);
            boolean started = false;
            StringBuilder signParams = new StringBuilder();
            for (String key : keyList) {
                if (started) {
                    signParams.append("&");
                }
                started = true;

                String value = sortedParams.get(key);
                // 空值也拼接，用空字符串表示
                signParams.append(key).append("=").append(value == null ? "" : value);
            }
            return signParams.toString();
        } catch (Exception e) {
            log.warn(String.format("getSignContent Exception: %s", e));
            throw e;
        }
    }

    private static StringBuffer doSignStrAppend(Map<String, String> map, Set<String> needSignParams) {
        StringBuffer sb = new StringBuffer();
        for (String key : map.keySet()) {
            if (needSignParams.contains(key)) {
                // do sign append
                String value = map.get(key);
                value = (value == null ? "" : value);
                sb.append(Separator.SPLIT).append(value);
            }
        }
        return sb;
    }

    /**
     * 处理参数并构造查询字符串
     *
     * @param params 原始参数Map
     * @return 查询字符串
     */
    public static String buildQueryString(Map<String, Object> params, String secret) {
        if (params == null || params.isEmpty()) {
            return "";
        }
        // 按key排序并转换值
        List<String> keys = new ArrayList<>(params.keySet());
        Collections.sort(keys);

        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (String key : keys) {
            Object value = params.get(key);
            if (value == null) {
                continue;
            }
            if (!first) {
                result.append("&");
            }
            first = false;
            result.append(key).append("=").append(convertValue(value));
        }

        return result.append(secret).toString();
    }

    /**
     * 转换参数值
     */
    private static String convertValue(Object value) {
        if (value instanceof String || value instanceof Number || value instanceof Boolean) {
            return value.toString();
        }
        return GsonFactory.getInstance().toJson(value);
    }

}
