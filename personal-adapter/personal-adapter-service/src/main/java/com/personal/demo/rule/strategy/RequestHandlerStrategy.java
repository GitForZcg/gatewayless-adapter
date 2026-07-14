package com.personal.demo.rule.strategy;

import com.personal.demo.pojo.base.RequestContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 请求处理策略
 * @date 2025/7/4 10:59
 */
public interface RequestHandlerStrategy {

    /**
     * 请求处理分发
     */
    boolean handle(HttpServletRequest request, HttpServletResponse response, RequestContext context) throws Exception;

}
