package com.personal.demo.adapter.internal.order.convert.impl;

import com.personal.demo.adapter.internal.order.convert.OrderConvert;
import com.personal.demo.conf.RedisFactory;
import com.personal.demo.dto.order.*;
import com.personal.demo.enu.RedisOrderKey;
import com.common.base.exception.BizException;
import com.common.dto.response.Result;
import com.common.redis.sdk.RedissionClient;
import com.common.tools.GsonUtils;
import com.personal.demo.order.PersonalOrderSyncApi;
import com.personal.demo.order.PersonalOrderSyncLogApi;
import com.personal.demo.order.dto.PersonalOrderSyncDto;
import com.personal.demo.order.dto.PersonalOrderSyncLogDto;
import com.personal.demo.order.dto.ReduceInventoryDto;
import com.personal.demo.order.enu.PlatformTypeEnum;
import com.personal.demo.order.enu.SyncStatusEnum;
import com.personal.demo.order.enu.SyncTypeEnum;
import com.personal.demo.order.request.QuerySyncDemoOrderRequest;
import com.personal.demo.order.request.SyncDemoOrderRequest;
import com.personal.product.IPersonalProductInternalApi;
import com.personal.product.internal.SkuCodeDto;
import com.personal.product.internal.SkuCodeParam;
import com.personal.store.dto.NdShopDTO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author liqianqian
 * @date
 */
@Slf4j
@Component
public class OrderConvertImpl implements OrderConvert {

    @Resource
    private PersonalOrderSyncApi personalOrderSyncApi;

    private final RedissionClient redissionClient = RedisFactory.getClient(RedisFactory.RedisClient.DEFAULT_1);

    @Resource
    private PersonalOrderSyncLogApi personalOrderSyncLogApi;

    @Resource
    private IPersonalProductInternalApi iPersonalProductInternalApi;


    /**
     * 换取sku
     *
     * @param orderNo
     * @return
     */
    @Override
    public boolean queryOrderSyncResult(String orderNo) {
        Result<Boolean> result = null;
        QuerySyncDemoOrderRequest request = new QuerySyncDemoOrderRequest();
        try {
            request.setOrderNo(orderNo);
            log.info("OrderConvert.queryOrderSyncResult 查询订单同步情况 入参:{}", GsonUtils.beanToJson(request));
            result = personalOrderSyncApi.queryOrderSync(request);
            log.info("OrderConvert.queryOrderSyncResult 查询订单同步情况 结果:{}", GsonUtils.beanToJson(result));
            return result.getData();
        } catch (Exception e) {
            log.error("OrderConvert.queryOrderSyncResult 查询订单同步情况 入参 :{} 订单编号:{}  异常code:{}", GsonUtils.beanToJson(request), orderNo, e.getMessage());

            throw new BizException("远程调用order异常", e.getMessage());
        }
    }

    @Override
    public void saveSynchronizingLog(OrderSyncData orderSyncData, DemoOrderDTO demoOrderDTO) {
        PersonalOrderSyncLogDto personalOrderSyncLogDto = new PersonalOrderSyncLogDto();
        personalOrderSyncLogDto.setOrderNo(demoOrderDTO.getBills().get(0).getBillno());
        personalOrderSyncLogDto.setPlatformOrderId(demoOrderDTO.getBills().get(0).getThird_bill_num());
        personalOrderSyncLogDto.setSyncType(SyncTypeEnum.DEMO_TO_PERSONAL.getCode());
        personalOrderSyncLogDto.setPlatformType(PlatformTypeEnum.DEMO.getCode());
        personalOrderSyncLogDto.setStatus(SyncStatusEnum.WAITING_SYNC.getCode());
        log.info("OrderConvert.saveSynchronizingLog 同步中--log日志 入参:{}", GsonUtils.beanToJson(personalOrderSyncLogDto));
        syncLog(personalOrderSyncLogDto);

    }


    private void syncLog(PersonalOrderSyncLogDto personalOrderSyncLogDto) {
        try {

            personalOrderSyncLogApi.saveSyncOrderLog(personalOrderSyncLogDto);
        } catch (Exception e) {

            log.error("OrderConvert.saveSyncOrderLog  同步日志 入参 :{}  异常code:{}", GsonUtils.beanToJson(personalOrderSyncLogDto), e.getMessage());
            throw new BizException("远程调用order异常", e.getMessage());
        }
    }


    /**
     * 处理适配样例订单数据
     *
     * @param orderSyncData
     */
    @Override
    public void handleDemoData(OrderSyncData orderSyncData, DemoOrderDTO demoOrderDTO) {
        if (!StringUtils.isEmpty(demoOrderDTO.getBills().get(0).getBill_state())) {
            return;
        }
        handleDemoBillDetailData(orderSyncData, demoOrderDTO);
        handleDemoBillDetailTpsData(orderSyncData, demoOrderDTO);
    }

    /**
     * 处理适配样例订单详情数据(规格)
     *
     * @param orderSyncData
     */
    private void handleDemoBillDetailTpsData(OrderSyncData orderSyncData, DemoOrderDTO demoOrderDTO) {
        if (CollectionUtils.isEmpty(demoOrderDTO.getBilldetailedtps())) {
            Map<String, List<String>> repeatBillDetailTpsMap = new HashMap<>();
            orderSyncData.setRepeatBillDetailTpsMap(repeatBillDetailTpsMap);
            return;
        }
        Map<String, List<String>> repeatBillDetailTpsMap =
                demoOrderDTO.getBilldetailedtps().stream()
                        .collect(
                                Collectors.groupingBy(
                                        DemoBillDetailedTpsDTO::getBilldetailedid,
                                        Collectors.mapping(
                                                DemoBillDetailedTpsDTO::getTpno,
                                                Collectors.toList()
                                        )
                                )

                        );

        orderSyncData.setRepeatBillDetailTpsMap(repeatBillDetailTpsMap);

        //----因为产品二期可能改版,允许产品无规格,所以去除这个逻辑
        /*if (CollectionUtils.isEmpty(demoOrderDTO.getBilldetailedtps())) {

            return;
        }*/
    }

    /**
     * 处理适配样例订单详情数据
     *
     * @param orderSyncData
     */
    private void handleDemoBillDetailData(OrderSyncData orderSyncData, DemoOrderDTO demoOrderDTO) {

        if (CollectionUtils.isEmpty(demoOrderDTO.getBilldetaileds())) {
            log.error("OrderConvert.pullOrder demo-- 异常订单数据--无产品数据  【订单uuid】:{}", demoOrderDTO.getBills().get(0).getBillid());
            handleExceptionSku(orderSyncData, demoOrderDTO);
            saveErrorSyncLog(orderSyncData, demoOrderDTO);
            return;
        }
        List<DemoBillDetailedDTO> filterBillDetailList = demoOrderDTO.getBilldetaileds().stream()
                .filter(detail -> detail.getPackageflag() != 1).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(filterBillDetailList)) {
            log.error("OrderConvert.pullOrder demo-- 异常订单数据 --无产品规格数据 【订单uuid】:{}", demoOrderDTO.getBills().get(0).getBillid());

            List<SkuDto> errorSkuList = demoOrderDTO.getBilldetaileds().stream().map(demoBillDetailedDTO -> {
                SkuDto skuDto = new SkuDto();
                skuDto.setProductCode(demoBillDetailedDTO.getDishesno());
                skuDto.setProductName(demoBillDetailedDTO.getDishesname());
                return skuDto;
            }).collect(Collectors.toList());

            handleErrorSku(orderSyncData, demoOrderDTO, errorSkuList);
            saveErrorSyncLog(orderSyncData, demoOrderDTO);
            return;
        }
        orderSyncData.setFilterBillDetailList(filterBillDetailList);
    }


    @Override
    public void querySku(OrderSyncData orderSyncData, DemoOrderDTO demoOrderDTO) {
        log.info("OrderConvert  querySku 方法入口 【订单uuid】:{} orderSyncData :{}", demoOrderDTO.getBills().get(0).getBillid(), GsonUtils.beanToJson(orderSyncData));
        if (!StringUtils.isEmpty(demoOrderDTO.getBills().get(0).getBill_state())) {

            return;
        }
        List<SkuCodeDto> skuCodeListResult = null;
        List<SkuCodeParam> param = null;
        try {
            if (CollectionUtils.isEmpty(orderSyncData.getFilterBillDetailList())) {
                return;
            }
            param = orderSyncData.getFilterBillDetailList().stream().map(detail -> {
                SkuCodeParam skuCodeParam = new SkuCodeParam();
                skuCodeParam.setProductCode(detail.getDishesno());
                if (!CollectionUtils.isEmpty(orderSyncData.getRepeatBillDetailTpsMap())) {

                    skuCodeParam.setSubSpecCodeList(orderSyncData.getRepeatBillDetailTpsMap().get(detail.getBilldetailedid()));
                }
                return skuCodeParam;
            }).collect(Collectors.toList());
            log.info("OrderConvert.getSkuCode 换取sku 入参:{},【订单uuid】:{}", GsonUtils.beanToJson(param), demoOrderDTO.getBills().get(0).getBillid());
            skuCodeListResult = iPersonalProductInternalApi.getSkuCode(param);
            log.info("OrderConvert.getSkuCode 换取sku 结果:{},[订单uuid]:{}", GsonUtils.beanToJson(skuCodeListResult), demoOrderDTO.getBills().get(0).getBillid());

            orderSyncData.setSkuCodeResultList(skuCodeListResult);
        } catch (Exception e) {
            log.error("OrderConvert.getSkuCode 换取sku 入参 :{} 【订单uuid】:{}  异常code:{}", GsonUtils.beanToJson(param), demoOrderDTO.getBills().get(0).getBillid(), e.getMessage());
            throw new BizException("远程调用product异常", e.getMessage());
        }
    }

    @Override
    public void filterSku(OrderSyncData orderSyncData, DemoOrderDTO demoOrderDTO) {
        log.info("OrderConvert  filterSku 方法入口 【订单uuid】:{} orderSyncData :{}", demoOrderDTO.getBills().get(0).getBillid(), GsonUtils.beanToJson(orderSyncData));
        if (!StringUtils.isEmpty(demoOrderDTO.getBills().get(0).getBill_state())) {

            return;
        }
        Map<String, List<String>> repeatBillDetailTpsMap = orderSyncData.getRepeatBillDetailTpsMap();
        List<DemoBillDetailedDTO> filterBillDetailList = orderSyncData.getFilterBillDetailList();


        List<SkuDto> errorSkuList = new ArrayList<>();
        Map<String, SkuDto> reduceInventorySkuMap = new HashMap<>();

        List<SkuCodeDto> skuCodeListResult = orderSyncData.getSkuCodeResultList();

        if (CollectionUtils.isEmpty(skuCodeListResult)) {

            if (!CollectionUtils.isEmpty(filterBillDetailList)) {
                for (DemoBillDetailedDTO demoBillDetailedDTO : filterBillDetailList) {

                    // 匹配异常场景: 规格没匹配/ace-没有规格
                    addToErrorList(demoBillDetailedDTO, new ArrayList<>(), errorSkuList);

                }
            }


        } else {

            List<SkuCodeDto> mapSuccessSkuList = skuCodeListResult.stream().filter(sku -> sku.getSkuCode() != null && !CollectionUtils.isEmpty(sku.getSubSpecCodeList())).collect(Collectors.toList());

            Map<String, List<SkuCodeDto>> skuMap = mapSuccessSkuList.stream().collect(Collectors.groupingBy(
                    SkuCodeDto::getProductCode));

            for (DemoBillDetailedDTO demoBillDetailedDTO : filterBillDetailList) {


                List<String> skuSpecCodeList = repeatBillDetailTpsMap.get(demoBillDetailedDTO.getBilldetailedid());

                List<SkuCodeDto> skuCodeList = skuMap.get(demoBillDetailedDTO.getDishesno());


                if (skuCodeList != null && !CollectionUtils.isEmpty(skuSpecCodeList)) {

                    processMatchedSkuCodes(skuCodeList, skuSpecCodeList, demoBillDetailedDTO,
                            reduceInventorySkuMap, errorSkuList);

                } else {


                    // 匹配异常场景: 规格没匹配/ace-没有规格
                    addToErrorList(demoBillDetailedDTO, skuSpecCodeList, errorSkuList);
                }


            }
        }

        orderSyncData.setReduceInventorySkuMap(reduceInventorySkuMap);
        orderSyncData.setErrorSkuList(errorSkuList);

        log.info("OrderConvert.filterSku【订单uuid】:{} 换取sku reduceInventorySkuMap:{} ,errorSkuList:{} ", demoOrderDTO.getBills().get(0).getBillid(), GsonUtils.beanToJson(reduceInventorySkuMap), GsonUtils.beanToJson(errorSkuList));

        handleErrorSku(orderSyncData, demoOrderDTO, errorSkuList);
    }

    /**
     * 匹配sku逻辑
     *
     * @param skuCodeDtoList         同一类产品的所有skuCode集合
     * @param skuSpecCodeList        待匹配的一个产品的规格集合
     * @param demoBillDetailedDTO
     * @param reduceInventorySkuMap
     * @param errorSkuList
     */
    private void processMatchedSkuCodes(List<SkuCodeDto> skuCodeDtoList, List<String> skuSpecCodeList,
                                        DemoBillDetailedDTO demoBillDetailedDTO, Map<String, SkuDto> reduceInventorySkuMap,
                                        List<SkuDto> errorSkuList) {

        boolean matched = false;
        for (SkuCodeDto skuCodeDto : skuCodeDtoList) {
            if (skuCodeDto.getSubSpecCodeList().size() == skuSpecCodeList.size()
                    && skuCodeDto.getSubSpecCodeList().containsAll(skuSpecCodeList)) {

                SkuDto skuDto = buildInventorySku(skuCodeDto, demoBillDetailedDTO);
                executeFilterSku(reduceInventorySkuMap, skuDto);
                matched = true;
                break;
            }
        }

        if (!matched) {
            addToErrorList(demoBillDetailedDTO, skuSpecCodeList, errorSkuList);
        }
    }

    private SkuDto buildInventorySku(SkuCodeDto skuCodeDto, DemoBillDetailedDTO demoBillDetailedDTO) {
        SkuDto skuDto = new SkuDto();
        skuDto.setProductCode(skuCodeDto.getProductCode());
        skuDto.setSkuCode(skuCodeDto.getSkuCode());
        skuDto.setSkuVersion(skuCodeDto.getSkuCodeVersion());
        skuDto.setSkuNum((int) Math.ceil(demoBillDetailedDTO.getCount()));
        return skuDto;
    }

    /**
     * 收集映射失败sku
     *
     * @param demoBillDetailedDTO
     * @param skuSpecCodeList
     * @param errorSkuList
     */
    private void addToErrorList(DemoBillDetailedDTO demoBillDetailedDTO,
                                List<String> skuSpecCodeList, List<SkuDto> errorSkuList) {

        SkuDto skuDto = new SkuDto();
        skuDto.setProductCode(demoBillDetailedDTO.getDishesno());
        skuDto.setProductName(demoBillDetailedDTO.getDishesname());
        skuDto.setSubSpecCodeList(skuSpecCodeList);
        errorSkuList.add(skuDto);
    }

    private void executeFilterSku(Map<String, SkuDto> reduceInventorySkuMap, SkuDto sku) {

        if (sku == null || StringUtils.isEmpty(sku.getSkuCode())) {
            return;
        }

        reduceInventorySkuMap.compute(sku.getSkuCode(), (key, existingSku) -> {
            if (existingSku == null) {
                SkuDto newSku = new SkuDto();
                newSku.setSkuCode(sku.getSkuCode());
                newSku.setSkuNum(sku.getSkuNum());
                newSku.setSkuVersion(sku.getSkuVersion());
                return newSku;
            } else {
                existingSku.setSkuNum(existingSku.getSkuNum() + sku.getSkuNum());
                return existingSku;
            }
        });


    }

    /**
     * 处理映射失败数据
     *
     * @param demoOrderDTO
     * @param errorSkuList
     */
    private void handleErrorSku(OrderSyncData orderSyncData, DemoOrderDTO demoOrderDTO, List<SkuDto> errorSkuList) {
        // 处理异常数据

        if (!errorSkuList.isEmpty()) {

            ErrorOrderMessageDto errorOrderMessageDto = new ErrorOrderMessageDto();
            errorOrderMessageDto.setOrderUuid(demoOrderDTO.getBills().get(0).getBillid());

            if (demoOrderDTO.getBills().get(0).getChannels().equals(1)) {
                errorOrderMessageDto.setChannel("pos堂食点单");
                errorOrderMessageDto.setThirdBillNum(demoOrderDTO.getBills().get(0).getBillno());

            } else if (demoOrderDTO.getBills().get(0).getChannels().equals(4)) {

                if (demoOrderDTO.getBills().get(0).getTatype().equals(1)) {
                    errorOrderMessageDto.setChannel("美团");
                    errorOrderMessageDto.setThirdBillNum(demoOrderDTO.getBills().get(0).getOutorderid());

                } else if (demoOrderDTO.getBills().get(0).getTatype().equals(3)) {
                    errorOrderMessageDto.setChannel("饿了吗");
                    errorOrderMessageDto.setThirdBillNum(demoOrderDTO.getBills().get(0).getOutorderid());

                }

            }else if (demoOrderDTO.getBills().get(0).getChannels().equals(0)){
                if (demoOrderDTO.getBills().get(0).getTatype().equals(5)) {
                    errorOrderMessageDto.setChannel("京东");
                    errorOrderMessageDto.setThirdBillNum(demoOrderDTO.getBills().get(0).getOutorderid());
                }
            }


            errorOrderMessageDto.setSkuList(errorSkuList);

            log.error("OrderConvert.pullOrder 产品映射失败!!!!!!!!!!!!!!!! " +
                            "门店名称:{}," +
                            "渠道:{}, " +
                            "【订单uuid】:{}," +
                            "三方订单编号:{}," +
                            "异常产品数据:{},",
                    orderSyncData.getSyncOrderBindingStoreDto().getStoreName(),
                    errorOrderMessageDto.getChannel(),
                    errorOrderMessageDto.getOrderUuid(),
                    errorOrderMessageDto.getThirdBillNum(),
                    GsonUtils.beanToJson(errorSkuList));
        }
    }

    /**
     * 处理映射失败数据
     *
     * @param demoOrderDTO
     */
    private void handleExceptionSku(OrderSyncData orderSyncData, DemoOrderDTO demoOrderDTO) {
        // 处理异常数据

        ErrorOrderMessageDto errorOrderMessageDto = new ErrorOrderMessageDto();
        errorOrderMessageDto.setOrderUuid(demoOrderDTO.getBills().get(0).getBillid());

        if (demoOrderDTO.getBills().get(0).getChannels().equals(1)) {
            errorOrderMessageDto.setChannel("pos堂食点单");
            errorOrderMessageDto.setThirdBillNum(demoOrderDTO.getBills().get(0).getBillno());

        } else if (demoOrderDTO.getBills().get(0).getChannels().equals(4)) {

            if (demoOrderDTO.getBills().get(0).getTatype().equals(1)) {
                errorOrderMessageDto.setChannel("美团");
                errorOrderMessageDto.setThirdBillNum(demoOrderDTO.getBills().get(0).getOutorderid());

            } else if (demoOrderDTO.getBills().get(0).getTatype().equals(3)) {
                errorOrderMessageDto.setChannel("饿了吗");
                errorOrderMessageDto.setThirdBillNum(demoOrderDTO.getBills().get(0).getOutorderid());

            }
            if (demoOrderDTO.getBills().get(0).getTatype().equals(5)) {
                errorOrderMessageDto.setChannel("京东");
                errorOrderMessageDto.setThirdBillNum(demoOrderDTO.getBills().get(0).getOutorderid());
            }
        }

        log.error("OrderConvert.pullOrder 产品映射失败!!!!!!!!!!!!!!!! " +
                        "门店名称:{}," +
                        "渠道:{}, " +
                        "【订单uuid】:{}," +
                        "三方订单编号:{},",
                orderSyncData.getSyncOrderBindingStoreDto().getStoreName(),
                errorOrderMessageDto.getChannel(),
                errorOrderMessageDto.getOrderUuid(),
                errorOrderMessageDto.getThirdBillNum());

    }

    @Override
    public void queryStore(OrderSyncData orderSyncData, DemoOrderDTO demoOrderDTO) {

/*        if (CollectionUtils.isEmpty(orderSyncData.getReduceInventorySkuMap())) {
            return "";
        }
        if (!StringUtils.isEmpty(demoOrderDTO.getBills().get(0).getBill_state())) {

            return "";
        }*/
        String storeString = redissionClient.getObjValue(RedisOrderKey.STORE_BINDING_STORE_KEY.getPrefix());
        if (StringUtils.isEmpty(storeString)) {
            log.error("OrderConvert.queryStore  门店信息异常,redis数据不存在");
            throw new BizException("暂无门店信息");
        }
        List<NdShopDTO> shopDTOList = GsonUtils.jsonToList(storeString, NdShopDTO.class);


        if (CollectionUtils.isEmpty(shopDTOList)) {
            log.error("OrderConvert.queryStore  门店信息不存在，门店id:{},门店数据:{}", demoOrderDTO.getBills().get(0).getOgnid(), GsonUtils.beanToJson(shopDTOList));
            throw new BizException("门店信息异常");
        }

        Map<String, NdShopDTO> demoPersonalMap = shopDTOList.stream().collect(Collectors.toMap(NdShopDTO::getId, Function.identity(), (key1, key2) -> key2));
        if (ObjectUtils.isEmpty(demoPersonalMap.get(demoOrderDTO.getBills().get(0).getOgnid()))
                || StringUtils.isEmpty(demoPersonalMap.get(demoOrderDTO.getBills().get(0).getOgnid()).getStoreCode())
                || StringUtils.isEmpty(demoPersonalMap.get(demoOrderDTO.getBills().get(0).getOgnid()).getShopFullName())) {
            log.error("OrderConvert.queryStore 门店信息不存在，门店id:{} 门店数据:{}", demoOrderDTO.getBills().get(0).getOgnid(), GsonUtils.beanToJson(demoPersonalMap));
            throw new BizException("门店信息异常");


        }

        SyncOrderBindingStoreDto syncOrderBindingStoreDto = new SyncOrderBindingStoreDto();
        syncOrderBindingStoreDto.setStoreCode(demoPersonalMap.get(demoOrderDTO.getBills().get(0).getOgnid()).getStoreCode());
        syncOrderBindingStoreDto.setStoreName(demoPersonalMap.get(demoOrderDTO.getBills().get(0).getOgnid()).getShopFullName());
        orderSyncData.setSyncOrderBindingStoreDto(syncOrderBindingStoreDto);

    }

    @Override
    public void syncDemoOrder(OrderSyncData orderSyncData, DemoOrderDTO demoOrderDTO) {
        log.info("OrderConvert  syncDemoOrder 方法入口 [getBillid]:{} orderSyncData :{}", demoOrderDTO.getBills().get(0).getBillid(), GsonUtils.beanToJson(orderSyncData));

        SyncDemoOrderRequest syncDemoOrderRequest = new SyncDemoOrderRequest();
        try {

            handleInventorySku(orderSyncData, demoOrderDTO, syncDemoOrderRequest);

            convertSuccessSyncOrder(syncDemoOrderRequest, demoOrderDTO);

            convertSuccessSyncOrderLog(syncDemoOrderRequest, demoOrderDTO);

            log.info("OrderConvert.syncDemoOrder 同步demo订单 入参:{}", GsonUtils.beanToJson(syncDemoOrderRequest));
            Result<Void> result = personalOrderSyncApi.demoOrderSyncPersonal(syncDemoOrderRequest);
            log.info("OrderConvert.syncDemoOrder 同步demo订单 返回结果:{}", GsonUtils.beanToJson(result));
        } catch (Exception e) {
            log.error("OrderConvert.syncDemoOrder 同步demo订单 入参 :{}  异常code:{}", GsonUtils.beanToJson(syncDemoOrderRequest), e.getMessage());
            //saveErrorSyncLog(orderSyncData, demoOrderDTO);
            throw new BizException("远程调用order异常", e.getMessage());
        }
    }


    private void handleInventorySku(OrderSyncData orderSyncData, DemoOrderDTO demoOrderDTO, SyncDemoOrderRequest syncDemoOrderRequest) {

        Map<String, SkuDto> reduceInventorySkuMap = orderSyncData.getReduceInventorySkuMap();

        if (!StringUtils.isEmpty(demoOrderDTO.getBills().get(0).getBill_state())) {

            return;
        }

        if (CollectionUtils.isEmpty(reduceInventorySkuMap)) {
            return;
        }

        ReduceInventoryDto reduceInventoryDto = new ReduceInventoryDto();
        reduceInventoryDto.setStoreCode(orderSyncData.getSyncOrderBindingStoreDto().getStoreCode());

        // 处理订单数据
        reduceInventoryDto.setOrderNo(demoOrderDTO.getBills().get(0).getBillid());
        reduceInventoryDto.setMemberCode("demo-用户");


        // 处理扣减库存数据
        List<com.personal.order.dto.msg.SkuDto> reduceInventorySkuList = reduceInventorySkuMap.entrySet().stream()
                .map(entry -> {
                    com.personal.order.dto.msg.SkuDto dto = new com.personal.order.dto.msg.SkuDto();
                    dto.setSkuCode(entry.getKey());
                    dto.setSkuNum(entry.getValue().getSkuNum());
                    dto.setSkuVersion(entry.getValue().getSkuVersion());
                    return dto;
                })
                .collect(Collectors.toList());

        reduceInventoryDto.setSkuList(reduceInventorySkuList);
        //orderSyncData.setSyncDemoOrderRequest(syncDemoOrderRequest);
        syncDemoOrderRequest.setReduceInventoryDto(reduceInventoryDto);
    }


    private void convertSuccessSyncOrderLog(SyncDemoOrderRequest syncDemoOrderRequest, DemoOrderDTO demoOrderDTO) {
        PersonalOrderSyncLogDto personalOrderSyncLogDto = new PersonalOrderSyncLogDto();
        personalOrderSyncLogDto.setOrderNo(demoOrderDTO.getBills().get(0).getBillid());
        personalOrderSyncLogDto.setPlatformOrderId(demoOrderDTO.getBills().get(0).getThird_bill_num());
        personalOrderSyncLogDto.setSyncType(SyncTypeEnum.DEMO_TO_PERSONAL.getCode());
        personalOrderSyncLogDto.setPlatformType(PlatformTypeEnum.DEMO.getCode());
        personalOrderSyncLogDto.setStatus(SyncStatusEnum.SYNC_SUCCESS.getCode());
        syncDemoOrderRequest.setPersonalOrderSyncLogDto(personalOrderSyncLogDto);

    }

    private void convertSuccessSyncOrder(SyncDemoOrderRequest syncDemoOrderRequest, DemoOrderDTO demoOrderDTO) {
        PersonalOrderSyncDto personalOrderSyncDto = new PersonalOrderSyncDto();
        personalOrderSyncDto.setOrderNo(demoOrderDTO.getBills().get(0).getBillid());
        personalOrderSyncDto.setSyncType(SyncTypeEnum.DEMO_TO_PERSONAL.getCode());
        personalOrderSyncDto.setSyncOrderInfo(GsonUtils.beanToJson(demoOrderDTO));
        syncDemoOrderRequest.setPersonalOrderSyncDto(personalOrderSyncDto);

    }


    /**
     * 保存异常log
     *
     * @param orderSyncData
     */
    @Override
    public void saveErrorSyncLog(OrderSyncData orderSyncData, DemoOrderDTO demoOrderDTO) {

        PersonalOrderSyncLogDto personalOrderSyncLogDto = new PersonalOrderSyncLogDto();
        personalOrderSyncLogDto.setOrderNo(demoOrderDTO.getBills().get(0).getBillid());
        personalOrderSyncLogDto.setPlatformOrderId(demoOrderDTO.getBills().get(0).getThird_bill_num());
        personalOrderSyncLogDto.setSyncType(SyncTypeEnum.DEMO_TO_PERSONAL.getCode());
        personalOrderSyncLogDto.setPlatformType(PlatformTypeEnum.DEMO.getCode());
        personalOrderSyncLogDto.setStatus(SyncStatusEnum.SYNC_FAIL.getCode());
        log.info("OrderConvert.saveErrorSyncLog 异常log日志 入参:{}", GsonUtils.beanToJson(personalOrderSyncLogDto));
        syncLog(personalOrderSyncLogDto);
    }


}
