package com.personal.demo.enu.internal.node;

import com.personal.demo.enu.internal.base.BaseChannel;
import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.enu.internal.base.NodeFactory;

/**
 * trade内部流程节点
 *
 * @Author:
 * @Date:
 */
public enum TradeNode implements BaseNode {

    TRADE_ORDER_PREVIEW("交易预览", "/deal/preview", BaseChannel.DEMO),
    TRADE_ORDER_COMMIT("交易提交", "/deal/commit", BaseChannel.DEMO),
    TRADE_ORDER_CANCEL("交易撤销", "/deal/cancel", BaseChannel.DEMO),
    ;

    public final String desc;

    public final String url;
    public final BaseChannel channel;

    TradeNode(String desc, String url, BaseChannel channel) {
        this.desc = desc;
        this.url = url;
        this.channel = channel;
    }

    @Override
    public String nodeDesc() {
        return this.desc;
    }

    @Override
    public String url() {
        return this.url;
    }

    @Override
    public String channel() {
        return String.valueOf(this.channel);
    }


    static {
        NodeFactory.register("TRADE_", TradeNode::valueOf);
    }
}
