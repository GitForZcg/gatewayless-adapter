package com.personal.demo.adapter.internal.trade.convert;

import com.personal.demo.pojo.dto.trade.reponse.OrderCommitRespDto;
import com.personal.demo.pojo.dto.trade.request.OrderCommitCancelReqDto;
import com.personal.demo.pojo.dto.trade.request.OrderCommitReqDto;
import com.personal.demo.pojo.dto.trade.request.OrderPreviewReqDto;
import com.personal.demo.request.trade.OrderCommitCancelParam;
import com.personal.demo.request.trade.OrderCommitParam;
import com.personal.demo.request.trade.OrderPreviewParam;
import com.personal.demo.response.trade.OrderCommitResultDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 类转换
 *
 * @Author:
 * @Date:
 */
@Mapper(componentModel = "spring")
public interface TradeConvert {

    TradeConvert INSTANCE = Mappers.getMapper(TradeConvert.class);


    /**
     * 订单预览数据参数转换
     *
     * @param param
     * @return
     */
    OrderPreviewReqDto previewDataConvert(OrderPreviewParam param);

    OrderCommitReqDto commitDataConvert(OrderCommitParam param);

    OrderCommitResultDto commitDataConvert(OrderCommitRespDto respDto);

}
