package com.personal.demo.enu.internal.node;

import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.enu.internal.base.NodeFactory;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/7/2 10:40
 */
public enum OrderNode implements BaseNode {
    ORDER_QUERY("查询门店", "/custom/uploadAndTrigger"),
    ORDER_PUSH("推送小程序订单", "/custom/uploadAndTrigger"),
    ORDER_PUSH_OFFSET("推送小程序退款单", "/custom/uploadAndTrigger"),


    ;

    public final String desc;
    public final String url;

    OrderNode(String desc, String url) {
        this.desc = desc;
        this.url = url;
    }

    @Override
    public String url() {
        return this.url;
    }

    static {
        NodeFactory.register("ORDER_", OrderNode::valueOf);
    }
}
