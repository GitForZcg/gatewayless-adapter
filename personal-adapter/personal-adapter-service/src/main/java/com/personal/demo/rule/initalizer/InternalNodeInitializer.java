package com.personal.demo.rule.initalizer;

import com.personal.demo.enu.internal.base.NodeFactory;
import com.personal.demo.enu.internal.node.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 确保所有Node都已初始化
 * 虽然需要手动维护，但代码集中在这一个地方
 * <p>
 * 当前支持的 implements BaseNode 的任意Node类型：
 * - {@link OrderNode} 订单相关节点
 * - {@link PayNode} 支付相关节点
 * <p>
 * 新增Node时，请在 {@link #initializeNode(Class)} 方法中添加对应的初始化调用
 * @date 2025/7/2 10:54
 */
public class InternalNodeInitializer {

    private static final Logger logger = LoggerFactory.getLogger(InternalNodeInitializer.class);
    private static volatile boolean initialized = false;

    /**
     * 确保所有Node都已初始化
     * 虽然需要手动维护，但代码集中在这一个地方
     */
    public static synchronized void ensureInitialized() {
        if (initialized) {
            return;
        }

        // 集中维护所有Node，新增时只需要在这里加一行
        initializeNode(OrderNode.class);
        initializeNode(PayNode.class);
        initializeNode(StoreNode.class);
        initializeNode(ProdNode.class);
        initializeNode(PaymentNode.class);
        initializeNode(InvoiceNode.class);
        initializeNode(MemberNode.class);
        initializeNode(ComputeNode.class);
        initializeNode(TradeNode.class);
        initialized = true;
        logger.info("Node resolver initialization completed, registered list: {}", NodeFactory.getRegisteredPrefixes());
    }

    /**
     * 初始化单个Node枚举类
     */
    private static void initializeNode(Class<? extends Enum<?>> nodeClass) {
        try {
            // 触发枚举类加载，执行静态块
            nodeClass.getEnumConstants();
            logger.debug("node initialization: {}", nodeClass.getSimpleName());
        } catch (Exception e) {
            logger.error("Node initialization failed: {}", nodeClass.getSimpleName(), e);
        }
    }
}
