package com.personal.demo.consts;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/7/8 10:47
 */
public interface Separator {

    // 通用分隔符
    String COMMA = ",";              // 逗号
    String DOT = ".";               // 点
    String COLON = ":";             // 冒号
    String SEMICOLON = ";";         // 分号
    String UNDERSCORE = "_";        // 下划线
    String DASH = "-";              // 中划线
    String SLASH = "/";            // 斜杠
    String BACKSLASH = "\\";       // 反斜杠

    // 特殊分隔符
    String PIPE = "|";              // 竖线
    String TAB = "\t";             // 制表符
    String NEWLINE = "\n";         // 换行符
    String CARRIAGE_RETURN = "\r"; // 回车符

    // 括号和引号
    String LEFT_BRACKET = "[";      // 左方括号
    String RIGHT_BRACKET = "]";     // 右方括号
    String LEFT_BRACE = "{";        // 左大括号
    String RIGHT_BRACE = "}";       // 右大括号
    String LEFT_PAREN = "(";        // 左圆括号
    String RIGHT_PAREN = ")";       // 右圆括号
    String SINGLE_QUOTE = "'";      // 单引号
    String DOUBLE_QUOTE = "\"";   // 双引号

    // 空白分隔符
    String SPACE = " ";             // 空格
    String NONE = "";             // 空白
    String SPLIT = "&";             // 空白
    String SPLIT_SPECIAL_MARK = "#";             // 空白
}
