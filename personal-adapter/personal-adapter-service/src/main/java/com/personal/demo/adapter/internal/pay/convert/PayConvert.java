package com.personal.demo.adapter.internal.pay.convert;

import com.personal.demo.pojo.dto.pay.*;
import com.personal.demo.request.pay.PayPrepareRequest;
import com.personal.demo.request.pay.PayQueryRequest;
import com.personal.demo.request.pay.PayRefundQueryRequest;
import com.personal.demo.request.pay.PayRefundRequest;
import com.personal.demo.response.pay.PayPrepareResultDto;
import com.personal.demo.response.pay.PayQueryResultDto;
import com.personal.demo.response.pay.PayRefundQueryResultDto;
import com.personal.demo.response.pay.PayRefundResultDto;
import com.personal.pay.pojo.request.PaymentNotifyResultRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/11/6 14:01
 */
@Mapper
public interface PayConvert {

    PayConvert INSTANCE = Mappers.getMapper(PayConvert.class);

    PayPrepareReqDto convertPayPrepareReqDto(PayPrepareRequest request);

    PayPrepareResultDto convertPayPrepareResultDto(PayPrepareRespDto response);

    PayQueryReqDto convertPayQueryReqDto(PayQueryRequest request);

    PayQueryResultDto convertPayQueryResultDto(PayQueryRespDto respDto);

    PayRefundReqDto convertPayRefundReqDto(PayRefundRequest request);

    PayRefundResultDto convertPayRefundResultDto(PayRefundRespDto respDto);

    PayRefundQueryReqDto convertPayRefundQueryReqDto(PayRefundQueryRequest request);

    PayRefundQueryResultDto convertPayRefundQueryResultDto(PayRefundQueryRespDto respDto);
    PaymentNotifyResultRequest convertNotifyResultDto(PayResultDto respDto);
}
