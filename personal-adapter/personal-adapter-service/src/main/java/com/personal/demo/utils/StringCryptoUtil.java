package com.personal.demo.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/7/17 14:00
 */
public class StringCryptoUtil {

    // 配置常量
    private static final String CHARSET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int CHALLENGE_LENGTH = 32;     // Challenge固定长度
    private static final int ITERATION_COUNT = 10000;   // PBKDF2迭代次数

    /**
     * 使用PBKDF2生成安全的Challenge（固定32字符）
     */
    public static String generateSecureChallenge(String verifier, String salt) {
        try {
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");

            // 1. 多轮哈希增加计算复杂度（PBKDF2思想）
            byte[] data = (verifier + salt).getBytes(StandardCharsets.UTF_8);

            for (int i = 0; i < ITERATION_COUNT; i++) {
                data = sha256.digest(data);
            }

            // 2. 扩展数据确保有足够随机性生成32字符
            StringBuilder expandedData = new StringBuilder();
            int cycles = (CHALLENGE_LENGTH / 32) + 1; // 每32字节需要一轮SHA-256

            byte[] currentData = data;
            for (int cycle = 0; cycle < cycles; cycle++) {
                currentData = sha256.digest(currentData);
                expandedData.append(Base64.getEncoder().encodeToString(currentData));
            }

            // 3. 转换为可读字符串，确保长度为32
            StringBuilder challenge = new StringBuilder();
            String expandedStr = expandedData.toString();

            for (int i = 0; i < CHALLENGE_LENGTH; i++) {
                // 使用扩展数据的字节值来选择字符
                int byteValue = expandedStr.getBytes(StandardCharsets.UTF_8)[i % expandedStr.getBytes(StandardCharsets.UTF_8).length] & 0xFF;
                int index = byteValue % CHARSET.length();
                challenge.append(CHARSET.charAt(index));
            }

            return challenge.toString();

        } catch (Exception e) {
            throw new RuntimeException("生成Challenge失败", e);
        }
    }
}
