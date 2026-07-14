package com.personal.demo.enu.external;

import com.personal.demo.anno.ExternalApiType;
import com.personal.demo.enu.external.base.ExternalBaseNode;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/7/4 10:39
 */

@ExternalApiType(value = "PRODUCT", channel = ExternalChannel.DEMO)
public enum ExternalProductNode implements ExternalBaseNode {

    PRODUCT_PREHEAT("菜项同步预热通知", ExternalChannel.DEMO),
    PRODUCT_ALL("门店菜项集合", ExternalChannel.DEMO),
    PRODUCT_CONDIMENT("门店配料", ExternalChannel.DEMO),
    PRODUCT_COMBO("门店套餐列表", ExternalChannel.DEMO),
    PRODUCT_MASTER_ALL("品牌级菜项基础数据", ExternalChannel.DEMO),
    PRODUCT_MENU("门店菜谱设置", ExternalChannel.DEMO),
    PRODUCT_DAILY("门店日结", ExternalChannel.DEMO),
    ;

    final public String desc;
    final public ExternalChannel channel;

    ExternalProductNode(String desc, ExternalChannel channel) {
        this.desc = desc;
        this.channel = channel;
    }

    @Override
    public ExternalChannel getChannel() {
        return this.channel;
    }
}
