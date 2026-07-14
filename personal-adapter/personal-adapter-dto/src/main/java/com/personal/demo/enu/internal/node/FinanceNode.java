package com.personal.demo.enu.internal.node;

import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.enu.internal.base.NodeFactory;

/**
 * 财务节点
 * @date: 2025/9/9 17:55 
 */
public enum FinanceNode implements BaseNode {
    FINANCE_IMPORT_VOUCHER("导入凭证","/cloudfi/EasImport/Voucher")
    ;

    public final String desc;
    public final String url;

    FinanceNode(String desc, String url) {
        this.desc = desc;
        this.url = url;
    }

    @Override
    public String url() {
        return this.url;
    }

    static {
        NodeFactory.register("FINANCE_", FinanceNode::valueOf);
    }
}
