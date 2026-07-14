package com.personal.demo.conf;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 线程池
 * @date 2025/7/2 10:46
 */
@Configuration
public class ThreadPoolConfig {

    private final ThreadPoolProperties properties;

    public ThreadPoolConfig(ThreadPoolProperties properties) {
        this.properties = properties;
    }

    @Bean(name = "adapterThreadPool")
    public ExecutorService customThreadPool() {
        return new ThreadPoolExecutor(
                properties.getCoreSize(),
                properties.getMaxSize(),
                properties.getKeepAliveSeconds(),
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(properties.getQueueCapacity()),
                new ThreadFactory() {
                    private final ThreadFactory defaultFactory = Executors.defaultThreadFactory();

                    @Override
                    public Thread newThread(@NotNull Runnable r) {
                        Thread thread = defaultFactory.newThread(r);
                        thread.setName("adapter-thread-" + thread.getId());
                        return thread;
                    }
                },
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
    }
}