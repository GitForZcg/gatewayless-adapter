package com.personal.demo.rule.factory;

import com.personal.demo.pojo.base.WhitelistConfig;
import com.personal.demo.rule.strategy.ExternalRequestHandlerStrategy;
import com.personal.demo.rule.strategy.InternalRequestHandlerStrategy;
import com.personal.demo.rule.strategy.RequestHandlerStrategy;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 请求分发策略工厂
 * @date 2025/7/4 10:50
 */
@Component
public class RequestHandlerStrategyFactory {

    private final Map<WhitelistConfig.AccessType, RequestHandlerStrategy> strategies;

    public RequestHandlerStrategyFactory(InternalRequestHandlerStrategy internalStrategy,
                                         ExternalRequestHandlerStrategy externalStrategy) {
        this.strategies = Map.of(
                WhitelistConfig.AccessType.INTERNAL, internalStrategy,
                WhitelistConfig.AccessType.EXTERNAL, externalStrategy
        );
    }

    public RequestHandlerStrategy getStrategy(WhitelistConfig.AccessType accessType) {
        RequestHandlerStrategy strategy = strategies.get(accessType);
        if (strategy == null) {
            throw new IllegalArgumentException("不支持的访问类型: " + accessType);
        }
        return strategy;
    }
}