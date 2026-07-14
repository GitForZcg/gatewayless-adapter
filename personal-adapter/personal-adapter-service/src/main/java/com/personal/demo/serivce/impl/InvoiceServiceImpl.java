package com.personal.demo.serivce.impl;

import com.personal.demo.conf.BaseUrlConfig;
import com.personal.demo.conf.GsonFactory;
import com.personal.demo.conf.RedisFactory;
import com.personal.demo.consts.Separator;
import com.personal.demo.enu.RedisStoreKey;
import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.pojo.base.AccessKeyConfig;
import com.personal.demo.pojo.dto.invoice.*;
import com.personal.demo.request.invoice.*;
import com.personal.demo.response.invoice.*;
import com.personal.demo.serivce.AppSecretService;
import com.personal.demo.serivce.IInvoiceService;
import com.personal.demo.serivce.InvoiceMD5Service;
import com.personal.demo.util.TypeHelperUtil;
import com.personal.demo.utils.DateTimeUtil;
import com.personal.demo.utils.HttpUtil;
import com.common.base.exception.BizException;
import com.common.redis.sdk.RedissionClient;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.personal.demo.consts.AdapterConstant.DB_SUFFIX;
import static com.personal.demo.enu.invoice.InvoiceStatus.PENDING_INVOICE;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/9/11 13:53
 */
@Service
@Slf4j
public class InvoiceServiceImpl implements IInvoiceService {
    private final InvoiceMD5Service invoiceMD5Service;
    private final AppSecretService appSecretService;
    private final RedissionClient commonClient = RedisFactory.getClient(RedisFactory.RedisClient.DEFAULT_1);
    private final Gson gson = GsonFactory.getInstance();
    private final BaseUrlConfig config;
    private final static String successCode = "0000";
    private final static Integer ConfirmCancel = 1;

    public InvoiceServiceImpl(InvoiceMD5Service invoiceMD5Service, AppSecretService appSecretService, BaseUrlConfig config) {
        this.invoiceMD5Service = invoiceMD5Service;
        this.appSecretService = appSecretService;
        this.config = config;
    }

    @Override
    public Boolean invoiceGet(InvoiceGetParams params, BaseNode node) throws Exception {
//        checkInvoiceLock(params.getNum(), params.getCancelType());
        //1、转化成适配样例的对象
        InvoiceSubjectInfoReqDto reqDto = new InvoiceSubjectInfoReqDto();
        reqDto.setStrTenancyId(getInvoiceMid(node));
        reqDto.setInvoiceType(3);
        reqDto.setDinType(1);
        reqDto.setSceneNotify("3");
        reqDto.setTotalInvoiceAmount(params.getInvoiceAmount());
        //门店code映射适配样例门店缓存
        Map<String, String> storeCodeMappings = getStoreCodeMappings();

        InvoiceDetailInfoReqDto reqDtoNew = convert(params, storeCodeMappings);
        reqDtoNew.setStrOrganId(storeCodeMappings.get(params.getStoreCode()));
        List<InvoiceDetailInfoReqDto> ls = List.of(reqDtoNew);
        reqDto.setInfo(ls);

        String signKey = invoiceMD5Service.executeSign(reqDto, node);
        reqDto.setSignKey(signKey);

        return invoiceMD5Service.executeResult(reqDto, node, null);
    }

//    private void checkInvoiceLock(String orderNo, Integer cancelType) {
//        if (ConfirmCancel.equals(cancelType)) {
//            boolean result = commonClient.hasKey(RedisInvoiceKey.INVOICE_UPDATE_LOCK.generateKey(orderNo));
//            if (result) {
//                throw new BizException("L90", "重开发票仅可执行一次");
//            }
//        }
//    }


    private InvoiceDetailInfoReqDto convert(InvoiceGetParams params, Map<String, String> storeCodeMappings) {
        InvoiceDetailInfoReqDto detailInfoReqDto = new InvoiceDetailInfoReqDto();
        detailInfoReqDto.setStrOrganId(storeCodeMappings.get(params.getStoreCode()));
        detailInfoReqDto.setSellerNum(params.getSellerNum());
        detailInfoReqDto.setNum(params.getNum());
        detailInfoReqDto.setGMFLX(params.getGmflx());
        detailInfoReqDto.setGMFSBH(params.getGmfsbh());
        detailInfoReqDto.setGMFMC(params.getGmfmc());
        detailInfoReqDto.setGMFDZ(params.getGmfdz());
        detailInfoReqDto.setGMFDH(params.getGmfdh());
        detailInfoReqDto.setGMFYH(params.getGmfyh());
        detailInfoReqDto.setGMFZH(params.getGmfzh());
        detailInfoReqDto.setGMFDZYX(params.getGmfdzyx());
        detailInfoReqDto.setStrReportDate(params.getStrReportDate());
        detailInfoReqDto.setInvoiceAmount(params.getInvoiceAmount());
        detailInfoReqDto.setRemainInvoiceAmount(params.getRemainInvoicePrice());
        detailInfoReqDto.setTotalAmount(params.getPayableAmount());
        return detailInfoReqDto;

    }

    @Override
    public Object invoiceCancel(InvoiceGetParams params, BaseNode node) throws Exception {
        InvoiceSubjectInfoReqDto reqDto = new InvoiceSubjectInfoReqDto();
        reqDto.setStrTenancyId(getInvoiceMid(node));
        reqDto.setDinType(1);
        reqDto.setSceneNotify("3");
        reqDto.setTotalInvoiceAmount(params.getInvoiceAmount());
        //2、做适配样例发票请求的signKey
        Map<String, String> storeCodeMappings = getStoreCodeMappings();

        InvoiceDetailInfoReqDto reqDtoNew = convert(params, storeCodeMappings);
        reqDtoNew.setStrOrganId(storeCodeMappings.get(params.getStoreCode()));
        List<InvoiceDetailInfoReqDto> ls = List.of(reqDtoNew);
        reqDto.setInfo(ls);

        String signKey = invoiceMD5Service.executeSign(reqDto, node);
        reqDto.setSignKey(signKey);
        return invoiceMD5Service.executeResult(reqDto, node, params.getCancelType());
    }

    @Override
    public InvoiceOrderInfoResultDto invoiceList(InvoiceOrderInfoParams params, BaseNode node) throws Exception {
        InvoiceOrderInfoReqDto reqDto = new InvoiceOrderInfoReqDto();
        reqDto.setMemberPhone(params.getMemberPhone()).setMemberNo(params.getMemberNo()).setPage(params.getCurrent()).setSize(params.getPageSize());
        reqDto.setMid(getInvoiceMid(node));
        String url = config.getInvoiceBaseUrl() + node.url();
        log.info("[invoiceList] 当前请求的的url地址为：[{}]", url);
        Map<String, Object> obj = gson.fromJson(gson.toJson(reqDto), TypeHelperUtil.mapOf(String.class, Object.class));
        Map<String, Object> respMap = HttpUtil.postForEntityNoSign(url, obj);
        if (ObjectUtils.isEmpty(respMap)) {
            throw new BizException("LB5", "获取列表异常");
        }
        Map<String, Object> data = (Map<String, Object>) respMap.getOrDefault("data", null);
        if (ObjectUtils.isEmpty(data)) {
            throw new BizException("LB9", "获取列表失败");
        }
        Object dataList = data.getOrDefault("list", null);
        InvoiceOrderInfoResultDto dto = new InvoiceOrderInfoResultDto();
        if (ObjectUtils.isEmpty(dataList)) {
            return dto;
        }
        List<InvoiceOrderInfoRespDto> respDtoList = gson.fromJson(gson.toJson(dataList), TypeHelperUtil.listOf(InvoiceOrderInfoRespDto.class));
        Map<String, String> storeCodeMappings = getMappingStoreCode();
        int totalSize = (int) data.get("total");
        dto.setList(handleResponseInvoiceInfo(respDtoList, storeCodeMappings));
        dto.setTotalSize(totalSize);
        return dto;
    }

    private List<InvoiceOrderInfoResult> handleResponseInvoiceInfo(List<InvoiceOrderInfoRespDto> respDtoList, Map<String, String> storeCodeMappings) {
        //转化发票列表 过滤发票状态 只返回待开票的列表
        return respDtoList.stream().filter(dto -> PENDING_INVOICE.code.equals(dto.getInvoiceStatus()))
                .map(dto -> new InvoiceOrderInfoResult()
                        .setInvoiceStatus(Integer.parseInt(dto.getInvoiceStatus()))
                        .setOrderNo(dto.getBillNum())
                        .setStoreCode(storeCodeMappings.getOrDefault(dto.getShopId(), null))
                        .setCreatedTime(DateTimeUtil.formatToStandard(dto.getOrderTime()))
                        .setOwnSellerNo(dto.getSellerNum())
                        .setPayableAmount(dto.getOrderAmount())
                ).toList();
    }

    @Override
    public InvoiceFinishedResultDto invoiceFinishedList(InvoiceFinishedParams params, BaseNode node) throws Exception {
        InvoiceFinishedReqDto reqDto = new InvoiceFinishedReqDto();
        reqDto.setMid(getInvoiceMid(node))
                .setMemberNo(params.getMemberNo())
                .setMemberPhone(params.getMemberPhone())
                .setPage(params.getPage())
                .setSize(params.getSize());
        String url = config.getFinishInvoiceBaseUrl() + node.url();
        Map<String, Object> obj = gson.fromJson(gson.toJson(reqDto), TypeHelperUtil.mapOf(String.class, Object.class));
        Map<String, Object> respMap = HttpUtil.postForEntityNoSign(url, obj);
        if (ObjectUtils.isEmpty(respMap)) {
            throw new BizException("LB5", "获取列表异常");
        }
        Object data = respMap.getOrDefault("data", null);
        if (ObjectUtils.isEmpty(data)) {
            throw new BizException("LB9", "获取列表失败");
        }
        InvoiceFinishedRespDto respDto = gson.fromJson(gson.toJson(data), TypeHelperUtil.getType(InvoiceFinishedRespDto.class));
        //转化参数
        Map<String, String> storeCodeMappings = getMappingStoreCode();
        List<InvoiceFinishedResult> resultList = getInvoiceFinishedResults(respDto, storeCodeMappings);
        return new InvoiceFinishedResultDto().setList(resultList).setTotalSize(respDto.getTotal());
    }

    @NotNull
    private List<InvoiceFinishedResult> getInvoiceFinishedResults(InvoiceFinishedRespDto respDto, Map<String, String> storeCodeMappings) {
        return respDto.getList().stream().map(dto ->
                        new InvoiceFinishedResult()
                                .setStoreCode(storeCodeMappings
                                        .getOrDefault(dto.getSid(), null))
                                .setType(dto.getBuyerType())
                                .setStoreName(dto.getShopName())
                                .setInvoiceNum(dto.getInvoiceNum())
                                .setOrdeNoList(dto.getOrderNumList())
                                .setInvoiceAmount(dto.getInvoiceAmount())
                                .setPayableAmount(dto.getTotalAmount())
                                .setOrderType(dto.getOrderType())
                                .setTitle(dto.getInvoiceTitle())
                                .setOwnSellerNo(dto.getSellerNum())
                                .setOwnSellerName(dto.getSellerName())
                                .setCompanyAddress(dto.getCompanyAddress())
                                .setTelephone(dto.getCompanyPhone())
                                .setBankName(dto.getBankName())
                                .setBankAccount(dto.getBankNum())
                                .setUserEmail(dto.getEmail())
                                .setPhone(dto.getPhone())
                                .setBuyerTax(dto.getBuyerTax())
                                .setInvoiceTime(dto.getInvoiceTime())
                                .setInvoiceStatus(dto.getInvoiceStatus())
                                .setInvoiceNo(dto.getInvoiceNo())
                                .setInvoiceCode(dto.getInvoiceCode()))
                .toList();
    }

    private Map<String, String> getMappingStoreCode() {
        String key = RedisStoreKey.MAPPING_STORE_CODE.generateKey();
        return commonClient.readAllMap(key);
    }

    @Override
    public List<InvoiceTitleDetailResultDto> invoiceTitleSearch(InvoiceTitleSearchParams params, BaseNode node) throws Exception {
        InvoiceTitleSearchReqDto reqDto = new InvoiceTitleSearchReqDto();
        reqDto.setName(params.getName());
        reqDto.setTenancyId(getInvoiceMid(node));
        Map<String, String> storeCodeMappings = getStoreCodeMappings();
        reqDto.setStoreId(storeCodeMappings.get(params.getStoreCode()));
        reqDto.setSceneNotify(3);
        String url = config.getKpinvoiceBaseUrl() + node.url();
        Map<String, Object> obj = gson.fromJson(gson.toJson(reqDto), TypeHelperUtil.mapOf(String.class, Object.class));

        Map<String, Object> respMap = HttpUtil.postForEntityNoSign(url, obj);
        if (ObjectUtils.isEmpty(respMap)) {
            throw new BizException("TS5", "获取公司列表异常");
        }
        Object data = respMap.getOrDefault("data", null);
        String code = respMap.get("code").toString();
        if (!successCode.equals(code) && ObjectUtils.isEmpty(data)) {
            throw new BizException("TS9", "获取公司列表失败");
        }
        if (successCode.equals(code) && ObjectUtils.isEmpty(data)) {
            return Collections.emptyList();
        }
        List<InvoiceCompanyInfoRespDto> respDtoList = gson.fromJson(gson.toJson(data), TypeHelperUtil.listOf(InvoiceCompanyInfoRespDto.class));
        return getInvoiceTitleDetailResult(respDtoList);
    }

    @NotNull
    private List<InvoiceTitleDetailResultDto> getInvoiceTitleDetailResult(List<InvoiceCompanyInfoRespDto> respDtoList) {
        return respDtoList.stream().map(dto -> new InvoiceTitleDetailResultDto().setTitle(dto.getName()).setTelephone(dto.getMobilePhone()).setTaxNumber(dto.getTaxId()).setCompanyAddress(dto.getCompanyIndex()).setBankAccount(dto.getBankAccount()).setBankName(dto.getBank())).toList();
    }

    private Map<String, String> getStoreCodeMappings() {
        String key = RedisStoreKey.STORE_CODE_MAPPING.generateKey();
        return commonClient.readAllMap(key);
    }


    @Override
    public List<InvoiceReviewAmountResultDto> invoiceReviewAmount(InvoiceReviewAmountParams params, BaseNode node) throws Exception {
        //缓存获取sid
        List<InvoiceReviewAmountReqDto> reqDtoList = params.getInvoiceOrderList().stream().map(param -> {
            Map<String, String> storeCodeMappings = getStoreCodeMappings();
            InvoiceReviewAmountReqDto reqDto = new InvoiceReviewAmountReqDto();
            reqDto.setSid(storeCodeMappings.get(param.getStoreCode()));
            reqDto.setMid(getInvoiceMid(node));
            reqDto.setOrderNumList(param.getOrderNoList());
            return reqDto;
        }).toList();
        String url = config.getInvoiceBaseUrl() + node.url();
        String json = HttpUtil.postForEntity(url, gson.toJson(reqDtoList));
//        if (ObjectUtils.isEmpty(respMap)) {
//            throw new BizException("CA500", "计算发票金额异常");
//        }
//        Object data = respMap.getOrDefault("data", null);
//        if (ObjectUtils.isEmpty(data)) {
//            throw new BizException("CA999", "计算发票金额失败");
//        }
//        List<InvoiceReviewAmountRespDto> list = gson.fromJson(gson.toJson(data), TypeHelperUtil.listOf(InvoiceReviewAmountRespDto.class));

//        String json = SBDemoNeedGetBodyHttpClient.doGetJson(url, gson.toJson(reqDtoList));
        List<InvoiceReviewAmountRespDto> list = gson.fromJson(json, TypeHelperUtil.listOf(InvoiceReviewAmountRespDto.class));

        /*
         * 1. 请求参数不能为空数组
         * 2. 支持批量查询多个商户下的订单开票金额
         * 3. 每个商户的查询会独立进行，使用对应商户的数据源
         * 4. respFlag 字段说明订单状态：
         *   值为 1：订单不存在
         *   值为 2：订单额度已开完
         *  值为 null：正常可开票状态
         * 5. 所有金额字段返回 Double 类型，精确到小数点后两位
         */
        return list.stream().map(l ->
                        new InvoiceReviewAmountResultDto()
                                .setInvoicePrice(l.getInvoicePrice())
                                .setRemainInvoicePrice(l.getRemainInvoicePrice())
                                .setOrderNo(l.getOrderNum()))
                .toList();
    }

    private String getInvoiceMid(BaseNode node) {
        String serviceName = node.name().split(Separator.UNDERSCORE)[0].concat(DB_SUFFIX).toLowerCase();
        AccessKeyConfig accessKeyConfig = appSecretService.getSecretByServiceName(serviceName);
        if (accessKeyConfig == null) {
            throw new BizException("KP9", "发票配置获取失败");
        }
        String extra = accessKeyConfig.getExtra();
        Map<String, Object> extraConfig = gson.fromJson(extra, TypeHelperUtil.mapOf(String.class, Object.class));
        return extraConfig.get("mid").toString();
    }


    @Override
    public Boolean invoicePushEmail(InvoicePushEmailParams params, BaseNode node) throws Exception {
        Map<String, String> storeCodeMappings = getStoreCodeMappings();

        InvoicePushEmailReqDto reqDto = new InvoicePushEmailReqDto()
                .setTenancyId(getInvoiceMid(node))
                .setStoreId(storeCodeMappings.get(params.getStoreCode()))
                .setSceneNotify("3")
                .setTaxNo(params.getSellerNum())
                .setInvoiceCode(params.getInvoiceCode())
                .setInvoiceNo(params.getInvoiceNo())
                .setDigitInvoiceNo(params.getInvoiceNo())
                .setPushEmail(params.getPushEmail());

        String url = config.getKpinvoiceBaseUrl() + node.url();
        Map<String, Object> obj = gson.fromJson(gson.toJson(reqDto), TypeHelperUtil.mapOf(String.class, Object.class));

        Map<String, Object> respMap = HttpUtil.postForEntityNoSign(url, obj);
        if (ObjectUtils.isEmpty(respMap)) {
            throw new BizException("PE5", "邮箱推送异常");
        }
        String success = (String) respMap.getOrDefault("code", "");

        if (!successCode.equals(success)) {
            String errorMsg = respMap.getOrDefault("msg", "推送邮箱失败").toString();
            throw new BizException("PE9", errorMsg);
        }
        return Boolean.TRUE;
    }


}
