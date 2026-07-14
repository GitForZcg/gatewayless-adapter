package com.personal.demo.serivce.impl;

import com.personal.demo.conf.BaseUrlConfig;
import com.personal.demo.conf.GsonFactory;
import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.pojo.base.DemoComputeMd5Param;
import com.personal.demo.serivce.AbstractComputeMD5Service;
import com.personal.demo.serivce.AppSecretService;
import com.personal.demo.utils.HttpUtil;
import com.personal.demo.utils.MD5Utils;
import com.common.base.exception.BizException;
import com.common.tools.GsonUtils;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.personal.demo.consts.AdapterConstant.TIMEZONE;
import static com.personal.demo.consts.HttpConstant.HEADER_TIMESTAMP;
import static com.personal.demo.consts.HttpConstant.HEADER_TOKEN;
import static com.personal.demo.serivce.impl.AbstractMemberMD5ServiceImpl.MAX_LOG_LENGTH;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/8/12 09:57
 */
@Service
@Slf4j
public class AbstractComputeMD5ServiceImpl implements AbstractComputeMD5Service {

    private final AppSecretService appSecretService;
    private final BaseUrlConfig config;
    private static final String STATUS = "status";
    private static final String DATA = "data";
    private static final String SUCCESS = "success";
    private static final String MESSAGE = "message";

    private final Gson gson = GsonFactory.getInstance();

    public AbstractComputeMD5ServiceImpl(AppSecretService appSecretService, BaseUrlConfig config) {
        this.appSecretService = appSecretService;
        this.config = config;
    }

    @Override
    public <T extends DemoComputeMd5Param> Map<String, Object> executeNoAccess(T reqDto, BaseNode node) {
        Map<String, Object> resultMap = new HashMap<>();
        long timeStamp = ZonedDateTime.now(ZoneId.of(TIMEZONE)).toEpochSecond();
        log.info("算价调用适配样例请求地址:{},参数：{}", node.url(), GsonUtils.beanToJson(reqDto));
        resultMap = HttpUtil.vendorPostForEntity(config.getComputeBaseUrl() + node.url(), gson.toJson(reqDto));
        log.info("算价调用适配样例请求地址:{},请求结果：{}", node.url(), resultMap);
        if (resultMap.get(STATUS) == null || !Objects.equals(200, resultMap.get(STATUS))) {
            resultMap.put(STATUS, SUCCESS);
        }
        computePrice(resultMap.toString(), "算价调用适配样例请求算价方案（原始数据），出参");
        return resultMap;
    }

    @Override
    public <T extends DemoComputeMd5Param> T executeResult(Map<String, Object> map, Class<T> clazz) {
        Object status = map.get(STATUS);
        if (!SUCCESS.equals(status.toString())) {
            throw new BizException("返回参数解析失败，失败原因：", map.get(MESSAGE).toString());
        }
        Object data = map.get(DATA);
        log.info("算价调用适配样例请求结果：{}", GsonUtils.beanToJson(data));
        return gson.fromJson(gson.toJson(data), clazz);
    }

    @Override
    public <T extends DemoComputeMd5Param> List<T> executeResultList(Map<String, Object> map, Class<T> clazz) {
        Object status = map.get(STATUS);
        if (status == null || !SUCCESS.equals(status.toString())) {
            String msg = map.get(MESSAGE) == null ? "未知错误" : map.get(MESSAGE).toString();
            throw new BizException("", msg);
        }
        Object data = map.get(DATA);
        log.info("算价调用适配样例请求结果：{}", GsonUtils.beanToJson(data));
        computePrice(data.toString(), "调用适配器请求算价方案（原始数据），出参");
        try {
            Type listType = new ParameterizedType() {
                @Override
                public Type[] getActualTypeArguments() {
                    // 指定List的泛型参数为传入的clazz（比如你的DemoComputeMd5Param子类）
                    return new Type[]{clazz};
                }

                @Override
                public Type getRawType() {
                    // 原始类型为List
                    return List.class;
                }

                @Override
                public Type getOwnerType() {
                    // 无所有者类型，返回null
                    return null;
                }
            };

            // 优化数据转换逻辑
            String json;
            if (data instanceof String) {
                json = (String) data;
            } else if (data == null) {
                return List.of(); // 返回空列表，避免解析null报错
            } else {
                json = gson.toJson(data);
            }

            // 用手动构建的listType解析，Gson能正确识别元素类型，不再是LinkedTreeMap
            return gson.fromJson(json, listType);
        } catch (Exception e) {
            log.error("返回参数列表转换失败: {}", e.getMessage(), e);
            throw new BizException("500", "返回参数列表转换失败: " + e.getMessage());
        }


    }

    @Override
    public <T extends DemoComputeMd5Param> Boolean executeResultBoolean(Map<String, Object> map){
        return checkState(map);
    }

    private Map<String, Object> buildApiHeader(long timestamp, String secret) {
        Map<String, Object> apiHeader = new HashMap<>();
        apiHeader.put(HEADER_TIMESTAMP, timestamp + "");
        apiHeader.put(HEADER_TOKEN, MD5Utils.MD5(timestamp + "", secret));
        return apiHeader;
    }


    private boolean checkState(Map<String, Object> response) {
        if (ObjectUtils.isEmpty(response)) {
            log.error("资产上报结果：会员信息上传失败");
            return Boolean.FALSE;
        }
        String status = response.get(STATUS).toString();
        if (SUCCESS.equals(status)) {
            log.info("资产上报结果：会员信息上传成功");
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public void computePrice(String param, String message) {
        int maxChunkSize = MAX_LOG_LENGTH; // 单条日志最大长度
        for (int i = 0; i < param.length(); i += maxChunkSize) {
            int endIndex = Math.min(i + maxChunkSize, param.length());
            log.info(message +"分段[{}]: {}", (i/maxChunkSize)+1, param.substring(i, endIndex));
        }
    }
}
