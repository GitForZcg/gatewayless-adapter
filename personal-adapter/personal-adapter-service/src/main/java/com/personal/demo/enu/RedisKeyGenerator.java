package com.personal.demo.enu;


import com.personal.demo.consts.Separator;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: redisKey统一规则生成器
 * @date 2025/7/8 10:47
 */
public interface RedisKeyGenerator {
    String separator = Separator.UNDERSCORE;

    /**
     * 返回 Redis Key 的模板
     *
     * @return Redis Key 模板
     */
    String getPrefix();

    /**
     * 默认实现：根据动态参数生成完整的 Redis Key
     *
     * @param uniqTag 动态参数
     * @return 拼接后的 Redis Key
     */
    default String generateKey(Object... uniqTag) {
        if (uniqTag == null || uniqTag.length == 0) {
            return getPrefix(); // 如果没有参数，返回模板本身
        }
        return getPrefix() + Separator.COLON + String.join(separator, toStringArray(uniqTag)); // 使用 "_" 连接参数
    }

    /**
     * 根据前缀和动态参数生成完整的 Redis Key。
     *
     * @param segments 自定义前缀组合
     * @param uniqTag  动态参数
     * @return 拼接后的 Redis Key
     */
    static String generateDynamicKey(String[] segments, Object... uniqTag) {
        if (segments == null || segments.length == 0) {
            throw new IllegalArgumentException("Segments must not be null or empty");
        }
        // 拼接多级前缀
        StringBuilder key = new StringBuilder(String.join(Separator.COLON, segments));

        if (!key.toString().endsWith(Separator.COLON)) {
            key.append(Separator.COLON); // 确保前缀以冒号结尾
        }
        // 拼接动态参数（如果有）
        if (uniqTag != null && uniqTag.length > 0) {
            key.append(String.join(separator, toStringArray(uniqTag)));
        }

        return key.toString();
    }


    /**
     * 默认实现：将 Object 数组转换为 String 数组
     *
     * @param uniqTag Object 数组
     * @return String 数组
     */
    static String[] toStringArray(Object... uniqTag) {
        String[] result = new String[uniqTag.length];
        for (int i = 0; i < uniqTag.length; i++) {
            result[i] = uniqTag[i].toString();
        }
        return result;
    }
}
