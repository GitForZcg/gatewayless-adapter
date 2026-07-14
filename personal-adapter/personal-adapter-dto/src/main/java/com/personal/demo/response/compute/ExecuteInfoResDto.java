package com.personal.demo.response.compute;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.List;

/**
 * @author sulu
 * @date 2026年02月10日 3:49 PM
 */
@Data
@Slf4j
public class ExecuteInfoResDto implements Serializable {

    /**
     * 执行的规则列表
     */
    private List<Object> executedRuleList;

    /**
     * 执行列表
     */
    private List<ExecuteItemResDTO> executeList;

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


    /**
     * 调试方法：测试从JSON解析
     */
    public static ExecuteInfoResDto fromJson(String json) {
        try {
            com.google.gson.Gson gson = new com.google.gson.GsonBuilder()
                .serializeNulls()  // 保留null值
                .setPrettyPrinting()
                .create();
            return gson.fromJson(json, ExecuteInfoResDto.class);
        } catch (Exception e) {
            log.error("ExecuteInfoDTO JSON解析失败: {}", e.getMessage(), e);
            return null;
        }
    }
}
