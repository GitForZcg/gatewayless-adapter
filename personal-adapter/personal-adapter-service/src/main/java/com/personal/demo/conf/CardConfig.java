package com.personal.demo.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;


/**
 * 是否合并电子卡配置类
 *
 * @Author: fxs
 * @Date: 2026/4/14 13:26
 */
@Data
@RefreshScope
@Component
@ConfigurationProperties(prefix = "card")
public class CardConfig {

    private Boolean isMerged;               // 是否合并电子卡
}
