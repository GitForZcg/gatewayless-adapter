package com.personal.demo.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/9/19 14:59
 */
public class DateTimeUtil {

    private static final String[] INPUT_PATTERNS = {
            "yyyy-MM-dd'T'HH:mm:ss.SSSXXX",
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            "yyyy-MM-dd'T'HH:mm:ssXXX",
            "yyyy-MM-dd'T'HH:mm:ss'Z'"
    };

    private static final String OUTPUT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 将ISO时间格式转换为标准格式
     * @param isoDateString 如: 2025-09-16T08:12:35.000+00:00
     * @return 如: 2025-09-16 08:12:35
     */
    public static String formatToStandard(String isoDateString) {
        if (isoDateString == null || isoDateString.isEmpty()) {
            return null;
        }

        for (String pattern : INPUT_PATTERNS) {
            try {
                DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern(pattern);
                DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern(OUTPUT_PATTERN);

                if (isoDateString.contains("+") || isoDateString.endsWith("Z")) {
                    ZonedDateTime zonedDateTime = ZonedDateTime.parse(isoDateString, inputFormatter);
                    LocalDateTime localDateTime = zonedDateTime.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
                    return localDateTime.format(outputFormatter);
                } else {
                    LocalDateTime localDateTime = LocalDateTime.parse(isoDateString, inputFormatter);
                    return localDateTime.format(outputFormatter);
                }
            } catch (DateTimeParseException e) {
                // 继续尝试下一个格式
            }
        }

        throw new IllegalArgumentException("无法解析日期格式: " + isoDateString);
    }
}
