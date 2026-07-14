package com.personal.demo.utils;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

import java.net.URI;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/9/19 16:11
 */
public class HttpGetWithEntity extends HttpEntityEnclosingRequestBase {
    public static final String METHOD_NAME = "GET";

    public HttpGetWithEntity(String uri) {
        super();
        setURI(URI.create(uri));
    }

    @Override
    public String getMethod() {
        return METHOD_NAME;
    }
}
