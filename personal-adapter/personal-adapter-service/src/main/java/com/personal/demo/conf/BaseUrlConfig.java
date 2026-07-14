package com.personal.demo.conf;

import lombok.Data;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 调用远端服务获取基础域名配置类
 * @date 2025/8/18 13:19
 */
@ConfigurationProperties(prefix = BaseUrlConfig.BASE_URL_PREFIX)
@Data
@Getter
public class BaseUrlConfig {

    public static final String BASE_URL_PREFIX = "service-request-url";

    private static final String BASE_URL_SUFFIX = "BaseUrl";

    private String orderBaseUrl;

    private String storeBaseUrl;

    private String paymentBaseUrl;

    private String invoiceBaseUrl;

    private String kpinvoiceBaseUrl;

    private String finishInvoiceBaseUrl;

    private String payBaseUrl;

    private String computeBaseUrl;

    private String memberBaseUrl;

    private String tradeBaseUrl;

    private Map<String, String> services = new HashMap<>();

    /**
     * 根据服务名获取访问地址
     *
     * @param serviceName 服务名
     * @return 服务访问地址
     */
    public String getServiceUrl(String serviceName) {
        String serviceBaseUrl = getServiceName(serviceName);
        boolean result = this.hasService(serviceBaseUrl);
        if (!result) {
            throw new IllegalArgumentException("找不到对应的服务baseUrl配置 " + serviceName);
        }
        return services.get(serviceBaseUrl);
    }

    /**
     * 检查服务是否存在
     */
    public boolean hasService(String serviceName) {
        return services.containsKey(serviceName);
    }

    @NotNull
    private static String getServiceName(String serviceName) {
        return serviceName + BASE_URL_SUFFIX;
    }
}
