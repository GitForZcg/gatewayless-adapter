package com.personal.demo.enu.internal.node;

import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.enu.internal.base.NodeFactory;

public enum ComputeNode implements BaseNode {


    COMPUTE_PROMOTION("促销算价", "/market/api/promotion"),

    COMPUTE_MEMBER_ASSETS("上报会员资产","/market/member/info"),
    COMPUTE_CART_PRICE("购物车算价", "/market/api/executePos"),

    COMPUTE_PRE_ORDER_PRICE("订单确认页", "/market/api/executePos"),

    COMPUTE_POS_MEMBER_ASSETS("POS会员资产上报", "/market/pos/member/info"),


    ;

    public final String desc;
    public final String url;

    ComputeNode(String desc, String url) {
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
        NodeFactory.register("COMPUTE_", ComputeNode::valueOf);
    }
}
