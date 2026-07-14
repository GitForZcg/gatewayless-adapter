package com.personal.demo.adapter.internal.payment.convert;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.personal.demo.consts.DateFormatConstant;
import com.personal.demo.dto.payment.GoodsDetailFileDto;
import com.personal.demo.enu.payment.SettlementMethodEnum;
import com.personal.demo.pojo.dto.payment.*;
import com.personal.demo.request.payment.ImportPaymentClassificationParams;
import com.personal.demo.request.payment.ImportPaymentOrderParams;
import com.personal.demo.request.payment.PaymentOrderDetailParams;
import com.personal.demo.response.payment.ErrorPayOrderDataResultDto;
import com.personal.demo.response.payment.PaymentClassificationResultDto;
import com.personal.demo.response.payment.PaymentOrderDetailResultDto;
import com.personal.demo.response.payment.PaymentOrderResultDto;
import com.common.tools.GsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author czs
 */
@Slf4j
@Component
public class PaymentConvert {
    private static final String CURRENCY = "CNY";
    private static final String CORP_TYPE = "NO_RECEIPT";
    private static final String ACCOUNT_TYPE = "CORP";
    private static final String PAYMENT_TYPE = "BANK";
    @Value("${vendor.expenseTypeBizCode}")
    private String expenseTypeBizCode;
    @Value("${vendor.tradingPartnerBizCode}")
    private String tradingPartnerBizCode;
    @Value("${vendor.formSubTypeBizCode}")
    private String formSubTypeBizCode;

    public static Long getForecastReceiptDateAfterFifteenDays() {
        return System.currentTimeMillis() + 15 * 24 * 60 * 60 * 1000L;
    }

    public static String getCurrentDateYyyyMmDd() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern(DateFormatConstant.FORMAT_NO_DATE));
    }

    /**
     * 将毫秒级时间戳转换为LocalDateTime
     *
     * @param timestamp 毫秒级时间戳
     * @return LocalDateTime对象
     */
    public static LocalDateTime toLocalDateTime(Long timestamp) {
        if (timestamp == null) {
            return null;
        }
        return LocalDateTime.ofInstant(
                Instant.ofEpochMilli(timestamp),
                ZoneId.systemDefault()
        );
    }

    /**
     * 转换导入费用结果
     *
     * @param params 供应商系统的结果
     * @return 我们自己系统的结果
     */
    public PaymentClassificationResultDto convertImportPaymentClassificationResult(PaymentClassificationRespDto params) {
        PaymentClassificationResultDto paymentClassificationResultDto = new PaymentClassificationResultDto();
        paymentClassificationResultDto.setCode(params.getCode());
        paymentClassificationResultDto.setMessage(params.getMessage());
        paymentClassificationResultDto.setSuccess(params.getSuccess());
        DataRespDto dataRespDto = params.getData();
        if (dataRespDto != null) {
            paymentClassificationResultDto.setErrorData(dataRespDto.getErrorData());
            paymentClassificationResultDto.setSuccessData(dataRespDto.getSuccessData());
        }
        return paymentClassificationResultDto;
    }

    /**
     * 转换导入付款单结果
     *
     * @param params 供应商系统的结果
     * @return 我们自己系统的结果
     */
    public PaymentOrderResultDto convertImportPaymentOrderResult(PaymentOrderRespDto params) {
        PaymentOrderResultDto paymentOrderResultDto = new PaymentOrderResultDto();
        paymentOrderResultDto.setCode(params.getCode());
        paymentOrderResultDto.setMessage(params.getMessage());
        paymentOrderResultDto.setSuccess(params.getSuccess());
        if (params.getSuccess() && Objects.nonNull(params.getData())) {
            paymentOrderResultDto.setSuccessData((String) params.getData());
        } else if (!params.getSuccess() && Objects.nonNull(params.getData())) {
            List<ErrorPayOrderDataResultDto> orderDataResults = JSONUtil.toList((String) params.getData(), ErrorPayOrderDataResultDto.class);
            if (CollectionUtil.isNotEmpty(orderDataResults)) {
                paymentOrderResultDto.setErrorData(orderDataResults.get(0));
            }
        }
        return paymentOrderResultDto;
    }

    /**
     * 转换付款单入参
     *
     * @param params 我们自己系统的入参
     * @return 供应商系统的入参
     */
    public PaymentOrderDetailReqDto convertImportPaymentOrderParams(PaymentOrderDetailParams params) {
        PaymentOrderDetailReqDto paymentOrderDetailReqDto = new PaymentOrderDetailReqDto();
        paymentOrderDetailReqDto.setFormCode(params.getFormCode());
        paymentOrderDetailReqDto.setEnableExpenseList(true);
        paymentOrderDetailReqDto.setEnableInvoiceList(true);
        return paymentOrderDetailReqDto;
    }

    /**
     * 转换付款单结果
     *
     * @param params 供应商系统的结果
     * @return 我们自己系统的结果
     */
    public PaymentOrderDetailResultDto convertPaymentOrderResult(PaymentDetailRespDto params) {
        PaymentOrderDetailResultDto paymentOrderDetailResultDto = new PaymentOrderDetailResultDto();
        paymentOrderDetailResultDto.setCode(params.getCode());
        paymentOrderDetailResultDto.setMessage(params.getMessage());
        if (StringUtils.isNotBlank(params.getMessage()) || Objects.isNull(params.getData())) {
            return paymentOrderDetailResultDto;
        }
        PaymentOrderDetailRespDto respDto = params.getData();
        paymentOrderDetailResultDto.setFormDataCode(respDto.getFormDataCode());
        paymentOrderDetailResultDto.setFormCode(respDto.getFormCode());
        CustomObjectRespDto customObject = respDto.getCustomObject();
        if (customObject != null && customObject.getCF2() != null && customObject.getCF2().getBusinessCode() != null) {
            // 转换成我们系统的
            paymentOrderDetailResultDto.setSettlementMethod(SettlementMethodEnum.getCodeByDesc(customObject.getCF2().getDetailBusinessCode()));
        }
        paymentOrderDetailResultDto.setFormStatus(respDto.getFormStatus());
        paymentOrderDetailResultDto.setSettledAt(toLocalDateTime(respDto.getSettledAt()));
        List<ExpenseInputRespDto> expenseList = respDto.getExpenseList();
        if (CollectionUtil.isNotEmpty(expenseList)) {
            //设置核销付款单单号
            ExpenseInputRespDto expenseInputRespDto = expenseList.get(0);
            paymentOrderDetailResultDto.setExpenseTypeName(expenseInputRespDto.getExpenseTypeName());
            paymentOrderDetailResultDto.setExpenseTypeBizCode(expenseInputRespDto.getExpenseTypeBizCode());

            List<ReceiptDeductionInputRespDto> receiptedDeductionList = expenseInputRespDto.getReceiptedDeductionList();
            if (CollectionUtil.isNotEmpty(receiptedDeductionList)) {
                ReceiptDeductionInputRespDto receiptDeductionInputRespDto = receiptedDeductionList.get(0);
                paymentOrderDetailResultDto.setReimburseCode(receiptDeductionInputRespDto.getReimburseCode());
                paymentOrderDetailResultDto.setDeductionDate(toLocalDateTime(receiptDeductionInputRespDto.getDeductionDate()));
            }
            //核销单的返回结果
            List<InvoiceInputRespDto> invoiceList = expenseInputRespDto.getInvoiceList();
            if (CollectionUtil.isNotEmpty(invoiceList)) {
                List<InvoiceInfoRespDto> invoiceInfoList = invoiceList.stream().map(InvoiceInputRespDto::getInvoiceInfo).filter(Objects::nonNull).toList();
                //金额
                BigDecimal totalPriceAmount = invoiceInfoList.stream().map(InvoiceInfoRespDto::getTotalPriceAmount).filter(StringUtils::isNotBlank).map(BigDecimal::new).reduce(BigDecimal.ZERO, BigDecimal::add);
                paymentOrderDetailResultDto.setTotalPriceAmount(totalPriceAmount);
                //价税合计
                BigDecimal totalPriceAndTax = invoiceInfoList.stream().map(InvoiceInfoRespDto::getTotalPriceAndTax).filter(StringUtils::isNotBlank).map(BigDecimal::new).reduce(BigDecimal.ZERO, BigDecimal::add);
                paymentOrderDetailResultDto.setTotalPriceAndTax(totalPriceAndTax);
                //税额
                BigDecimal totalTaxAmount = invoiceInfoList.stream().map(InvoiceInfoRespDto::getTotalTaxAmount).filter(StringUtils::isNotBlank).map(BigDecimal::new).reduce(BigDecimal.ZERO, BigDecimal::add);
                paymentOrderDetailResultDto.setTotalTaxAmount(totalTaxAmount);
            }
        }
        return paymentOrderDetailResultDto;
    }

    /**
     * 转换导入费用入参
     *
     * @param params 我们自己系统的入参
     * @return 供应商系统的入参
     */
    public PaymentClassificationReqDto convertImportPaymentClassificationParams(ImportPaymentClassificationParams params) {
        PaymentClassificationReqDto paymentClassificationReqDto = new PaymentClassificationReqDto();
        paymentClassificationReqDto.setEmployeeId(params.getPayOrderEmployeeNo());
        List<ExpenseReqDto> expenseList = new ArrayList<>();
        params.getImportPaymentClassifications().forEach(par -> {
            ExpenseReqDto expenseReqDto = new ExpenseReqDto();
            ConsumeAmountReqDto consumeAmountReqDto = new ConsumeAmountReqDto();
            consumeAmountReqDto.setAmount(String.valueOf(par.getPayableTotalAmount()));
            consumeAmountReqDto.setCurrency(CURRENCY);
            expenseReqDto.setConsumeAmount(consumeAmountReqDto);
            expenseReqDto.setExpenseTypeBizCode(expenseTypeBizCode);
            expenseReqDto.setCorpExpense(true);
            expenseReqDto.setCorpType(CORP_TYPE);
            expenseReqDto.setCorpExpenseResponsibleEmpIds(List.of(params.getPayOrderEmployeeNo(), params.getPayOrderEmployeeNo()));
            expenseReqDto.setNonReceiptAmount(consumeAmountReqDto);
            expenseReqDto.setForecastReceiptDate(getForecastReceiptDateAfterFifteenDays());
            expenseReqDto.setTradingPartnerBizCode(tradingPartnerBizCode);
            // 消费地点是否去掉待确定
            expenseList.add(expenseReqDto);
        });
        paymentClassificationReqDto.setExpenseList(expenseList);
        return paymentClassificationReqDto;
    }

    /**
     * 转换导入付款单入参
     *
     * @param params 我们自己系统的入参
     * @return 供应商系统的入参
     */
    public PaymentOrderReqDto convertImportPaymentOrderParams(ImportPaymentOrderParams params) {
        PaymentOrderReqDto paymentOrderReqDto = new PaymentOrderReqDto();
        paymentOrderReqDto.setFormCode(params.getPaymentOrderNo());
        paymentOrderReqDto.setReimburseName(params.getStoreName() + params.getSettlementPeriodMonth() + "付款单");
        paymentOrderReqDto.setFormSubTypeBizCode(formSubTypeBizCode);
        paymentOrderReqDto.setSubmittedUserEmployeeId(params.getPayOrderEmployeeNo());
        paymentOrderReqDto.setLegalEntityBizCode(params.getBusinessHeader());
        paymentOrderReqDto.setCoverUserEmployeeId(params.getPayOrderEmployeeNo());
        paymentOrderReqDto.setCoverDepartmentBizCode(params.getStoreCode());
        PayeeAccountReqDto payeeAccountReqDto = new PayeeAccountReqDto();
        payeeAccountReqDto.setAccountType(ACCOUNT_TYPE);
        payeeAccountReqDto.setPaymentType(PAYMENT_TYPE);
        payeeAccountReqDto.setBankAcctName(params.getPayeeAccount());
        payeeAccountReqDto.setBankAcctNumber(params.getPayeeBankAccountNumber());
        paymentOrderReqDto.setPayeeAccount(payeeAccountReqDto);
        paymentOrderReqDto.setExpenseCodes(params.getExpenseCodes());
        CustomObjectReqDto customObjectReqDto = new CustomObjectReqDto();
        // 结转方式测试和生成是否一样
        customObjectReqDto.setCF2(SettlementMethodEnum.getDescByCode(params.getSettlementMethod()));
        GoodsDetailFileDto fileDto = params.getFileDto();
        if (fileDto != null) {
            FileReqDto fileReqDto = new FileReqDto();
            fileReqDto.setUrl(fileDto.getUrl());
            fileReqDto.setFileType(".xlsx");
            if (StringUtils.isNotBlank(fileDto.getFileKey())) {
                try {
                    fileReqDto.setFileName(fileDto.getFileKey().split("\\.")[0]);
                } catch (Exception e) {
                    log.error("从文件fileKey解析文件名异常，params: {}", GsonUtils.beanToJson(params), e);
                }
            }
            customObjectReqDto.setCF4(fileReqDto);
        }
        customObjectReqDto.setCF31(params.getStoreCode());
        paymentOrderReqDto.setCustomObject(customObjectReqDto);
        paymentOrderReqDto.setTradingPartnerBizCode(tradingPartnerBizCode);
        paymentOrderReqDto.setNonCheckContractAgent(true);
        return paymentOrderReqDto;
    }
}
