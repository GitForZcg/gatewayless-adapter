package com.personal.demo.conf;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.function.Supplier;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 饿汉式 单例 GSON 工厂
 * @date 2025/7/2 10:46
 */
public class GsonFactory {

    private static final Supplier<Gson> GSON_INSTANCE = () ->
            new GsonBuilder().disableHtmlEscaping().create();

    public static Gson getInstance() {
        return GSON_INSTANCE.get();
    }

}
