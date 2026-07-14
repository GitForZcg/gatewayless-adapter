package com.personal.demo.rule.strategy;

import com.personal.demo.pojo.base.RequestContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 内部请求处理策略
 * @date 2025/7/4 10:58
 */
@Component
@Slf4j
public class InternalRequestHandlerStrategy implements RequestHandlerStrategy {

    @Override
    public boolean handle(HttpServletRequest request, HttpServletResponse response, RequestContext context) {
        return true;
    }
}