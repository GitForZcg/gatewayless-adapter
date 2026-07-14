package com.personal.demo.serivce.impl;

import com.personal.demo.adapter.internal.order.convert.DemoConvert;
import com.personal.demo.adapter.internal.order.convert.OrderConvert;
import com.personal.demo.adapter.internal.order.convert.impl.OrderSyncData;
import com.personal.demo.conf.RedisFactory;
import com.personal.demo.dto.order.DemoOrderDTO;
import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.pojo.dto.DemoOrderReqDto;
import com.personal.demo.pojo.dto.order.DemoOrderInfoDTO;
import com.personal.demo.request.order.PushOrderParams;
import com.personal.demo.request.order.TestOrderParams;
import com.personal.demo.serivce.AbstractOrderMD5Service;
import com.personal.demo.serivce.IOrderService;
import com.common.base.exception.BizException;
import com.common.dto.response.Result;
import com.common.redis.sdk.RedissionClient;
import com.common.tools.GsonUtils;
import com.personal.demo.order.PersonalOrderSyncApi;
import com.personal.demo.order.PersonalOrderSyncLogApi;
import com.personal.demo.order.dto.PersonalOrderSyncDto;
import com.personal.demo.order.dto.PersonalOrderSyncLogDto;
import com.personal.demo.order.request.PersonalOrderSyncLogRequest;
import com.personal.demo.order.request.QuerySyncDemoOrderRequest;
import com.personal.member.MembersApi;
import com.personal.member.MembersCouponApi;
import com.personal.member.dto.member.MembersDetailsDto;
import com.personal.order.IPersonalOrderInfoApi;
import com.personal.order.IPersonalOrderRefundInfoApi;
import com.personal.order.dto.order.AppletOrderDetailDto;
import com.personal.order.dto.order.PersonalOrderInfoDto;
import com.personal.order.dto.refundOrder.PersonalOrderRefundInfoDto;
import com.personal.order.request.OrderRequest;
import com.personal.order.request.QueryRefundOrderRequest;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class OrderServiceImpl implements IOrderService {

    private final AbstractOrderMD5Service service;

    private final RedissionClient redissionClient = RedisFactory.getClient(RedisFactory.RedisClient.DEFAULT_1);


    public OrderServiceImpl(AbstractOrderMD5Service service) {
        this.service = service;
    }

    @Resource
    private IPersonalOrderInfoApi personalOrderInfoApi;

    @Resource
    private MembersCouponApi membersCouponApi;

    @Resource
    private MembersApi membersApi;

    @Resource
    private DemoConvert demoConvert;

    @Resource
    private IPersonalOrderRefundInfoApi personalOrderRefundInfoApi;

    @Resource
    private PersonalOrderSyncLogApi personalOrderSyncLogApi;
    @Resource
    private PersonalOrderSyncApi personalOrderSyncApi;

    @Resource
    private OrderConvert orderConvert;

    @Override
    public Object query(TestOrderParams params, BaseNode node) throws Exception {

        DemoOrderReqDto reqDto = new DemoOrderReqDto();
        reqDto.setAceOrderId(params.getOrderId())
                .setAceOrderAmount(params.getAmount());
        //转化完数据之后 调用签名处理器生成签名
        String orderId = "123554252552";
        Boolean result = service.execute(reqDto, node, orderId, "");
        System.out.println("result = " + result);
        return result;
    }

    /**
     * 推送正向订单到适配样例
     *
     * @param params
     * @param node
     * @return
     * @throws Exception
     */
    @Override
    public Object pushOrder(PushOrderParams params, BaseNode node) throws Exception {
        log.info("【推送订单到适配样例】收到推送订单消息，准备推送小程序订单到适配样例，params:{}", GsonUtils.beanToJson(params));
        //先查询是否是重复数据
        PersonalOrderSyncLogRequest request = new PersonalOrderSyncLogRequest();
        request.setOrderNo(params.getOrderNo());
        request.setOrderType(0);
        Result<PersonalOrderSyncLogDto> logResult = personalOrderSyncLogApi.searchSyncOrderLog(request);
        if (logResult.isSuccess() && Objects.nonNull(logResult.getData())) {
            if (Objects.equals(logResult.getData().getStatus(), 2)) {
                log.info("【推送订单到适配样例】查询到重复数据，不进行推送，params:{}", GsonUtils.beanToJson(params));
                return true;
            }
            log.info("【推送订单到适配样例】查询到的数据非成功数据，进行推送，params:{}", GsonUtils.beanToJson(params));
        }


        PersonalOrderSyncLogDto syncLog = new PersonalOrderSyncLogDto();
        syncLog.setOrderNo(params.getOrderNo());
        syncLog.setStatus(1);
        syncLog.setOrderType(0);
//        syncLog.setCreateTime(Date.from(Instant.now()));
        boolean sync = demoConvert.syncLog(syncLog);
        if (!sync) {
            log.error("【推送订单到适配样例】插入同步日志失败，params:{}", GsonUtils.beanToJson(params));
            throw new Exception("插入同步日志失败");
        }
        try {
            // 1.收到 mq 消息 查询所有能查的消息
            DemoOrderInfoDTO orderDTO = new DemoOrderInfoDTO();
            // 1.1 获取小程序订单详情
            OrderRequest var1 = new OrderRequest();
            var1.setOrderNo(params.getOrderNo());
            var1.setMemberCode(params.getMemberCode());
            Result<AppletOrderDetailDto> orderResult = personalOrderInfoApi.getOrderDetail(var1);
            log.info("【推送订单到适配样例】查询小程序订单详情成功，订单号：{},返回内容:{}", params.getOrderNo(),GsonUtils.beanToJson(orderResult));
            if (!orderResult.isSuccess() || Objects.isNull(orderResult.getData())) {
                log.error("【推送订单到适配样例】查询订单失败，订单号：{}", params.getOrderNo());
                throw new Exception("查询订单失败");
            }
            // 1.2 获取会员信息
            Result<MembersDetailsDto> memberResult = membersApi.membersDetails(params.getMemberCode());
            if (!memberResult.isSuccess() || Objects.isNull(memberResult.getData())) {
                log.error("【推送订单到适配样例】查询会员信息失败，会员编号：{}", params.getMemberCode());
                throw new Exception("查询会员信息失败");
            }
            // 1.3 获取门店信息
            //2.分别处理订单会员门店信息
            AppletOrderDetailDto orderDetail = orderResult.getData();
            //2.1 主表数据转化
            //2.2 订单明细数据转化
            //2.3 订单支付信息数据转化
            PersonalOrderInfoDto orderInfoDto = orderDetail.getPersonalOrderInfoDto();

            QuerySyncDemoOrderRequest request1 = new QuerySyncDemoOrderRequest();
            request1.setOrderNo(params.getOrderNo());
            Result<PersonalOrderSyncDto> syncByOrderNo = personalOrderSyncApi.getSyncByOrderNo(request1);
            if (syncByOrderNo.isSuccess() && Objects.nonNull(syncByOrderNo.getData())) {
                log.info("【推送订单到适配样例】查询到重复数据，直接推送，params:{}", GsonUtils.beanToJson(params));
                orderDTO = GsonUtils.jsonToBean(syncByOrderNo.getData().getSyncOrderInfo(), DemoOrderInfoDTO.class);
            }else {
                orderDTO = demoConvert.billConvert(orderDetail, memberResult.getData());
                demoConvert.sync(params.getOrderNo(), orderDTO);
            }
            String sId = orderDTO.getBills().get(0).getOgnid();
            log.info("【推送订单到适配样例】订单转化完成，orderDTO:{}", GsonUtils.beanToJson(orderDTO));
            //3.1 订单记录表记录
            //3.2 调用适配样例
            log.info("【推送订单到适配样例】调用适配样例，params:{}", GsonUtils.beanToJson(orderDTO));
            Boolean result = service.execute(orderDTO, node, orderInfoDto.getOrderNo(),sId);
//            Boolean result = true;
            String billId = orderDTO.getBills().get(0).getBillid();
            //3.3 根据适配样例处理的结果，新增订单数据表
            if (result != null && result) {
                //更新成功
                PersonalOrderSyncLogDto syncLogSuccess = new PersonalOrderSyncLogDto();
                syncLogSuccess.setOrderNo(params.getOrderNo());
                syncLogSuccess.setPlatformOrderId(billId);
                syncLogSuccess.setStatus(2);
                syncLogSuccess.setOrderType(0);
                demoConvert.syncLog(syncLogSuccess);
                //新增
                return true;
            }
            //更新失败，增加失败的日志
            PersonalOrderSyncLogDto syncLogFail = new PersonalOrderSyncLogDto();
            syncLogFail.setOrderNo(params.getOrderNo());
            syncLogFail.setPlatformOrderId(billId);
            syncLogFail.setStatus(3);
            syncLogFail.setOrderType(0);
            demoConvert.syncLog(syncLogFail);
            return result;
        } catch (Exception e) {
            log.error("【推送订单到适配样例】推送订单失败，params:{}", GsonUtils.beanToJson(params), e);
            //把失败的订单记录一下
            PersonalOrderSyncLogDto syncLogFail = new PersonalOrderSyncLogDto();
            syncLogFail.setOrderNo(params.getOrderNo());
            syncLogFail.setStatus(3);
            syncLogFail.setOrderType(0);
            demoConvert.syncLog(syncLogFail);
            throw e;
        }
    }

    @Override
    public void pullOrder(DemoOrderDTO demoOrderDTO) {
        log.info("OrderServiceImpl.pullOrder 入参:{}, billid:{} billNo:{}", GsonUtils.beanToJson(demoOrderDTO),demoOrderDTO.getBills().get(0).getBillid(),demoOrderDTO.getBills().get(0).getBillno());
        OrderSyncData orderSyncData = new OrderSyncData();
        try {

            orderConvert.saveSynchronizingLog(orderSyncData, demoOrderDTO);

            orderConvert.queryStore(orderSyncData, demoOrderDTO);

            orderConvert.handleDemoData(orderSyncData, demoOrderDTO);

            orderConvert.querySku(orderSyncData, demoOrderDTO);


            orderConvert.filterSku(orderSyncData, demoOrderDTO);

            orderConvert.syncDemoOrder(orderSyncData, demoOrderDTO);
        }catch (Exception e){

            log.error("OrderServiceImpl.pullOrder  异常  orderSyncData:{}, demoOrderDTO:{}",GsonUtils.beanToJson(orderSyncData), GsonUtils.beanToJson(demoOrderDTO),e);
            orderConvert.saveErrorSyncLog(orderSyncData,demoOrderDTO);
        }



    }


    /**
     * 推送冲减单到适配样例
     *
     * @param params
     * @param node
     * @return
     * @throws Exception
     */
    @Override
    public Object pushOffSetOrder(PushOrderParams params, BaseNode node) throws Exception {

        // 1.收到 mq 消息 查询所有能查的消息
        log.info("【推送退款单到适配样例】收到推送订单消息，准备推送小程序订单到适配样例，params:{}", GsonUtils.beanToJson(params));
        try {
            //先查询是否是重复数据
            PersonalOrderSyncLogRequest request = new PersonalOrderSyncLogRequest();
            request.setOrderNo(params.getOrderNo());
            request.setOrderType(1);
            Result<PersonalOrderSyncLogDto> logResult = personalOrderSyncLogApi.searchSyncOrderLog(request);
            if (logResult.isSuccess() && Objects.nonNull(logResult.getData())) {
                if (Objects.equals(logResult.getData().getStatus(), 2)) {
                    log.info("【推送退款单到适配样例】查询到重复数据，不进行推送，params:{}", GsonUtils.beanToJson(params));
                    return true;
                }
                log.info("【推送退款单到适配样例】查询到的数据非成功数据，进行推送，params:{}", GsonUtils.beanToJson(params));
            }
            PersonalOrderSyncLogDto syncLogbegin = new PersonalOrderSyncLogDto();
            syncLogbegin.setOrderNo(params.getOrderNo());
            syncLogbegin.setStatus(1);
            syncLogbegin.setOrderType(1);
            demoConvert.syncLog(syncLogbegin);

            DemoOrderInfoDTO orderDTO = new DemoOrderInfoDTO();
            QueryRefundOrderRequest queryRefundOrderRequest = new QueryRefundOrderRequest();
            queryRefundOrderRequest.setOrderNo(params.getOrderNo());
            //1.查询退单的订单信息
            Result<PersonalOrderRefundInfoDto> queryRefundOrderResult = personalOrderRefundInfoApi.queryRefundOrderByOrderNo(queryRefundOrderRequest);
            if (!queryRefundOrderResult.isSuccess() || Objects.isNull(queryRefundOrderResult.getData())) {
                log.error("【推送退款单到适配样例】查询退款单失败，订单号：{}", params.getOrderNo());
                throw new BizException("【推送退款单到适配样例】查询退款单失败，订单号：{}", params.getOrderNo());
            }
            //查询订单的同步信息
            QuerySyncDemoOrderRequest querySyncDemoOrderRequest = new QuerySyncDemoOrderRequest();
            querySyncDemoOrderRequest.setOrderNo(params.getOrderNo());
            Result<PersonalOrderSyncDto> orderSyncResult = personalOrderSyncApi.getSyncByOrderNo(querySyncDemoOrderRequest);
            if (!orderSyncResult.isSuccess() || Objects.isNull(orderSyncResult.getData())) {
                log.error("【推送退款单到适配样例】查询同步订单信息失败，订单号：{}", params.getOrderNo());
                throw new BizException("【推送退款单到适配样例】查询同步订单信息失败，订单号：{}", params.getOrderNo());
            }
            params.setMemberCode(queryRefundOrderResult.getData().getMemberCode());
            // 1.1 获取小程序订单详情
            OrderRequest var1 = new OrderRequest();
            var1.setOrderNo(params.getOrderNo());
            var1.setMemberCode(queryRefundOrderResult.getData().getMemberCode());
            Result<AppletOrderDetailDto> orderResult = personalOrderInfoApi.getOrderDetail(var1);
            if (!orderResult.isSuccess() || Objects.isNull(orderResult.getData())) {
                log.error("【推送退款单到适配样例】查询正向订单失败，订单号：{}", params.getOrderNo());
                throw new BizException("【推送退款单到适配样例】查询正向订单失败，订单号：{}", params.getOrderNo());
            }
            // 1.2 获取会员信息
            Result<MembersDetailsDto> memberResult = membersApi.membersDetails(params.getMemberCode());
            if (!memberResult.isSuccess() || Objects.isNull(memberResult.getData())) {
                log.error("【推送退款单到适配样例】查询会员信息失败，会员编号：{}", params.getMemberCode());
                throw new BizException("【推送退款单到适配样例】查询会员信息失败，会员编号：{}", params.getMemberCode());
            }

            // 1.3 获取门店信息
            //2.分别处理订单会员门店信息
            AppletOrderDetailDto orderDetail = orderResult.getData();
            //2.1 主表数据转化
            //2.2 订单明细数据转化
            //2.3 订单支付信息数据转化
            PersonalOrderRefundInfoDto refundInfoDto = queryRefundOrderResult.getData();
            QuerySyncDemoOrderRequest request1 = new QuerySyncDemoOrderRequest();
            request1.setOrderNo(refundInfoDto.getRefundOrderNo());
            Result<PersonalOrderSyncDto> syncByOrderNo = personalOrderSyncApi.getSyncByOrderNo(request1);
            if (syncByOrderNo.isSuccess() && Objects.nonNull(syncByOrderNo.getData())) {
                log.info("【推送退款单到适配样例】查询到重复数据，直接推送，params:{}", GsonUtils.beanToJson(params));
                orderDTO = GsonUtils.jsonToBean(syncByOrderNo.getData().getSyncOrderInfo(), DemoOrderInfoDTO.class);
            } else {
                orderDTO = demoConvert.billConvert(orderDetail, memberResult.getData());
                //冲减单数据转化
                demoConvert.offSetBillConvert(orderDTO, queryRefundOrderResult.getData(), orderSyncResult.getData());
            }
            //新增同步数据表
            demoConvert.sync(refundInfoDto.getRefundOrderNo(), orderDTO);
            log.info("【推送退款单到适配样例】订单转化完成，orderDTO:{}", GsonUtils.beanToJson(orderDTO));
            //3.1 订单记录表记录
            //3.2 调用适配样例
            String sId = orderDTO.getBills().get(0).getOgnid();
            log.info("【推送退款单到适配样例】调用适配样例，params:{}", GsonUtils.beanToJson(orderDTO));
            Boolean result = service.execute(orderDTO, node, params.getOrderNo(), sId);
//            Boolean result = true;
            //3.3 根据适配样例处理的结果，新增订单数据表
            String billId = orderDTO.getBills().get(0).getBillid();
            //3.3 根据适配样例处理的结果，新增订单数据表
            if (result != null && result) {
                //更新成功
                PersonalOrderSyncLogDto syncLog = new PersonalOrderSyncLogDto();
                syncLog.setOrderNo(params.getOrderNo());
                syncLog.setPlatformOrderId(billId);
                syncLog.setRefundNo(refundInfoDto.getRefundOrderNo());
                syncLog.setPlatformOrderId(billId);
                syncLog.setStatus(2);
                syncLog.setOrderType(1);
                demoConvert.syncLog(syncLog);
                return true;
            }
            //更新失败，增加失败的日志
            PersonalOrderSyncLogDto syncLog = new PersonalOrderSyncLogDto();
            syncLog.setOrderNo(params.getOrderNo());
            syncLog.setPlatformOrderId(billId);
            syncLog.setRefundNo(refundInfoDto.getRefundOrderNo());
            syncLog.setPlatformOrderId(billId);
            syncLog.setStatus(2);
            syncLog.setOrderType(1);
            demoConvert.syncLog(syncLog);
            return result;
        } catch (Exception e) {
            log.error("【推送退款单到适配样例】推送订单失败，params:{}", GsonUtils.beanToJson(params), e);
            //更新失败，增加失败的日志
            PersonalOrderSyncLogDto syncLog = new PersonalOrderSyncLogDto();
            syncLog.setOrderNo(params.getOrderNo());
            syncLog.setStatus(3);
            syncLog.setOrderType(1);
            demoConvert.syncLog(syncLog);
            throw e;
        }

    }


}
