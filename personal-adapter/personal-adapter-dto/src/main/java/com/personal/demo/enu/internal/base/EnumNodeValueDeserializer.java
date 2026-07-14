package com.personal.demo.enu.internal.base;

import com.personal.demo.enu.internal.node.*;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 枚举解析器
 * @date 2025/7/2 10:39
 */
public class EnumNodeValueDeserializer extends JsonDeserializer<BaseNode> {

    private static final Map<String, BaseNode> NODE_NAME_CACHE;

    //需要添加新枚举时 以下map 添加一行
    static {
        NODE_NAME_CACHE = buildNodeCache(
                OrderNode.values(),
                PayNode.values(),
                ProdNode.values(),
                StoreNode.values(),
                FinanceNode.values(),
                PaymentNode.values(),
                InvoiceNode.values(),
                StoreNode.values(),
                MemberNode.values(),
                TradeNode.values(),
                ComputeNode.values()
        );
    }

    private static Map<String, BaseNode> buildNodeCache(BaseNode[]... nodeArrays) {
        Map<String, BaseNode> cache = new ConcurrentHashMap<>();
        for (BaseNode[] nodes : nodeArrays) {
            for (BaseNode node : nodes) {
                cache.put(node.name(), node);
            }
        }
        return Collections.unmodifiableMap(cache);
    }

    @Override
    public BaseNode deserialize(JsonParser p, DeserializationContext ctx)
            throws IOException {
        String nodeName = p.getValueAsString();
        BaseNode node = NODE_NAME_CACHE.get(nodeName);
        if (node == null) {
            throw new JsonParseException(p, "非法的调用节点" + nodeName);
        }
        return node;
    }
}
