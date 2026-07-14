package com.personal.demo.conf;

import com.personal.demo.pojo.dto.response.product.DayCarryoverResponse;
import com.personal.demo.pojo.dto.response.product.PeriodSchemeTimeResponse;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * 餐段设置
 *
 * @Author: fxs
 * @Date: 2026/2/4 14:46
 */
@Data
@RefreshScope
@Component
@ConfigurationProperties(prefix = "store.schedule")
public class ScheduleProperties {


    /**
     * 门店日结设置
     */
    private DayCarryoverResponse dayCarryover;

    /**
     * 门店餐段时间设置
     */
    private PeriodSchemeTimeResponse periodSchemeTime;
}
