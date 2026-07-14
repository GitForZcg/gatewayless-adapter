package com.personal.demo.pojo.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/7/17 14:37
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class AccessKeyConfig implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;
    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 应用凭证
     */
    private String appid;
    /**
     * 访问密钥
     */
    private String secret;
    /**
     * 拓展配置
     */
    private String extra;

    /**
     * 随机值
     */
    private String salt;
    /**
     * 时间戳容忍差
     */
    private int timestampTolerance;

    /**
     * 渠道公钥
     */
    private String publicKey;

    /**
     * 本公司私钥
     */
    private String privateKey;

    /**
     * 激活状态：1-激活，0-禁用
     */
    private boolean activeStatus;

    /**
     * 描述信息
     */
    private String description;

    /**
     * 配置有效期，NULL表示永久有效
     */
    private Long expireTime;

    /**
     * 创建时间
     */
    private Long createdTime;

    /**
     * 更新时间
     */
    private Long updatedTime;


}
