package com.personal.demo.utils;

import com.personal.demo.consts.Separator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.personal.demo.consts.HttpConstant.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/7/16 18:32
 */

@Slf4j
public class HttpUtil {


    static {
        disableSslVerification();
    }

    private static final Set<String> INCLUDE_HEADERS = Set.of(
            "appid", "timestamp", "apisign", "sign"
    );

    private static void disableSslVerification() {
        try {
            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }
            };
            // Install the all-trusting trust manager
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            // Create all-trusting host name verifier
            HostnameVerifier allHostsValid = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };

            // Install the all-trusting host verifier
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            log.warn(String.format("disableSslVerification Exception:%s", e));
        }
    }


    public static Map<String, String> postForEntity(String url, String requestBody, Map<String, Object> apiHeader) {
        RestTemplate client = getTemplate();
        HttpHeaders headers = getHttpHeaders();

        // 以json的方式提交
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(HEADER_APPID, apiHeader.get(HEADER_APPID).toString());
        headers.add(HEADER_TIMESTAMP, apiHeader.get(HEADER_TIMESTAMP).toString());
        headers.add(HEADER_API_SIGN, apiHeader.get(HEADER_API_SIGN).toString());

        // 将请求头部和参数合成一个请求
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        // 执行HTTP请求
        Map<String, String> response = client.postForEntity(url, requestEntity, Map.class).getBody();

        return response;
    }

    public static Map<String, Object> postForEntity(String url, Map<String, Object> map) {
        RestTemplate client = getTemplate();
        HttpHeaders headers = getHttpHeaders();

        // 以json的方式提交
        headers.setContentType(MediaType.valueOf("application/json;charset=UTF-8"));
        headers.add(HEADER_SIGN, map.get(HEADER_SIGN).toString());

        // 将请求头部和参数合成一个请求
        Object body = map.get(BODY_DATA);
        HttpEntity<Object> requestEntity = new HttpEntity<>(body, headers);
        // 执行HTTP请求
        ResponseEntity<Map> mapResponseEntity = client.postForEntity(url, requestEntity, Map.class);
        Map<String, Object> response = mapResponseEntity.getBody();
        // 批量获取指定的响应头
        Map<String, String> responseHeaders = extractResponseHeaders(mapResponseEntity);
        response.put(HEADER, responseHeaders);
        return response;
    }

    public static Map<String, Object> postForEntityNoSign(String url, Map<String, Object> map) {
        RestTemplate client = getTemplate();
        HttpHeaders headers = getHttpHeaders();

        // 以json的方式提交
        headers.setContentType(MediaType.valueOf("application/json;charset=UTF-8"));
        // 将请求头部和参数合成一个请求
        HttpEntity<Object> requestEntity = new HttpEntity<>(map, headers);
        // 执行HTTP请求
        ResponseEntity<Map> mapResponseEntity = client.postForEntity(url, requestEntity, Map.class);
        Map<String, Object> response = mapResponseEntity.getBody();
        // 批量获取指定的响应头
        Map<String, String> responseHeaders = extractResponseHeaders(mapResponseEntity);
        response.put(HEADER, responseHeaders);
        return response;
    }

    // 提取指定的响应头
    private static Map<String, String> extractResponseHeaders(ResponseEntity<?> responseEntity) {
        Map<String, String> result = new HashMap<>();
        HttpHeaders headers = responseEntity.getHeaders();
        INCLUDE_HEADERS.forEach(headerName -> {
            String headerValue = headers.getFirst(headerName);
            if (headerValue != null) {
                result.put(headerName, headerValue);
            }
        });

        return result;
    }

    public static Map<String, Object> postEntity(String url, String requestBody, Map<String, Object> apiHeader) {
        RestTemplate client = getTemplate();
        HttpHeaders headers = getHttpHeaders();

        // 以json的方式提交
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(HEADER_TIMESTAMP, apiHeader.get(HEADER_TIMESTAMP).toString());
        headers.add(HEADER_TOKEN, apiHeader.get(HEADER_TOKEN).toString());

        // 将请求头部和参数合成一个请求
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        // 执行HTTP请求
        Map<String, Object> response = client.postForEntity(url, requestEntity, Map.class).getBody();

        return response;
    }

    public static Map<String, Object> vendorPostForEntity(String url, String requestBody) {
        RestTemplate client = getTemplate();
        HttpHeaders headers = getHttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        // 使用ParameterizedTypeReference避免类型转换警告
        ResponseEntity<Map<String, Object>> response = client.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<>() {
                }
        );

        return response.getBody();
    }

    public static String postForEntity(String url, String requestBody) {
        RestTemplate client = getTemplate();
        HttpHeaders headers = getHttpHeaders();
        headers.setContentType(APPLICATION_JSON);

        HttpEntity<Object> requestEntity = new HttpEntity<>(requestBody, headers);

        // 使用ParameterizedTypeReference避免类型转换警告
        ResponseEntity<String> response = client.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<>() {
                }
        );

        return response.getBody();
    }

    public static Map<String, Object> postForEntity(String url, MultiValueMap<String, Object> formData) {
        RestTemplate client = getTemplate();
        HttpHeaders headers = getHttpHeaders();

        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(formData, headers);

        // 使用ParameterizedTypeReference避免警告
        ResponseEntity<Map<String, Object>> response = client.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<>() {
                }
        );

        return response.getBody();
    }

    /**
     * 发送自定义application/x-www-form-urlencoded格式的POST请求
     * 适用于需要特殊处理参数编码的情况
     *
     * @param url         请求URL
     * @param requestBody 已经编码好的请求体字符串 (格式如: param1=value1&param2=value2)
     * @return 响应结果
     */
    public static Map<String, Object> postRawFormUrlEncoded(String url, String requestBody) {
        RestTemplate client = getTemplate();
        HttpHeaders headers = getHttpHeaders();

        // 设置Content-Type为application/x-www-form-urlencoded
        headers.setContentType(MediaType.valueOf("application/x-www-form-urlencoded;charset=utf-8"));
        // 创建请求实体，直接使用字符串作为请求体
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        // 执行HTTP请求并获取响应

        // 使用exchange替代postForEntity
        ResponseEntity<Map<String, Object>> response = client.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<>() {
                }
        );

        return response.getBody();
    }

    /**
     * 发送 multipart/form-data 格式的 POST 请求
     *
     * @param url         请求URL
     * @param requestBody 已编码的表单字符串 (格式如: param1=value1&param2=value2)
     * @return 响应结果
     */
    public static Map<String, Object> postMultipartFormData(String url, String requestBody) {
        RestTemplate client = getTemplate();
        HttpHeaders headers = getHttpHeaders();

        // 将 param1=value1&param2=value2 解析成 MultiValueMap
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        if (requestBody != null && !requestBody.isEmpty()) {
            for (String pair : requestBody.split(Separator.SPLIT)) {
                String[] kv = pair.split("=", 2);
                if (kv.length == 2) {
                    try {
                        String key   = URLDecoder.decode(kv[0], StandardCharsets.UTF_8);
                        String value = URLDecoder.decode(kv[1], StandardCharsets.UTF_8);
                        formData.add(key, value);
                    } catch (Exception e) {
                        throw new RuntimeException("参数解码失败: " + pair, e);
                    }
                }
            }
        }

        // 设置 Content-Type 为 multipart/form-data
        HttpHeaders formHeaders = new HttpHeaders();
        formHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, String>> requestEntity =
                new HttpEntity<>(formData, formHeaders);

        ResponseEntity<Map<String, Object>> response = client.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<>() {}
        );

        return response.getBody();
    }


    public static Map<String, Object> vendorRequestForEntity(String url, String requestJson, Map<String, Object> authRespMap, HttpMethod method) {
        RestTemplate client = getTemplate();
        HttpHeaders headers = getHttpHeaders();

        // 以json的方式提交
        headers.setContentType(MediaType.valueOf("application/json;charset=UTF-8"));
        headers.add("tokenId", authRespMap.get("tokenId").toString());
        headers.add("entCode", authRespMap.get("entCode").toString());

        // 将请求头部和参数合成一个请求
        HttpEntity<String> requestEntity = new HttpEntity<>(requestJson, headers);

        // 执行HTTP PUT请求
        ResponseEntity<Map> mapResponseEntity = client.exchange(
                url,
                method,
                requestEntity,
                Map.class
        );

        return (Map<String, Object>) mapResponseEntity.getBody();
    }

    private static RestTemplate getTemplate() {
        RestTemplate client = getRestTemplate();
        client.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        return client;
    }

    public static RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    public static HttpHeaders getHttpHeaders() {
        return new HttpHeaders();
    }


    // ==================== GET 方法 ====================

    /**
     * 简单的GET请求
     *
     * @param url 请求URL
     * @return 响应结果
     */
    public static String getForEntity(String url) {
        RestTemplate client = getTemplate();
        HttpHeaders headers = getHttpHeaders();
        headers.setContentType(APPLICATION_JSON);

        HttpEntity<Object> requestEntity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = client.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {
                }
        );

        return response.getBody();
    }

    /**
     * GET请求携带对象body（自动转换为JSON）
     *
     * @param url  请求URL
     * @param body 请求体对象
     * @return 响应结果
     */
    public static Map<String, Object> getForEntity(String url, Object body) {
        RestTemplate client = getTemplate();
        HttpHeaders headers = getHttpHeaders();
        // 直接传递对象，RestTemplate会自动转换为JSON
        HttpEntity<Object> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<Map<String, Object>> response = client.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<Map<String, Object>>() {
                }
        );
        return response.getBody();
    }


    /**
     * 带API头部信息的GET请求
     *
     * @param url       请求URL
     * @param apiHeader API头部信息
     * @return 响应结果
     */
    public static Map<String, Object> getForEntity(String url, Map<String, Object> apiHeader) {
        RestTemplate client = getTemplate();
        HttpHeaders headers = getHttpHeaders();

        // 添加API相关头部
        headers.add(HEADER_APPID, apiHeader.get(HEADER_APPID).toString());
        headers.add(HEADER_TIMESTAMP, apiHeader.get(HEADER_TIMESTAMP).toString());
        headers.add(HEADER_API_SIGN, apiHeader.get(HEADER_API_SIGN).toString());

        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);

        ResponseEntity<Map<String, Object>> response = client.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {
                }
        );

        return response.getBody();
    }

    /**
     * 带签名的GET请求
     *
     * @param url  请求URL
     * @param sign 签名信息
     * @return 响应结果，包含响应头
     */
    public static Map<String, Object> getForEntityWithSign(String url, String sign) {
        RestTemplate client = getTemplate();
        HttpHeaders headers = getHttpHeaders();

        headers.add(HEADER_SIGN, sign);

        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);

        ResponseEntity<Map<String, Object>> response = client.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {
                }
        );

        Map<String, Object> result = response.getBody();
        if (result == null) {
            result = new HashMap<>();
        }

        // 提取响应头
        Map<String, String> responseHeaders = extractResponseHeaders(response);
        result.put(HEADER, responseHeaders);

        return result;
    }


    public static Map<String, Object> vendorPostCooikeForEntity(String url, String requestBody, String mid) {
        RestTemplate client = getTemplate();
        HttpHeaders headers = getHttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Cookie", "mid=" + mid);

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        // 使用ParameterizedTypeReference避免类型转换警告
        ResponseEntity<Map<String, Object>> response = client.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<>() {
                }
        );
        return response.getBody();
    }


}
