package com.personal.demo.enu.external;

import com.personal.demo.anno.ExternalApiType;
import com.personal.demo.enu.external.base.ExternalBaseNode;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 
 * @date 2025/7/7 10:39
 */

@ExternalApiType(value = "STORE", channel = ExternalChannel.DEMO)
public enum ExternalStoreNode implements ExternalBaseNode {

    STORE_QUERY("门店查询", ExternalChannel.DEMO),
    STORE_LIST("门店列表", ExternalChannel.DEMO),
    ;

    final public String desc;
    final public ExternalChannel channel;

    ExternalStoreNode(String desc, ExternalChannel channel) {
        this.desc = desc;
        this.channel = channel;
    }

    @Override
    public ExternalChannel getChannel() {
        return this.channel;
    }
}
