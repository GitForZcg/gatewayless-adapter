package com.personal.demo;

import jakarta.servlet.annotation.WebFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;


/**
 * 主控代码.
 */

@SpringBootApplication(exclude = {
        RedisAutoConfiguration.class,
        RedisRepositoriesAutoConfiguration.class
})
@ComponentScan(value = {"com.common.dto", "com.common.base", "com.common.log", "com.common.redis", "com.common.oss", "com.personal.demo*",
        "com.personal.order", "com.personal.member", "com.personal.product", "com.demo.order", "com.personal.store", "com.personal.demo.order", "com.common.mq", "com.personal.pay"})
@WebFilter("com.common.dto.filter")
@ConfigurationPropertiesScan
@Slf4j
@EnableFeignClients(basePackages = {"com.personal.order", "com.personal.member", "com.demo.order", "com.personal.product", "com.personal.store", "com.personal.demo.order", "com.personal.pay"})
public class DemoAdapterApplication {

    /**
     * 主控方法.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(DemoAdapterApplication.class, args);
    }

}