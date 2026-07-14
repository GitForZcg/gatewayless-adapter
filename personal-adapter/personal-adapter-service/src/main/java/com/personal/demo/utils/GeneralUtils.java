package com.personal.demo.utils;

import java.util.*;

public class GeneralUtils {

    /**
     * 获取签名串
     */
    public static String getSignContent(Map<String, String> params) {
        StringBuilder content = new StringBuilder();
        List<String> keys = new ArrayList<>(params.keySet());
        Collections.sort(keys);
        int index = 0;
        for (String key : keys) {
            String value = params.get(key);
            if (null != key && null != value) {
                content.append(index == 0 ? "" : "&").append(key).append("=").append(value);
                index++;
            }
        }
        return content.toString();
    }

    /**
     * 随机字符串
     */
    public static String randomStr(int length) {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, length);
    }

}
