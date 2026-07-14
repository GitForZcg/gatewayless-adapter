package com.personal.demo.utils;


import static com.personal.demo.utils.StringCryptoUtil.generateSecureChallenge;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 字符串加密工具类
 * @date 2025/7/17 09:36
 */
public class StringDecryptUtil {

    /**
     * 验证字符串对
     *
     * @param pair 字符串对
     * @return 验证结果
     */
    public static boolean verifySecureStringPair(SecureStringPair pair) {
        if (pair == null) {
            return false;
        }

        // 1. 检查是否过期
        if (pair.isExpired()) {
            return false;
        }

        try {
            // 2. 重新生成Challenge并比较
            String expectedChallenge = generateSecureChallenge(pair.verifier(), pair.salt());

            // 3. 使用时间安全的比较方法（防时间攻击）
            return constantTimeEquals(pair.challenge(), expectedChallenge);

        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 时间安全的字符串比较（防时间攻击）
     */
    private static boolean constantTimeEquals(String a, String b) {
        if (a == null || b == null || a.length() != b.length()) {
            return false;
        }
        int result = 0;
        for (int i = 0; i < a.length(); i++) {
            result |= a.charAt(i) ^ b.charAt(i);
        }
        return result == 0;
    }


}