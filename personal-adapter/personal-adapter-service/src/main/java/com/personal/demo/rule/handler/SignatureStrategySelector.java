package com.personal.demo.rule.handler;

import com.personal.demo.anno.Signature;
import com.personal.demo.conf.SignatureConfig;
import com.personal.demo.pojo.base.SignatureParam;
import com.personal.demo.rule.exception.AdapterException;
import com.personal.demo.rule.strategy.SignatureStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.personal.demo.pojo.wrapper.AdapterErrorCode.SIGN_CALCULATE_NOT_SUPPORT;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 签名策略选择器
 * @date 2025/7/18 10:01
 */
@Component
@Slf4j
public class SignatureStrategySelector {


    private final Map<String, SignatureStrategy> strategies;

    public SignatureStrategySelector(List<SignatureStrategy> strategyList) {
        this.strategies = strategyList.stream()
                .collect(Collectors.toMap(SignatureStrategy::getStrategyType, Function.identity()));

        log.info("Initialized calculate signature strategies: {}", strategies.keySet());
    }

    /**
     * 根据参数类的注解获取验签配置
     */
    public SignatureConfig getCalculateConfig(Object params) {
        if (params == null) {
            return SignatureConfig.skip();
        }

        Class<?> paramClass = params.getClass();

        // 查找类上的SignatureValidation注解
        Signature annotation = findSignatureValidationAnnotation(paramClass);
        if (annotation == null) {
            // 如果没有注解，检查是否实现了SignatureParam接口
            if (!(params instanceof SignatureParam)) {
                log.debug("参数类 {} 未实现SignatureParam接口，跳过加签", paramClass.getSimpleName());
                return SignatureConfig.skip();
            }
            // 使用默认配置
            log.warn("参数类 {} 未找到@Signature注解，使用默认配置", paramClass.getSimpleName());
            throw new RuntimeException("无法找到对应的加签规则");
        }

        // 获取对应的策略
        SignatureStrategy strategy = getStrategy(annotation.strategy());

        log.debug("参数类 {} 使用加签策略: {}, 是否必须: {}",
                paramClass.getSimpleName(), annotation.strategy(), annotation.required());

        return new SignatureConfig(annotation, strategy);
    }

    /**
     * 递归查找SignatureValidation注解（支持继承）
     */
    private Signature findSignatureValidationAnnotation(Class<?> clazz) {
        if (clazz == null || clazz == Object.class) {
            return null;
        }

        // 检查当前类
        Signature annotation = clazz.getAnnotation(Signature.class);
        if (annotation != null) {
            return annotation;
        }

        // 检查父类
        annotation = findSignatureValidationAnnotation(clazz.getSuperclass());
        if (annotation != null) {
            return annotation;
        }

        // 检查接口
        for (Class<?> interfaceClass : clazz.getInterfaces()) {
            annotation = findSignatureValidationAnnotation(interfaceClass);
            if (annotation != null) {
                return annotation;
            }
        }

        return null;
    }

    /**
     * 获取验签策略
     */
    private SignatureStrategy getStrategy(String strategyType) {
        SignatureStrategy strategy = strategies.get(strategyType);
        if (strategy == null) {
            throw new AdapterException(SIGN_CALCULATE_NOT_SUPPORT.code,
                    String.format(SIGN_CALCULATE_NOT_SUPPORT.message, strategyType),
                    SIGN_CALCULATE_NOT_SUPPORT.type
            );
        }
        return strategy;
    }
}
