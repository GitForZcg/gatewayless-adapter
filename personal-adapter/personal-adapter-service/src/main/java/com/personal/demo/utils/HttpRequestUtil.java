package com.personal.demo.utils;

import com.personal.demo.conf.GsonFactory;
import com.personal.demo.util.TypeHelperUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import java.io.BufferedReader;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: http请求参数工具类
 * @date 2025/7/4 11:01
 */
@Slf4j
public class HttpRequestUtil {

    // 配置：只获取指定的请求头字段（如果为空则获取所有，排除上面的字段）
    private static final Set<String> INCLUDE_HEADERS = Set.of(
            "appid", "timestamp", "apisign", "sign"
    );
    // 配置：需要排除的请求头字段
    private static final Set<String> EXCLUDED_HEADERS = Set.of(
            "content-length", "content-encoding", "transfer-encoding",
            "connection", "cache-control", "accept-encoding"
    );

    public static Map<String, Object> requestParams(HttpServletRequest request) {
        Map<String, Object> params = new HashMap<>();
        try {

            getRequestHeaders(request, params);
            // 处理GET请求参数（包含在URL中的查询参数）
            if (HttpMethod.GET.name().equalsIgnoreCase(request.getMethod())) {
                // 获取所有查询参数
                Map<String, String[]> parameterMap = request.getParameterMap();
                getPathParams(parameterMap, params);
            }

            // 处理其他HTTP方法的查询参数（如POST请求URL中的参数）
            else {
                // 所有HTTP方法都可能有查询参数，不仅仅是GET
                Map<String, String[]> parameterMap = request.getParameterMap();
                getPathParams(parameterMap, params);

                // 获取body参数（JSON）- 仅用于POST/PUT等方法
                if (HttpMethod.POST.name().equalsIgnoreCase(request.getMethod()) ||
                        HttpMethod.PUT.name().equalsIgnoreCase(request.getMethod()) ||
                        HttpMethod.PATCH.name().equalsIgnoreCase(request.getMethod())) {

                    String contentType = request.getContentType();
                    if (contentType != null && contentType.contains(MediaType.APPLICATION_JSON_VALUE)) {
                        try (BufferedReader reader = request.getReader()) {
                            String body = reader.lines().collect(Collectors.joining());
                            if (!body.trim().isEmpty()) {
                                Type mapType = TypeHelperUtil.mapOf(String.class, Object.class);
                                Map<String, Object> bodyParams = GsonFactory.getInstance().fromJson(body, mapType);
                                // 合并参数，body参数优先级更高
                                if (bodyParams != null) {
                                    params.putAll(bodyParams);
                                }
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            log.error("Error parsing request parameters", e);
            params.put("parseError", e.getMessage());
        }

        return params;
    }

    /**
     * 获取请求头信息并存入参数Map
     */
    private static void getRequestHeaders(HttpServletRequest request, Map<String, Object> params) {
        try {
            // 获取所有请求头名称
            Enumeration<String> headerNames = request.getHeaderNames();

            if (headerNames != null) {
                while (headerNames.hasMoreElements()) {
                    String headerName = headerNames.nextElement();
                    String headerNameLower = headerName.toLowerCase();

                    // 过滤逻辑：如果配置了包含列表，则只获取列表中的；否则获取所有但排除黑名单
                    boolean shouldInclude;
                    if (!INCLUDE_HEADERS.isEmpty()) {
                        // 白名单模式：只获取指定字段
                        shouldInclude = INCLUDE_HEADERS.contains(headerNameLower);
                    } else {
                        // 黑名单模式：排除指定字段
                        shouldInclude = !EXCLUDED_HEADERS.contains(headerNameLower);
                    }

                    if (!shouldInclude) {
                        continue; // 跳过不需要的字段
                    }

                    // 获取该请求头的所有值
                    Enumeration<String> headerValues = request.getHeaders(headerName);
                    List<String> valueList = new ArrayList<>();

                    while (headerValues.hasMoreElements()) {
                        valueList.add(headerValues.nextElement());
                    }

                    // 根据值的数量决定存储方式
                    if (valueList.size() == 1) {
                        // 单个值直接存储字符串
                        params.put(headerNameLower, valueList.get(0));
                    } else if (valueList.size() > 1) {
                        // 多个值存储为列表
                        params.put(headerNameLower, valueList);
                    }
                }
            }

        } catch (Exception e) {
            log.error("Error parsing request headers", e);
            params.put("headerParseError", e.getMessage());
        }
    }

    private static void getPathParams(Map<String, String[]> parameterMap, Map<String, Object> params) {
        parameterMap.forEach((key, values) -> {
            if (values.length == 1) {
                params.put(key, values[0]);
            } else {
                params.put(key, Arrays.asList(values));
            }
        });
    }
}
