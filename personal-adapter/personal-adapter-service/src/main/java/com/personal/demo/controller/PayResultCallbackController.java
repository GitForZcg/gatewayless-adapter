package com.personal.demo.controller;

import com.personal.demo.conf.GsonFactory;
import com.personal.demo.pojo.base.AccessKeyConfig;
import com.personal.demo.rule.handler.WhitelistServiceHandler;
import com.personal.demo.serivce.AppSecretService;
import com.personal.demo.utils.SignatureUtil;
import com.common.base.anno.UnWrapResult;
import com.google.gson.Gson;
import com.personal.pay.IPersonalPayApi;
import com.personal.pay.pojo.request.PaymentNotifyResultRequest;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.AccessDeniedException;
import java.util.Map;
import java.util.Objects;

import static com.personal.demo.consts.PayConstant.*;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 支付回调结果接收
 * @date 2025/11/13 10:54
 */
@RestController
@RequestMapping("/notify")
@Slf4j
public class PayResultCallbackController {

    private final AppSecretService appSecretService;
    private final Gson gson = GsonFactory.getInstance();
    private final WhitelistServiceHandler whitelistService;

    @Resource
    IPersonalPayApi payApi;

    public PayResultCallbackController(AppSecretService appSecretService, WhitelistServiceHandler whitelistService) {
        this.appSecretService = appSecretService;
        this.whitelistService = whitelistService;
    }

    @UnWrapResult
    @PostMapping(path = "result", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String notifyResult(@RequestParam Map<String, Object> params, HttpServletRequest request) throws AccessDeniedException {
        // 使用支持域名的验证方法
        boolean hasAccess = whitelistService.validateRequest(request);
        if (!hasAccess) {
            throw new AccessDeniedException("禁止访问");
        }
        if (ObjectUtils.isEmpty(params)) {
            log.error("支付回调参数不正确");
            return NOTIFY_FAILED;
        }
        AccessKeyConfig accessKey = appSecretService.getSecretByServiceName(NOTIFY_SERVICE_NAME);
        String sign = (String) params.remove(RESPONSE_SIGN);
        String contentStr = SignatureUtil.getSortedExclusiveBlankStr(params);
        String verifySign = DigestUtils.md5Hex(contentStr + accessKey.getSecret());
        if (!Objects.equals(sign, verifySign)) {
            log.error("支付回调通知验签不通过");
            return NOTIFY_FAILED;
        }
        PaymentNotifyResultRequest notifyResultRequest = gson.fromJson(gson.toJson(params), PaymentNotifyResultRequest.class);
        return payApi.notifyResult(notifyResultRequest);
    }
}
