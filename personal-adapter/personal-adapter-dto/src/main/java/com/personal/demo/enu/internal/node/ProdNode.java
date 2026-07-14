package com.personal.demo.enu.internal.node;

import com.personal.demo.enu.internal.base.BaseChannel;
import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.enu.internal.base.NodeFactory;

/**
 * 产品内部流程节点
 *
 * @Author: fxs
 * @Date: 2025/8/13 14:55
 */
public enum ProdNode implements BaseNode {

    PRODUCT_NOTIFICATION("通知接口", "/personal-adapter/product/pluSyncPreheat.do", BaseChannel.DEMO),

    ;

    public final String desc;

    public final String url;
    public final BaseChannel channel;

    ProdNode(String desc, String url, BaseChannel channel) {
        this.desc = desc;
        this.url = url;
        this.channel = channel;
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
        NodeFactory.register("PRODUCT_", ProdNode::valueOf);
    }
}
