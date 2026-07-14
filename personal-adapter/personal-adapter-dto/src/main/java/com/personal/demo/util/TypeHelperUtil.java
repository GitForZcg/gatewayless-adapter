package com.personal.demo.util;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: Gson type 工具类
 * @date 2025/7/4 10:41
 */
public class TypeHelperUtil {
    // 缓存避免重复创建
    private static final Map<String, Type> TYPE_CACHE = new ConcurrentHashMap<>();

    /**
     * 获取泛型类型
     */
    public static Type getType(Class<?> rawType, Class<?>... typeArguments) {
        String key = rawType.getName() + Arrays.toString(typeArguments);
        return TYPE_CACHE.computeIfAbsent(key, k -> {
            if (typeArguments.length == 0) {
                return rawType;
            }
            return TypeToken.getParameterized(rawType, typeArguments).getType();
        });
    }

    /**
     * 获取List<T>类型
     */
    public static Type listOf(Class<?> elementType) {
        return getType(List.class, elementType);
    }

    /**
     * 获取包装类型，如EsResultWrap<T>
     */
    public static Type wrapOf(Class<?> wrapperType, Class<?> contentType) {
        return getType(wrapperType, contentType);
    }

    /**
     * 获取Map<K,V>类型
     */
    public static Type mapOf(Class<?> keyType, Class<?> valueType) {
        return getType(Map.class, keyType, valueType);
    }
}
