package com.personal.demo.serivce;

import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.pojo.base.DemoOrderMd5Param;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 抽象订单MD5处理
 * @date 2025/7/24 13:37
 */

public interface AbstractOrderMD5Service {


    <T extends DemoOrderMd5Param> Boolean execute(T reqDto, BaseNode node,String orderId,String sid);

}
