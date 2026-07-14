package com.personal.demo.serivce.impl;

import com.personal.demo.conf.BaseUrlConfig;
import com.personal.demo.conf.GsonFactory;
import com.personal.demo.consts.Separator;
import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.pojo.base.AccessKeyConfig;
import com.personal.demo.pojo.base.BaseMemberPublicParam;
import com.personal.demo.pojo.base.BaseTradePublicParam;
import com.personal.demo.serivce.AbstractMemberMD5Service;
import com.personal.demo.serivce.AppSecretService;
import com.personal.demo.util.TypeHelperUtil;
import com.personal.demo.utils.HttpUtil;
import com.personal.demo.utils.SignUtils;
import com.common.base.exception.BizException;
import com.common.tools.GsonUtils;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.personal.demo.consts.AdapterConstant.DB_SUFFIX;
import static com.personal.demo.consts.AdapterConstant.TIMEZONE;

@Service
@Slf4j
public class AbstractMemberMD5ServiceImpl implements AbstractMemberMD5Service {

    private final AppSecretService appSecretService;
    private final Gson gson = GsonFactory.getInstance();
    private final BaseUrlConfig config;
    public static final int MAX_LOG_LENGTH = 13000;

    private final static String APPID = "appId";
    private final static String REQ = "req";
    private final static String VERSION = "v";
    private final static String VERSION_VALUE = "2.0";
    private final static String TIMESTAMP = "ts";
    private final static String SIGN = "sig";
    private final static String FORMAT_TYPE = "JSON";
    private final static String MESSAGE = "errmsg";
    private final static String CODE = "errcode";
    private final static String DATA = "res";
    private final static String SUCCESS = "0";

    public AbstractMemberMD5ServiceImpl(AppSecretService appSecretService, BaseUrlConfig config) {
        this.appSecretService = appSecretService;
        this.config = config;
    }

    @Override
    public <T extends BaseMemberPublicParam> LinkedHashMap<String, Object> executeSign(T reqDto, BaseNode node) {
        String serviceName = node.name().split(Separator.UNDERSCORE)[0].concat(DB_SUFFIX).toLowerCase();
        AccessKeyConfig accessKeyConfig = appSecretService.getSecretByServiceName(serviceName);
        if (accessKeyConfig == null) {
            throw new BizException("123", "MD5加签:获取会员密钥配置失败");
        }
        String json = gson.toJson(reqDto);
        log.info("MD5组装签名前原始数据json:{}", json);
        Map<String, Object> content = gson.fromJson(json, TypeHelperUtil.mapOf(String.class, Object.class));
        log.info("MD5组装签名前的转化数据json:{}", gson.toJson(content));
        LinkedHashMap<String, Object> dataMap = Maps.newLinkedHashMapWithExpectedSize(10);
        String appid = accessKeyConfig.getAppid();
        String appKey = accessKeyConfig.getSecret();
        long timestamp = ZonedDateTime.now(ZoneId.of(TIMEZONE)).toEpochSecond();
        String sign = SignUtils.sign(content, appid, appKey, timestamp);
        dataMap.put(REQ, json);
        dataMap.put(APPID, appid);
        dataMap.put(TIMESTAMP, timestamp);
        dataMap.put(SIGN, sign);
        return dataMap;
    }

    @Override
    public <T extends BaseTradePublicParam> LinkedHashMap<String, Object> executeSign(T reqDto, BaseNode node) {
        String serviceName = node.name().split(Separator.UNDERSCORE)[0].concat(DB_SUFFIX).toLowerCase();
        AccessKeyConfig accessKeyConfig = appSecretService.getSecretByServiceName(serviceName);
        if (accessKeyConfig == null) {
            throw new BizException("123", "交易MD5加签:获取交易密钥配置失败");
        }
        String json = gson.toJson(reqDto);
        log.info("交易MD5组装签名前原始数据json:{}", json);
        Map<String, Object> content = gson.fromJson(json, TypeHelperUtil.mapOf(String.class, Object.class));
        log.info("交易MD5组装签名前的转化数据json:{}", gson.toJson(content));
        LinkedHashMap<String, Object> dataMap = Maps.newLinkedHashMapWithExpectedSize(10);
        String appid = accessKeyConfig.getAppid();
        String appKey = accessKeyConfig.getSecret();
        long timestamp = ZonedDateTime.now(ZoneId.of(TIMEZONE)).toEpochSecond();
        String sign = SignUtils.sign(content, appid, appKey, timestamp);
        dataMap.put(REQ, json);
        dataMap.put(APPID, appid);
        dataMap.put(TIMESTAMP, timestamp);
        dataMap.put(SIGN, sign);
        return dataMap;
    }

    @Override
    public <R> R executeResult(LinkedHashMap<String, Object> dataMap, BaseNode node, Class<R> clazz) {
        String resData = getResData(dataMap, node);
        return GsonUtils.jsonToBean(resData, TypeHelperUtil.getType(clazz));
//        return gson.fromJson(resData, TypeHelperUtil.getType(clazz));
    }

    @Override
    public <R> R executeResult(LinkedHashMap<String, Object> dataMap, BaseNode node, Type type) {
        String resData = getResData(dataMap, node);
//        return gson.fromJson(resData, type);
        return GsonUtils.jsonToBean(resData, type);
    }

    @Override
    public <R> List<R> executeResultList(LinkedHashMap<String, Object> dataMap, BaseNode node, Class<R> clazz) {
        String resData = getResData(dataMap, node);
//        return gson.fromJson(resData, type);
        return GsonUtils.jsonToList(resData, clazz);
    }

    @Override
    public Map<String, Object> executeResultMap(LinkedHashMap<String, Object> dataMap, BaseNode node) {
        return getResponse(dataMap, node);
    }


    private String getResData(LinkedHashMap<String, Object> dataMap, BaseNode node) {

        Map<String, Object> response = getResponse(dataMap, node);

        String errorCode = response.get(CODE).toString();
        if (!SUCCESS.equals(errorCode)) {
            log.error("会员节点【 {} 】", node.nodeDesc());
            throw new BizException("501", response.get(MESSAGE).toString());
        }
        String resData = GsonUtils.beanToJson(response.get(DATA));
        log.info("会员[" + node.nodeDesc() + "]响应报文内容:{}", resData);
        syncItemParam(resData);
        return resData;
    }

    /**
     * 获取响应报文
     *
     * @param dataMap
     * @param node
     * @return
     */
    private Map<String, Object> getResponse(LinkedHashMap<String, Object> dataMap, BaseNode node) {

        String req = dataMap.remove(REQ).toString();
        String appid = dataMap.remove(APPID).toString();
        String sig = dataMap.remove(SIGN).toString();
        String timestamp = dataMap.remove(TIMESTAMP).toString();
        String requestContent = "req=" + req + "&appid=" + appid + "&v=" + VERSION_VALUE + "&ts=" + timestamp + "&sig=" + sig + "&fmt=" + FORMAT_TYPE;

        log.info("会员请求节点【 {} 】---请求参数: {}, appid: {}, sig: {}",
                node.nodeDesc(),
                GsonUtils.beanToJson(req),
                GsonUtils.beanToJson(appid),
                GsonUtils.beanToJson(sig));

        log.info("会员请求url:{}", config.getMemberBaseUrl() + node.url());

        Map<String, Object> response = HttpUtil.postMultipartFormData(config.getMemberBaseUrl() + node.url(), requestContent);

        log.info("AbstractMemberMD5ServiceImpl response content:{}", GsonUtils.beanToJson(response));

        if (ObjectUtils.isEmpty(response)) {
            log.error("会员节点【 {} 】--- sig: {}", node.nodeDesc(), sig);
            throw new BizException("", "会员节点[" + node.nodeDesc() + "]响应为空");
        }

        log.info("会员[" + node.nodeDesc() + "]响应报文内容:{}", GsonUtils.beanToJson(response));

        return response;
    }


    private void syncItemParam(String param) {
        int maxChunkSize = MAX_LOG_LENGTH; // 单条日志最大长度
        for (int i = 0; i < param.length(); i += maxChunkSize) {
            int endIndex = Math.min(i + maxChunkSize, param.length());
            log.info("会员请求返回分段[{}]: {}", (i / maxChunkSize) + 1, param.substring(i, endIndex));
        }
    }
}
