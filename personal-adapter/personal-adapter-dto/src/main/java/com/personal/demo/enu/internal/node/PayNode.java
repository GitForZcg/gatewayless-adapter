package com.personal.demo.enu.internal.node;

import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.enu.internal.base.NodeFactory;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/7/2 10:40
 */
public enum PayNode implements BaseNode {
    PAY_PREPARE("预支付", "/prepay"),
    PAY_QUERY("支付查询", "/queryOrder"),
    //    PAY_CLOSE("支付关闭", "/"),
    PAY_REFUND("支付退款", "/refund"),
    PAY_REFUND_QUERY("退款查询", "/queryRefund"),

    ;

    public final String desc;
    public final String url;

    PayNode(String desc, String url) {
        this.desc = desc;
        this.url = url;
    }

    @Override
    public String url() {
        return this.url;
    }

    @Override
    public String nodeDesc() {
        return this.desc;
    }

    static {
        NodeFactory.register("PAY_", PayNode::valueOf);
    }
}
