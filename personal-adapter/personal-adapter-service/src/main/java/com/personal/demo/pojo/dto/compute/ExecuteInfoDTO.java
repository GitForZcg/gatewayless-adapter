package com.personal.demo.pojo.dto.compute;

import com.personal.demo.pojo.base.DemoComputeMd5Param;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * @author sulu
 * @date 2026年02月10日 3:49 PM
 */
@Data
@Slf4j
public class ExecuteInfoDTO implements DemoComputeMd5Param, Serializable {

    /**
     * 执行的规则列表
     */
    private List<Object> executedRuleList;

    /**
     * 执行列表
     */
    private List<ExecuteItemDTO> executeList;

    /**
     * 微信OpenID
     */
    private String openId;

    /**
     * 订单Key
     */
    private String orderKey;

    /**
     * 执行ID
     */
    private String executeId;

    /**
     * 执行时间
     */
    private String executeTime;


    public Set<String> needSign() {
        return null;
    }

    @Override
    public Set<String> needSignParam() {
        return null;
    }

    @Override
    public String orderId() {
        return null;
    }

    /**
     * 调试方法：测试从JSON解析
     */
    public static ExecuteInfoDTO fromJson(String json) {
        try {
            com.google.gson.Gson gson = new com.google.gson.GsonBuilder()
                .serializeNulls()  // 保留null值
                .setPrettyPrinting()
                .create();
            return gson.fromJson(json, ExecuteInfoDTO.class);
        } catch (Exception e) {
            log.error("ExecuteInfoDTO JSON解析失败: {}", e.getMessage(), e);
            return null;
        }
    }
}
