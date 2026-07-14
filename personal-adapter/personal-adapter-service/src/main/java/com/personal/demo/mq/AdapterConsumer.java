package com.personal.demo.mq;

import com.personal.demo.adapter.internal.order.OrderFlow;
import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.enu.internal.node.OrderNode;
import com.personal.demo.request.base.BaseParams;
import com.personal.demo.request.order.PushOrderParams;
import com.common.base.exception.BizException;
import com.common.mq.config.RocketMqConsumerProperties;
import com.common.mq.consumer.PersonalRocketMqConsumer;
import com.common.tools.GsonUtils;
import com.personal.order.dto.msg.OrderMsgDto;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author sulu
 * @date 2025年08月15日 1:26 PM
 */
@Component("adapterConsumer")
@Slf4j
public class AdapterConsumer extends PersonalRocketMqConsumer {

    @Resource
    private RocketMqConsumerProperties rocketMqConsumerProperties;

    @Resource
    private OrderFlow orderFlow;

    @Value("${order-sheet.mq.finish-refund-order.topic}")
    private String finishRefundOrderTopic;
    @Value("${order-sheet.mq.finish-refund-order.tag}")
    private String finishRefundOrderTag;

    @Value("${order-sheet.mq.transfer-status-waiting-make.topic}")
    private String transferStatusWaitingMakeTopic;

    @Value("${order-sheet.mq.transfer-status-waiting-make.tag}")
    private String transferStatusWaitingMakeTag;

    @Value("${order-sheet.mq.pay-exception.tag}")
    private String payExceptionTag;

    @Value("${order-sheet.mq.pay-exception.topic}")
    private String payExceptionTopic;

    @PostConstruct
    private void init() throws MQClientException {
        super.initConsumer(rocketMqConsumerProperties);
    }
    @Override
    protected void consume(MessageExt messageExt, ConsumeConcurrentlyContext consumeConcurrentlyContext) throws Exception {
        String msgId = messageExt.getMsgId();
        String messageStr = new String(messageExt.getBody());
        String topic = messageExt.getTopic();
        String tags = messageExt.getTags();
        if (finishRefundOrderTopic.equals(topic) && tags.contains(finishRefundOrderTag)) {
            //取消订单完成
            log.info("consume 【推送退款单到适配样例】接收 rocketmq，orderedMsgDTO topic : {}, tags : {}, msgId : {}, msg : {}", topic, tags, msgId, messageStr);
            OrderMsgDto orderedMsgDTO = GsonUtils.jsonToBean(messageStr, OrderMsgDto.class);
            //下单后的操作-取消支付
            PushOrderParams params = new PushOrderParams();
            params.setOrderNo(orderedMsgDTO.getOrderNo());
            params.setMemberCode(orderedMsgDTO.getMemberCode());
            BaseParams base = new BaseParams<>();
            base.setBizData(params);
            BaseNode node = OrderNode.ORDER_PUSH_OFFSET;
            base.setBizData(params);
            try {
                orderFlow.pushOffSetOrder(base, node);
            } catch (Exception e) {
                log.info("consume 【推送退款单到适配样例】失败，orderedMsgDTO topic : {}, tags : {}, msgId : {}, msg : {}", topic, tags, msgId, messageStr);
                throw new BizException("推送退款单到适配样例失败,e:{}", e.getMessage());
            }

        }
        if (transferStatusWaitingMakeTopic.equals(topic) && tags.contains(transferStatusWaitingMakeTag)) {
            //订单转入待制作状态
            log.info("consume 【推送正向订单到适配样例】接收 rocketmq，orderedMsgDTO topic : {}, tags : {}, msgId : {}, msg : {}", topic, tags, msgId, messageStr);
            OrderMsgDto orderedMsgDTO = GsonUtils.jsonToBean(messageStr, OrderMsgDto.class);
            //下单后的操作-支付完成
            PushOrderParams params = new PushOrderParams();
            params.setOrderNo(orderedMsgDTO.getOrderNo());
            params.setMemberCode(orderedMsgDTO.getMemberCode());
            BaseParams base = new BaseParams<>();
            base.setBizData(params);
            BaseNode node = OrderNode.ORDER_PUSH;
            base.setBizData(params);
            try {
                orderFlow.pushOrder(base, node);
            } catch (Exception e) {
                log.error("consume 【推送正向订单到适配样例】失败，orderedMsgDTO topic : {}, tags : {}, msgId : {}, msg : {}", topic, tags, msgId, messageStr);
                throw new BizException("推送正向订单到适配样例失败e:{}",e.getMessage());
            }

        }
        if (payExceptionTopic.equals(topic) && tags.contains(payExceptionTag)) {
            //支付异常的订单
            log.info("consume 【推送正向订单到适配样例】【支付异常】接收 rocketmq，orderedMsgDTO topic : {}, tags : {}, msgId : {}, msg : {}", topic, tags, msgId, messageStr);
            OrderMsgDto orderedMsgDTO = GsonUtils.jsonToBean(messageStr, OrderMsgDto.class);
            //下单后的操作-支付异常但收到了钱
            PushOrderParams params = new PushOrderParams();
            params.setOrderNo(orderedMsgDTO.getOrderNo());
            params.setMemberCode(orderedMsgDTO.getMemberCode());
            BaseParams base = new BaseParams<>();
            base.setBizData(params);
            BaseNode node = OrderNode.ORDER_PUSH;
            base.setBizData(params);
            try {
                orderFlow.pushOrder(base, node);
            } catch (Exception e) {
                log.error("consume 【推送正向订单到适配样例】失败，orderedMsgDTO topic : {}, tags : {}, msgId : {}, msg : {}", topic, tags, msgId, messageStr);
                throw new BizException("推送正向订单到适配样例失败e:{}",e.getMessage());
            }
        }

    }


}
