package com.personal.demo.serivce.trade.impl;

import com.personal.demo.adapter.internal.trade.convert.TradeConvert;
import com.personal.demo.conf.RedisFactory;
import com.personal.demo.enu.RedisOrderKey;
import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.pojo.dto.trade.reponse.OrderCommitCancelRespDto;
import com.personal.demo.pojo.dto.trade.reponse.OrderCommitRespDto;
import com.personal.demo.pojo.dto.trade.request.OrderCommitCancelReqDto;
import com.personal.demo.pojo.dto.trade.request.OrderCommitReqDto;
import com.personal.demo.pojo.dto.trade.request.OrderPreviewReqDto;
import com.personal.demo.pojo.dto.trade.reponse.OrderPreviewRespDto;
import com.personal.demo.request.trade.OrderCommitCancelParam;
import com.personal.demo.request.trade.OrderCommitParam;
import com.personal.demo.request.trade.OrderPreviewParam;
import com.personal.demo.response.trade.OrderCommitCancelResultDto;
import com.personal.demo.response.trade.OrderCommitResultDto;
import com.personal.demo.response.trade.OrderPreviewResultDto;
import com.personal.demo.serivce.member.Sm2Invoker;
import com.personal.demo.serivce.trade.ITradeService;
import com.personal.demo.util.TypeHelperUtil;
import com.common.base.exception.BizException;
import com.common.redis.sdk.RedissionClient;
import com.common.tools.GsonUtils;
import com.personal.store.dto.NdShopDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.TypeHandler;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * trade服务实现类
 *
 * @Author:
 * @Date:
 */
@Service
@Slf4j
public class ITradeServiceImpl implements ITradeService {

    private final Sm2Invoker sm2Invoker;

    private final RedissionClient redissionClient = RedisFactory.getClient(RedisFactory.RedisClient.DEFAULT_1);

    public ITradeServiceImpl(Sm2Invoker sm2Invoker) {

        this.sm2Invoker = sm2Invoker;
    }


    @Override
    public OrderPreviewResultDto orderPreview(OrderPreviewParam bizData, BaseNode node) {
        OrderPreviewReqDto reqDto = TradeConvert.INSTANCE.previewDataConvert(bizData);
        NdShopDTO ndShopDTO = queryStore(bizData);
        reqDto.setShop_id(Integer.valueOf(ndShopDTO.getWshId().toString())); //微生活id  shop_id
        log.info("【订单预览】入参：{}", GsonUtils.beanToJson(reqDto));
        OrderPreviewRespDto respDto = sm2Invoker.invoke(reqDto, node, OrderPreviewRespDto.class);
        log.info("【订单预览】返回结果：{}", GsonUtils.beanToJson(respDto));

        OrderPreviewResultDto resultDto = new OrderPreviewResultDto();
        resultDto.setTcid(respDto.getTcid());
        return resultDto;
    }

    private NdShopDTO queryStore(OrderPreviewParam bizData) {
        String storeString = redissionClient.getObjValue(RedisOrderKey.STORE_BINDING_STORE_KEY.getPrefix());
        if (StringUtils.isEmpty(storeString)) {
            log.error("ITradeServiceImpl.queryStore  门店信息异常,redis数据不存在");
            throw new BizException("redis中暂无门店信息");
        }
        List<NdShopDTO> shopDTOList = GsonUtils.jsonToList(storeString, NdShopDTO.class);
        log.info("ITradeServiceImpl.queryStore  门店code:{},门店数据:{}", bizData.getStoreCode(), GsonUtils.beanToJson(shopDTOList));

        if (CollectionUtils.isEmpty(shopDTOList)) {
            throw new BizException("门店信息Json转换异常");
        }


        Map<String, NdShopDTO> demoPersonalMap = shopDTOList.stream().collect(Collectors.toMap(NdShopDTO::getStoreCode, Function.identity(), (key1, key2) -> key2));

        if (CollectionUtils.isEmpty(demoPersonalMap)
                || ObjectUtils.isEmpty(demoPersonalMap.get(bizData.getStoreCode()))) {
            log.error("ITradeServiceImpl.queryStore  门店信息不存在，门店code:{},门店数据:{}", bizData.getStoreCode(), GsonUtils.beanToJson(shopDTOList));
            throw new BizException("当前门店信息不存在");
        }
        return demoPersonalMap.get(bizData.getStoreCode());
    }

    @Override
    public OrderCommitResultDto orderCommit(OrderCommitParam bizData, BaseNode node) {
        OrderCommitReqDto reqDto = TradeConvert.INSTANCE.commitDataConvert(bizData);
        Map<String, Object> map = sm2Invoker.invokeMap(reqDto, node);
        OrderCommitRespDto respDto = GsonUtils.jsonToBean(GsonUtils.beanToJson(map), OrderCommitRespDto.class);
        return TradeConvert.INSTANCE.commitDataConvert(respDto);
    }

    @Override
    public OrderCommitCancelResultDto orderCancel(OrderCommitCancelParam bizData, BaseNode node) {
        OrderCommitCancelReqDto reqDto = new OrderCommitCancelReqDto().setBiz_id(bizData.getTradeCommitId());
        OrderCommitCancelRespDto respDto = sm2Invoker.invoke(reqDto, node, OrderCommitCancelRespDto.class);
        return new OrderCommitCancelResultDto().setResult(respDto.getResult());
    }
}
