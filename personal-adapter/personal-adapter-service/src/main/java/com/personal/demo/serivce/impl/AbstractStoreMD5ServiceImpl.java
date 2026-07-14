package com.personal.demo.serivce.impl;

import com.personal.demo.conf.BaseUrlConfig;
import com.personal.demo.conf.GsonFactory;
import com.personal.demo.consts.Separator;
import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.pojo.base.AccessKeyConfig;
import com.personal.demo.pojo.base.DemoStoreMd5Param;
import com.personal.demo.serivce.AbstractStoreMD5Service;
import com.personal.demo.serivce.AppSecretService;
import com.personal.demo.utils.HttpUtil;
import com.personal.demo.utils.MD5Utils;
import com.common.base.exception.BizException;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

import static com.personal.demo.consts.AdapterConstant.DB_SUFFIX;
import static com.personal.demo.consts.AdapterConstant.TIMEZONE;
import static com.personal.demo.consts.HttpConstant.HEADER_TIMESTAMP;
import static com.personal.demo.consts.HttpConstant.HEADER_TOKEN;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/8/12 09:57
 */
@Service
@Slf4j
public class AbstractStoreMD5ServiceImpl implements AbstractStoreMD5Service {

    private final AppSecretService appSecretService;
    private final BaseUrlConfig config;
    private static final String STATUS = "status";
    private static final String DATA = "data";
    private static final String SUCCESS = "success";
    private static final String MESSAGE = "message";

    private final Gson gson = GsonFactory.getInstance();

    public AbstractStoreMD5ServiceImpl(AppSecretService appSecretService, BaseUrlConfig config) {
        this.appSecretService = appSecretService;
        this.config = config;
    }

    @Override
    public <T extends DemoStoreMd5Param> Map<String, Object> execute(T reqDto, BaseNode node) {
        String serviceName = node.name().split(Separator.UNDERSCORE)[0].concat(DB_SUFFIX).toLowerCase();
        AccessKeyConfig accessKeyConfig = appSecretService.getSecretByServiceName(serviceName);
        if (accessKeyConfig == null) {
            throw new BizException("456", "门店MD5加签:获取密钥配置失败");
        }
        long timeStamp = ZonedDateTime.now(ZoneId.of(TIMEZONE)).toEpochSecond();
        Map<String, Object> apiHeader = buildApiHeader(timeStamp, accessKeyConfig.getSecret());
        return HttpUtil.postEntity(config.getStoreBaseUrl() + node.url(), gson.toJson(reqDto), apiHeader);
    }

    @Override
    public <T extends DemoStoreMd5Param> T executeResult(Map<String, Object> map, Class<T> clazz) {
        Object status = map.get(STATUS);
        if (!SUCCESS.equals(status.toString())) {
            throw new BizException("", map.get(MESSAGE).toString());
        }
        Object data = map.get(DATA);
        return gson.fromJson(gson.toJson(data), clazz);
    }

    private Map<String, Object> buildApiHeader(long timestamp, String secret) {
        Map<String, Object> apiHeader = new HashMap<>();
        apiHeader.put(HEADER_TIMESTAMP, timestamp + "");
        apiHeader.put(HEADER_TOKEN, MD5Utils.MD5(timestamp + "", secret));
        return apiHeader;
    }
}
