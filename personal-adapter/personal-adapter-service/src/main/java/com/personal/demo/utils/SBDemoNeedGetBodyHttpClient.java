//package com.personal.demo.utils;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.http.client.config.RequestConfig;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
//import org.apache.http.conn.ssl.NoopHostnameVerifier;
//import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
//import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
//import org.apache.http.entity.ContentType;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.ssl.SSLContexts;
//import org.apache.http.util.EntityUtils;
//
//import java.net.URI;
//
///**
// * @author zhangjunyi
// * @version 1.0
// * @description:
// * @date 2025/9/19 16:34
// */
//@Slf4j
//public class SBDemoNeedGetBodyHttpClient {
//    private static SSLConnectionSocketFactory sslsf = null;
//
//    private SBDemoNeedGetBodyHttpClient() {
//    }
//
//    static {
//        try {
//            sslsf = new SSLConnectionSocketFactory(
//                    SSLContexts.custom().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build(),
//                    NoopHostnameVerifier.INSTANCE);
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//        }
//    }
//
//    public static CloseableHttpClient getHttpClient() {
//        RequestConfig requestConfig = RequestConfig.custom()
//                //设置等待数据超时时间
//                .setSocketTimeout(50000)
//                //设置连接超时时间
//                .setConnectTimeout(5000)
//                .setConnectionRequestTimeout(5000)
//                .build();
//        return HttpClients.custom()
//                .setSSLSocketFactory(sslsf)
//                .setConnectionManagerShared(true).setDefaultRequestConfig(requestConfig)
//                .build();
//    }
//
//
//    public static String doGetJson(String url, String param) {
//        // 构建支持跳过 SSL 验证的 HttpClient 实例
//        CloseableHttpClient client = SBDemoNeedGetBodyHttpClient.getHttpClient();
//        String body = "";
//        HttpGetWithEntity httpGetWithEntity = new HttpGetWithEntity(url);
//        org.apache.http.HttpEntity httpEntity = new StringEntity(param, ContentType.APPLICATION_JSON);
//        httpGetWithEntity.setEntity(httpEntity);
//        //执行请求操作，并拿到结果（同步阻塞）
//        try (CloseableHttpResponse response = client.execute(httpGetWithEntity)) {
//            //获取结果实体
//            org.apache.http.HttpEntity entity = response.getEntity();
//            if (entity != null) {
//                //按指定编码转换结果实体为String类型
//                body = EntityUtils.toString(entity, "UTF-8");
//            }
//        } catch (Exception e) {
//            log.error("doGetJson() error.", e);
//        }
//        return body;
//    }
//
//    static class HttpGetWithEntity extends HttpEntityEnclosingRequestBase {
//        private final static String METHOD_NAME = "GET";
//
//        @Override
//        public String getMethod() {
//            return METHOD_NAME;
//        }
//
//        public HttpGetWithEntity() {
//            super();
//        }
//
//        public HttpGetWithEntity(final URI uri) {
//            super();
//            setURI(uri);
//        }
//
//        HttpGetWithEntity(final String uri) {
//            super();
//            setURI(URI.create(uri));
//        }
//    }
//
//}
