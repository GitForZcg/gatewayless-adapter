package com.personal.demo.serivce.member.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * @author Li QianQian
 * describe:
 */
public class DateUtil {

    /**
     * 使用Java 8的DateTimeFormatter转换日期格式
     */
    public static String convertDateTimeFormatJava8(String dateTimeStr) {
        if (dateTimeStr == null || dateTimeStr.trim().isEmpty()) {
            return dateTimeStr;
        }

        try {
            // 定义输入格式
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            // 解析字符串为LocalDateTime对象
            LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, inputFormatter);

            // 定义输出格式
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy年M月d日 HH:mm:ss");

            // 格式化输出
            return dateTime.format(outputFormatter);
        } catch (DateTimeParseException e) {
            // 如果解析失败，返回原字符串
            System.err.println("日期格式转换失败: " + dateTimeStr);
            return dateTimeStr;
        }
    }
}

