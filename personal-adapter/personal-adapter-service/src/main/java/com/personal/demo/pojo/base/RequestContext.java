package com.personal.demo.pojo.base;

import lombok.Builder;
import lombok.Data;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/7/4 10:48
 */
@Data
@Builder
public class RequestContext {
    /**
     * 访问类型：internal, external
     */
    private WhitelistConfig.AccessType accessType;

    /**
     * 公司名称
     */
    private String channelName;

    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * API路径
     */
    private String apiPath;
    /**
     * API类型
     */
    private String apiType;
    /**
     * API子类型
     */
    private String apiSubType;
    /**
     * 密钥访问id
     */
    private int accessKeyId;
    /**
     * 客户端IP
     */
    private String clientIp;

    /**
     * HTTP方法
     */
    private String httpMethod;

    /**
     * 是否通过白名单验证
     */
    private boolean whitelisted;

    /**
     * 匹配到的配置ID
     */
    private Long matchedConfigId;

    /**
     * 匹配到的配置ID
     */
    private String className;
}
