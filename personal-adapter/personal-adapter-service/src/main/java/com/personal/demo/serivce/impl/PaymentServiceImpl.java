package com.personal.demo.serivce.impl;

import com.personal.demo.adapter.internal.payment.convert.PaymentConvert;
import com.personal.demo.conf.GsonFactory;
import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.pojo.base.VendorBaseResponse;
import com.personal.demo.pojo.dto.payment.*;
import com.personal.demo.request.payment.ImportPaymentClassificationParams;
import com.personal.demo.request.payment.ImportPaymentOrderParams;
import com.personal.demo.request.payment.PaymentOrderDetailParams;
import com.personal.demo.response.payment.PaymentClassificationResultDto;
import com.personal.demo.response.payment.PaymentOrderDetailResultDto;
import com.personal.demo.response.payment.PaymentOrderResultDto;
import com.personal.demo.serivce.AbstractVendorAuthService;
import com.personal.demo.serivce.IPaymentService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("paymentService")
@Slf4j
public class PaymentServiceImpl implements IPaymentService {

    private final static String ACK = "ack";
    private final AbstractVendorAuthService vendorAuthService;
    private final PaymentConvert paymentConvert;

    private final Gson gson = GsonFactory.getInstance();

    public PaymentServiceImpl(AbstractVendorAuthService vendorAuthService, PaymentConvert paymentConvert) {
        this.vendorAuthService = vendorAuthService;
        this.paymentConvert = paymentConvert;
    }

    @Override
    public PaymentClassificationResultDto importClassification(ImportPaymentClassificationParams params, BaseNode node) throws Exception {
        PaymentClassificationReqDto reqDto = paymentConvert.convertImportPaymentClassificationParams(params);
        Map<String, Object> authParam = vendorAuthService.executeAuthParam(node);
        log.info("importClassification req: {}", gson.toJson(reqDto));
        VendorBaseResponse<?> vendorBaseResponse = vendorAuthService.executeResult(authParam, gson.toJson(reqDto), node);
        log.info("importClassification resp: {}", gson.toJson(vendorBaseResponse));
        PaymentClassificationRespDto respDto = gson.fromJson(gson.toJson(vendorBaseResponse), PaymentClassificationRespDto.class);
        return paymentConvert.convertImportPaymentClassificationResult(respDto);
    }

    @Override
    public PaymentOrderResultDto importPaymentOrder(ImportPaymentOrderParams params, BaseNode node) throws Exception {
        PaymentOrderReqDto reqDto = paymentConvert.convertImportPaymentOrderParams(params);
        Map<String, Object> authParam = vendorAuthService.executeAuthParam(node);
        log.info("importPaymentOrder req: {}", gson.toJson(reqDto));
        VendorBaseResponse<?> vendorBaseResponse = vendorAuthService.executeResult(authParam, gson.toJson(reqDto), node);
        log.info("importPaymentOrder resp: {}", gson.toJson(vendorBaseResponse));
        PaymentOrderRespDto respDto = gson.fromJson(gson.toJson(vendorBaseResponse), PaymentOrderRespDto.class);
        return paymentConvert.convertImportPaymentOrderResult(respDto);
    }

    @Override
    public PaymentOrderDetailResultDto queryPaymentOrderDetail(PaymentOrderDetailParams params, BaseNode node) throws Exception {
        PaymentOrderDetailReqDto reqDto = paymentConvert.convertImportPaymentOrderParams(params);
        Map<String, Object> authParam = vendorAuthService.executeAuthParam(node);
        log.info("queryPaymentOrderDetail req: {}", gson.toJson(reqDto));
        VendorBaseResponse<?> vendorBaseResponse = vendorAuthService.executeResult(authParam, gson.toJson(reqDto), node);
        log.info("queryPaymentOrderDetail resp: {}", gson.toJson(vendorBaseResponse));
        PaymentDetailRespDto respDto = gson.fromJson(gson.toJson(vendorBaseResponse), PaymentDetailRespDto.class);
        return paymentConvert.convertPaymentOrderResult(respDto);
    }
}
