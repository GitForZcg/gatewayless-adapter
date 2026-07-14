package com.personal.demo.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

/**
 * @author sulu
 * @date 2025年09月16日 1:49 PM
 */
@Configuration
@Slf4j
public class SpecialCharacterDetector {
    // 正则：匹配特殊字符（非字母、数字、中文、常用标点）
    private static final Pattern FILTER_PATTERN = Pattern.compile(
            "[^\\u4e00-\\u9fa5a-zA-Z0-9,，.!！？?…]"
    );

    /**
     * 过滤字符串，只保留中文、英文、数字和简单标点符号
     * @param str 待过滤的字符串
     * @return 过滤后的字符串（null或空字符串返回原内容）
     */
    public static String keepSpecificChars(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        // 将所有不符合条件的字符替换为空
        return FILTER_PATTERN.matcher(str).replaceAll("");
    }


    public String  washText(String str) {
        String filtered = keepSpecificChars(str);
        if (StringUtils.isBlank(filtered)) {
            return "";
        }
        return filtered;
    }
    public String  washName(String str) {
        String filtered = keepSpecificChars(str);
        if (StringUtils.isBlank(filtered)) {
            return "小喏";
        }
        return filtered;
    }
}
