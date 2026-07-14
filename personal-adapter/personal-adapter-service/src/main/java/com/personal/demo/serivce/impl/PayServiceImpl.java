package com.personal.demo.serivce.impl;

import com.personal.demo.adapter.internal.pay.convert.PayConvert;
import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.pojo.base.BasePayRequest;
import com.personal.demo.pojo.dto.pay.*;
import com.personal.demo.request.pay.PayPrepareRequest;
import com.personal.demo.request.pay.PayQueryRequest;
import com.personal.demo.request.pay.PayRefundQueryRequest;
import com.personal.demo.request.pay.PayRefundRequest;
import com.personal.demo.response.pay.PayPrepareResultDto;
import com.personal.demo.response.pay.PayQueryResultDto;
import com.personal.demo.response.pay.PayRefundQueryResultDto;
import com.personal.demo.response.pay.PayRefundResultDto;
import com.personal.demo.serivce.AbstractPayMD5Service;
import com.personal.demo.serivce.IPayService;
import org.springframework.stereotype.Service;

@Service
public class PayServiceImpl implements IPayService {

    private final AbstractPayMD5Service md5Service;

    public PayServiceImpl(AbstractPayMD5Service payMD5Service) {
        this.md5Service = payMD5Service;
    }

    @Override
    public PayPrepareResultDto payPrepare(PayPrepareRequest request, BaseNode node) throws Exception {
        PayPrepareReqDto reqDto = PayConvert.INSTANCE.convertPayPrepareReqDto(request);
        BasePayRequest basePayRequest = md5Service.executeSign(reqDto, node, request.getBaid());
        PayPrepareRespDto respDto = md5Service.execute(basePayRequest, node, reqDto.orderId(), PayPrepareRespDto.class);
        return PayConvert.INSTANCE.convertPayPrepareResultDto(respDto);
    }

    @Override
    public PayQueryResultDto payQuery(PayQueryRequest request, BaseNode node) throws Exception {
        PayQueryReqDto reqDto = PayConvert.INSTANCE.convertPayQueryReqDto(request);
        BasePayRequest basePayRequest = md5Service.executeSign(reqDto, node, request.getBaid());
        PayQueryRespDto respDto = md5Service.execute(basePayRequest, node, reqDto.orderId(), PayQueryRespDto.class);
        return PayConvert.INSTANCE.convertPayQueryResultDto(respDto);
    }

    @Override
    public PayRefundResultDto refund(PayRefundRequest request, BaseNode node) throws Exception {
        PayRefundReqDto reqDto = PayConvert.INSTANCE.convertPayRefundReqDto(request);
        BasePayRequest basePayRequest = md5Service.executeSign(reqDto, node, request.getBaid());
        PayRefundRespDto respDto = md5Service.execute(basePayRequest, node, reqDto.orderId(), PayRefundRespDto.class);
        return PayConvert.INSTANCE.convertPayRefundResultDto(respDto);
    }

    @Override
    public PayRefundQueryResultDto refundQuery(PayRefundQueryRequest request, BaseNode node) throws Exception {
        PayRefundQueryReqDto reqDto = PayConvert.INSTANCE.convertPayRefundQueryReqDto(request);
        BasePayRequest basePayRequest = md5Service.executeSign(reqDto, node, request.getBaid());
        PayRefundQueryRespDto respDto = md5Service.execute(basePayRequest, node, reqDto.orderId(), PayRefundQueryRespDto.class);
        return PayConvert.INSTANCE.convertPayRefundQueryResultDto(respDto);


    }
}
