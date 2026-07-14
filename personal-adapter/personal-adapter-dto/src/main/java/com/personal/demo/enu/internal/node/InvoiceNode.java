package com.personal.demo.enu.internal.node;

import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.enu.internal.base.NodeFactory;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/7/2 10:40
 */
public enum InvoiceNode implements BaseNode {
    INVOICE_GET("发票开具", "/invoice/elec/applet/new/get"),
    INVOICE_CANCEL("发票取消", "/invoice/elec/applet/new/get"),
    INVOICE_LIST("可开发票列表", "/api/order/getInvoiceList"),
    INVOICE_REVIEW_AMOUNT("计算实际开票金额", "/invoice/list/amount"),
    INVOICE_FINISH_LIST("已开票的列表", "/api/invoice/applet/orderNo/list"),
    INVOICE_TITLE_SEARCH("发票抬头查询", "/invoice/elec/title_search"),
    INVOICE_PUSH_EMAIL("发票邮箱推送", "/invoice/elec/applet/pushEmail"),

    ;

    public final String desc;
    public final String url;

    InvoiceNode(String desc, String url) {
        this.desc = desc;
        this.url = url;
    }

    @Override
    public String url() {
        return this.url;
    }

    static {
        NodeFactory.register("INVOICE_", InvoiceNode::valueOf);
    }
}
