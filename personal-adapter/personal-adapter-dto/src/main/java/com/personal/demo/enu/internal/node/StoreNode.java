package com.personal.demo.enu.internal.node;

import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.enu.internal.base.NodeFactory;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/7/2 10:40
 */
public enum StoreNode implements BaseNode {
    STORE_COMPANY_QUERY("公司查询","/cloudfi/EasBaseImport/company")
    ;

    public final String desc;
    public final String url;

    StoreNode(String desc, String url) {
        this.desc = desc;
        this.url = url;
    }

    @Override
    public String url() {
        return this.url;
    }

    static {
        NodeFactory.register("STORE_", StoreNode::valueOf);
    }
}
