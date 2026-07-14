package com.personal.demo.serivce.impl;

import cn.hutool.core.codec.Base64Encoder;
import com.personal.demo.conf.BaseUrlConfig;
import com.personal.demo.consts.Separator;
import com.personal.demo.enu.RedisInvoiceKey;
import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.enu.invoice.InvoiceResponseCode;
import com.personal.demo.pojo.base.AccessKeyConfig;
import com.personal.demo.pojo.dto.invoice.InvoiceSubjectInfoReqDto;
import com.personal.demo.serivce.AppSecretService;
import com.personal.demo.serivce.InvoiceMD5Service;
import com.personal.demo.util.TypeHelperUtil;
import com.personal.demo.utils.HttpUtil;
import com.personal.demo.utils.InvoiceEncryptUtils;
import com.common.base.exception.BizException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.personal.demo.consts.AdapterConstant.DB_SUFFIX;
import static com.personal.demo.consts.Separator.SPLIT_SPECIAL_MARK;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/9/11 15:18
 */

@Service
public class InvoiceMD5ServiceImpl implements InvoiceMD5Service {

    private final AppSecretService appSecretService;
    private final BaseUrlConfig config;
    private final static String FIELD_SUCCESS = "success";
    private final static String FIELD_CODE = "code";
    private final static String FIELD_MSG = "msg";
    private final static Integer ConfirmCancel = 1;
    private final static String PARAM_SUFFIX = "?para=";

    private final Gson gsonWithNulls = new GsonBuilder().create();

    public InvoiceMD5ServiceImpl(AppSecretService appSecretService, BaseUrlConfig config) {
        this.appSecretService = appSecretService;
        this.config = config;
    }

    @Override
    public String executeSign(InvoiceSubjectInfoReqDto reqDto, BaseNode node) {
        String serviceName = node.name().split(Separator.UNDERSCORE)[0].concat(DB_SUFFIX).toLowerCase();
        AccessKeyConfig accessKeyConfig = appSecretService.getSecretByServiceName(serviceName);
        if (accessKeyConfig == null) {
            throw new BizException("KP998", "发票MD5加签:获取密钥配置失败");
        }
        String content = buildKpSign(reqDto);
        String secretKey = accessKeyConfig.getSecret();
        return InvoiceEncryptUtils.encryptASEMD5(content, secretKey).substring(0, 4);
    }

    @Override
    public Boolean executeResult(InvoiceSubjectInfoReqDto reqDto, BaseNode node, Integer cancelType) {

        String content = buildKpInfo(reqDto, cancelType);
        String requestUrl = config.getKpinvoiceBaseUrl() + node.url() + PARAM_SUFFIX;
        String newContent = Base64Encoder.encode(content.getBytes());
        newContent = newContent.replace("\r\n", "");
        String strUrlContent = requestUrl + newContent;
        String str = HttpUtil.getForEntity(strUrlContent);
        Map<String, Object> responseMap = gsonWithNulls.fromJson(str, TypeHelperUtil.mapOf(String.class, Object.class));
        if (ObjectUtils.isEmpty(responseMap)) {
            throw new BizException("KP999", "开票失败");
        }
        String code = responseMap.getOrDefault(FIELD_CODE, "").toString();
        String msg = responseMap.getOrDefault(FIELD_MSG, "").toString();
        if (InvoiceResponseCode.isFailure(code)) {
            throw new BizException(code, msg);
        }

        return (Boolean) responseMap.get(FIELD_SUCCESS);
    }

    /**
     * 按文档字段顺序拼接字符串
     * 非必填字段无值时传空串
     *
     * @param dto 开票详情请求对象
     * @return 拼接后的字符串
     */
    private String buildKpSign(InvoiceSubjectInfoReqDto dto) {
        return safeString(dto.getStrTenancyId()) + SPLIT_SPECIAL_MARK +
                safeString(dto.getInvoiceType()) + SPLIT_SPECIAL_MARK +
                safeString(dto.getDinType()) + SPLIT_SPECIAL_MARK +
                safeString(dto.getSceneNotify()) + SPLIT_SPECIAL_MARK +
                safeString(dto.getTotalInvoiceAmount()) + SPLIT_SPECIAL_MARK +
                safeString(gsonWithNulls.toJson(dto.getInfo()));

    }

    /**
     * 按文档字段顺序拼接字符串
     * 非必填字段无值时传空串
     *
     * @param dto 开票详情请求对象
     * @return 拼接后的字符串
     */
    private String buildKpInfo(InvoiceSubjectInfoReqDto dto, Integer cancelType) {
        String sb = safeString(dto.getStrTenancyId()) + SPLIT_SPECIAL_MARK +
                safeString(dto.getInvoiceType()) + SPLIT_SPECIAL_MARK +
                safeString(dto.getDinType()) + SPLIT_SPECIAL_MARK +
                safeString(dto.getSceneNotify()) + SPLIT_SPECIAL_MARK +
                safeString(dto.getTotalInvoiceAmount()) + SPLIT_SPECIAL_MARK +
                safeString(gsonWithNulls.toJson(dto.getInfo())) + SPLIT_SPECIAL_MARK;
        if (ConfirmCancel.equals(cancelType)) {
            sb += safeString(cancelType) + SPLIT_SPECIAL_MARK;
        }
        sb += safeString(dto.getSignKey());
        return sb;
    }


    /**
     * 安全字符串处理 null值转换为空串
     */
    private static String safeString(Object obj) {
        return obj == null ? "" : obj.toString();
    }
}
