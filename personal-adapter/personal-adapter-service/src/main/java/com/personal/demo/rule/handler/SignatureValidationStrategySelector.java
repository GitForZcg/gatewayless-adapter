package com.personal.demo.rule.handler;

import com.personal.demo.anno.Signature;
import com.personal.demo.conf.SignatureValidationConfig;
import com.personal.demo.pojo.base.SignatureValidationParam;
import com.personal.demo.rule.exception.AdapterException;
import com.personal.demo.rule.strategy.SignatureValidationStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.personal.demo.pojo.wrapper.AdapterErrorCode.SIGN_VERIFY_STRATEGY_NOT_SUPPORT;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 签名验证策略选择器
 * @date 2025/7/8 10:53
 */
@Component
@Slf4j
public class SignatureValidationStrategySelector {


    private final Map<String, SignatureValidationStrategy> strategies;

    public SignatureValidationStrategySelector(List<SignatureValidationStrategy> strategyList) {
        this.strategies = strategyList.stream()
                .collect(Collectors.toMap(SignatureValidationStrategy::getStrategyType, Function.identity()));

        log.info("Initialized verify signature strategies: {}", strategies.keySet());
    }

    /**
     * 根据参数类的注解获取验签配置
     */
    public SignatureValidationConfig getValidationConfig(Object params) {
        if (params == null) {
            return SignatureValidationConfig.skip();
        }

        Class<?> paramClass = params.getClass();

        // 查找类上的SignatureValidation注解
        Signature annotation = findSignatureValidationAnnotation(paramClass);

        if (annotation == null) {
            // 如果没有注解，检查是否实现了SignatureValidationParam接口
            if (!(params instanceof SignatureValidationParam)) {
                log.debug("参数类 {} 未实现SignatureValidationParam接口，跳过验签", paramClass.getSimpleName());
                return SignatureValidationConfig.skip();
            }
            // 使用默认配置
            log.debug("参数类 {} 未找到@Signature注解，使用默认配置", paramClass.getSimpleName());
            annotation = createDefaultAnnotation();
        }

        // 获取对应的策略
        SignatureValidationStrategy strategy = getStrategy(annotation.strategy());

        log.debug("参数类 {} 使用验签策略: {}, 是否必须: {}",
                paramClass.getSimpleName(), annotation.strategy(), annotation.required());

        return new SignatureValidationConfig(annotation, strategy);
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
    private SignatureValidationStrategy getStrategy(String strategyType) {
        SignatureValidationStrategy strategy = strategies.get(strategyType);
        if (strategy == null) {
            throw new AdapterException(SIGN_VERIFY_STRATEGY_NOT_SUPPORT.code,
                    String.format(SIGN_VERIFY_STRATEGY_NOT_SUPPORT.message, strategyType),
                    SIGN_VERIFY_STRATEGY_NOT_SUPPORT.type
            );
        }
        return strategy;
    }

    /**
     * 创建默认注解配置
     */
    private Signature createDefaultAnnotation() {
        return new Signature() {
            @Override
            public Class<? extends Annotation> annotationType() {
                return Signature.class;
            }

            @Override
            public String strategy() {
                return "MD5";
            }

            @Override
            public boolean required() {
                return true;
            }

            @Override
            public String signField() {
                return "sign";
            }

            @Override
            public String appIdField() {
                return "app_id";
            }
        };
    }
}
