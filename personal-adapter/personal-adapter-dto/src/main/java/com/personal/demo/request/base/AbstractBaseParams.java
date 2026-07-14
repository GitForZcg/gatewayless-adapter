package com.personal.demo.request.base;


/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 基础检索参数-用于约束类型
 * @date 2025/7/2 10:41
 */

public abstract class AbstractBaseParams implements BaseParamsProvider {

    /**
     * 顶级业务参数提供器
     */
    @Override
    public abstract Object getBizData();

    /**
     * 设置业务数据
     */
    public abstract void setBizData(Object bizData);
}
