package com.personal.demo.enu.internal.base;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:  Node注册解析工厂
 * @date 2025/7/2 10:40
 */
public class NodeFactory {
    private static final Logger logger = LoggerFactory.getLogger(NodeFactory.class);
    private static final Map<String, Function<String, BaseNode>> parsers = new ConcurrentHashMap<>();

    // 注册解析器
    public static void register(String prefix, Function<String, BaseNode> parser) {
        parsers.put(prefix, parser);
        logger.info(String.format("Registered Node resolver: %s", prefix));
    }

    // 解析BaseNode
    public static BaseNode parse(String nodeName) {
        return parsers.entrySet().stream()
                .filter(entry -> nodeName.startsWith(entry.getKey()))
                .findFirst()
                .map(entry -> entry.getValue().apply(nodeName))
                .orElseThrow(() -> new IllegalArgumentException("未知节点: " + nodeName + "，已注册前缀: " + parsers.keySet()));
    }

    public static Set<String> getRegisteredPrefixes() {
        return new HashSet<>(parsers.keySet());
    }
}
