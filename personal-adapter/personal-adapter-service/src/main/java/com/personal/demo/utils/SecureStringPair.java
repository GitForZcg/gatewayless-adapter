package com.personal.demo.utils;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 安全字符串对
 * @date 2025/7/17 13:52
 */
public record SecureStringPair(String challenge, String verifier, String salt,  long expireTime) {

    /**
     * 检查是否过期
     */
    public boolean isExpired() {
        return System.currentTimeMillis() > expireTime;
    }

    /**
     * 获取剩余有效时间（秒）
     */
    public long getRemainingSeconds() {
        long remaining = (expireTime - System.currentTimeMillis()) / 1000;
        return Math.max(0, remaining);
    }

    /**
     * 获取过期时间的格式化字符串
     */
    public String getExpireTimeString() {
        LocalDateTime expireDateTime = LocalDateTime.ofInstant(
                java.time.Instant.ofEpochMilli(expireTime),
                ZoneId.systemDefault()
        );
        return expireDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    public String toString() {
        return String.format(
                """
                        SecureStringPair{
                          challenge='%s' (长度:%d)
                          verifier='%s' (长度:%d)
                          salt='%s' (长度:%d)
                          expireTime=%d (%s)
                          expired=%s
                          remainingSeconds=%d
                        }""",
                challenge, challenge.length(),
                verifier, verifier.length(),
                salt, salt.length(),
                expireTime, getExpireTimeString(),
                isExpired(),
                getRemainingSeconds()
        );
    }
}