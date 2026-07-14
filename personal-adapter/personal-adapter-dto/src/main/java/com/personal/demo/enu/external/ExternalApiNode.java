package com.personal.demo.enu.external;

import com.personal.demo.enu.external.base.ExternalBaseNode;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/7/4 10:39
 */



@SuppressWarnings("all")
public enum ExternalApiNode {
    PRODUCT(ExternalChannel.DEMO) {
        @Override
        public ExternalBaseNode[] getNodes() {
            return ExternalProductNode.values();
        }
    },
    STORE(ExternalChannel.DEMO) {
        @Override
        public ExternalBaseNode[] getNodes() {
            return ExternalStoreNode.values();
        }
    };

    final public ExternalChannel channel;

    ExternalApiNode(ExternalChannel channel) {
        this.channel = channel;
    }

    // 抽象方法，由每个枚举常量实现
    public abstract ExternalBaseNode[] getNodes();

//    // 修改返回类型为 ExternalBaseNode
//    public static ExternalBaseNode getNode(String apiType, String apiSubType) {
//        for (ExternalApiNode node : ExternalApiNode.values()) {
//            if (node.name().equals(apiType)) {
//                return Arrays.stream(node.getNodes())
//                        .filter(no -> ((Enum<?>) no).name().equals(apiSubType))
//                        .findFirst()
//                        .orElse(null);
//            }
//        }
//        return null;
//    }
//
//    public static ExternalApiNode getName(String apiType) {
//        for (ExternalApiNode value : ExternalApiNode.values()) {
//            if (value.name().equals(apiType)) {
//                return value;
//            }
//        }
//        return null;
//    }
//
//    // 额外的工具方法：获取特定类型的节点
//
//    public <T extends ExternalBaseNode> T getNodeAs(String apiSubType, Class<T> clazz) {
//        return (T) Arrays.stream(this.getNodes())
//                .filter(no -> ((Enum<?>) no).name().equals(apiSubType))
//                .findFirst()
//                .orElse(null);
//    }
}

