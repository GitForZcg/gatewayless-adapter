package com.personal.demo.conf;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 线程池属性类
 * @date 2025/7/2 10:46
 */
@Getter
@Component
@ConfigurationProperties(prefix = ThreadPoolProperties.PREFIX)
@Data
public class ThreadPoolProperties {

    public static final String PREFIX = "thread.pool";

    private int coreSize;
    private int maxSize;
    private int queueCapacity;
    private int keepAliveSeconds;

    public void setCoreSize(int coreSize) {
        this.coreSize = coreSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public void setQueueCapacity(int queueCapacity) {
        this.queueCapacity = queueCapacity;
    }

    public void setKeepAliveSeconds(int keepAliveSeconds) {
        this.keepAliveSeconds = keepAliveSeconds;
    }
}
