package com.personal.demo.serivce;

import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.request.pay.*;
import com.personal.demo.response.pay.PayPrepareResultDto;
import com.personal.demo.response.pay.PayQueryResultDto;
import com.personal.demo.response.pay.PayRefundQueryResultDto;
import com.personal.demo.response.pay.PayRefundResultDto;

public interface IPayService {

    PayPrepareResultDto payPrepare(PayPrepareRequest request, BaseNode node) throws Exception;

    PayQueryResultDto payQuery(PayQueryRequest request, BaseNode node) throws Exception;

    PayRefundResultDto refund(PayRefundRequest request, BaseNode node) throws Exception;

    PayRefundQueryResultDto refundQuery(PayRefundQueryRequest request, BaseNode node) throws Exception;
}
