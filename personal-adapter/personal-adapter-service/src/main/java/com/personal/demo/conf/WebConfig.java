package com.personal.demo.conf;

import com.personal.demo.rule.handler.WhitelistHandleInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 拦截服务配置
 * @date 2025/7/4 10:46
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final WhitelistHandleInterceptor whitelistInterceptor;

    public WebConfig(@Lazy WhitelistHandleInterceptor whitelistInterceptor) {
        this.whitelistInterceptor = whitelistInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(whitelistInterceptor)
                // 拦截内部统一服务接口和外部接口
                .addPathPatterns("/**")
                .excludePathPatterns("/config/**", "/notify/**")
                .order(1);
    }
}
