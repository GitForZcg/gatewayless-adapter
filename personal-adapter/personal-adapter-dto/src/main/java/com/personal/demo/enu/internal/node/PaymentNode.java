package com.personal.demo.enu.internal.node;

import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.enu.internal.base.NodeFactory;


public enum PaymentNode implements BaseNode {
    PAYMENT_IMPORT_FEE_DETAILS("导入分类费用明细", "/api/openapi/receive/expense", "POST"),
    PAYMENT_IMPORT_ORDER("导入付款单", "/api/openapi/receive/reimburse", "POST"),
    PAYMENT_QUERY_ORDER("查询付款单", "/api/openapi/form/reimburse/detail", "POST");

    public final String desc;
    public final String url;
    public final String method;

    PaymentNode(String desc, String url, String method) {
        this.desc = desc;
        this.url = url;
        this.method = method;
    }

    @Override
    public String url() {
        return this.url;
    }

    @Override
    public String method() {
        return this.method;
    }

    static {
        NodeFactory.register("PAYMENT_", PaymentNode::valueOf);
    }
}
