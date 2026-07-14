package com.personal.demo.oss.mns;

import com.personal.demo.adapter.internal.order.convert.OrderConvert;
import com.personal.demo.conf.GsonFactory;
import com.personal.demo.conf.RedisFactory;
import com.personal.demo.dto.order.DemoBillDTO;
import com.personal.demo.dto.order.DemoOrderDTO;
import com.personal.demo.enu.RedisOrderKey;
import com.personal.demo.serivce.IOrderService;
import com.common.base.exception.BizException;
import com.common.oss.client.OssDownload;
import com.common.oss.config.OssConfig;
import com.common.oss.consumer.PersonalMnsConsumer;
import com.common.oss.model.Events;
import com.common.oss.model.OssEvent;
import com.common.redis.sdk.RedissionClient;
import com.common.tools.GsonUtils;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import java.lang.reflect.Type;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author Li QianQian
 * describe:
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DemoOrderSyncMnsConsumer extends PersonalMnsConsumer {


    private final OssConfig ossConfig;

    private final OssDownload ossDownload;

    private final Gson gson = GsonFactory.getInstance();


    private final OrderConvert orderConvert;

    private final RedissionClient redissionClient = RedisFactory.getClient(RedisFactory.RedisClient.DEFAULT_1);



    @Value("${oss.key}")
    private String ossKey;

    private final IOrderService orderService;


    @PostConstruct
    private void init() {
        super.initConsumer(ossConfig);

    }

    @Override
    protected void consume(Events events) throws Exception {


        log.info("收到消息:{}", GsonUtils.beanToJson(events));
        try {
            for (OssEvent event : events.getEvents()) {
                log.info("收到消息:{}", GsonUtils.beanToJson(event));

                // 根据事件类型处理
                String eventName = event.getEventName();
                String[] parts = eventName.split(":");
                String baseEventType = parts.length > 0 ? parts[0] : "";

                switch (baseEventType) {
                    case "ObjectCreated":
                        log.info("Handling ObjectCreated event for file: {}", event.getOss().getObject().getKey());
                        handleObjectCreatedEvent(event);
                        break;

                    default:
                        log.warn("Unknown event type: {}", eventName);
                        break;
                }


            }
        } catch (Exception e) {
            log.error("消费消息失败:{}", e);

            // 重试逻辑


        }


    }

    private void handleObjectCreatedEvent(OssEvent event) {
        String[] parts = event.getEventName().split(":");
        if (parts.length < 2) {
            log.warn("Invalid ObjectCreated event format: {}", event.getEventName());
            return;
        }

        String subEventType = parts[1];
        switch (subEventType) {
            case "PutObject":
                log.info("Handling ObjectCreated: PutObject event for file: {}", event.getOss().getObject().getKey());
                handleObjectCreatedPutObjectEvent(event);
                break;

            case "PostObject":
                log.info("Handling ObjectCreated: PostObject event for file: {}", event.getOss().getObject().getKey());
                handleObjectCreatedPutObjectEvent(event);
                break;

            default:
                log.warn("Unknown ObjectCreated sub-event type: {}", subEventType);
                break;
        }
    }


    private void handleObjectCreatedPutObjectEvent(OssEvent event) {
        log.info("Handling object created event: {}", GsonUtils.beanToJson(event));

        // 事件名称
        String eventName = event.getEventName();
        // 文件全路径
        String key = event.getOss().getObject().getKey();
        log.info("收到消息，操作类型:{},key:{}", eventName, key);


        // 匹配key的文件目录
        String[] fileCatalog = event.getOss().getObject().getKey().split("/");
        String filePrefix = fileCatalog.length > 0 ? fileCatalog[0] : "";
        if (!filePrefix.equals(ossKey)) {
            log.info("当前事件 - 文件前缀不匹配  文件前缀:{}", filePrefix);
            return;
        }


        String fileContent = ossDownload.downloadFile(event.getOss().getBucket().getName(), key);
        log.info("下载文件，文件内容:{}", fileContent);
        Type dayCarryoverType = new TypeToken<DemoOrderDTO>() {
        }.getType();
        DemoOrderDTO demoOrderDTO = GsonUtils.jsonToBean(fileContent, dayCarryoverType);
        log.info("下载文件，转为适配样例数据 :{}", GsonUtils.beanToJson(demoOrderDTO));


        if (ObjectUtils.isEmpty(demoOrderDTO)
                || CollectionUtils.isEmpty(demoOrderDTO.getBills())
                || ObjectUtils.isEmpty(demoOrderDTO.getBills().get(0))
                || ObjectUtils.isEmpty(demoOrderDTO.getBills().get(0).getBillid())
                || ObjectUtils.isEmpty(demoOrderDTO.getBills().get(0).getBillno())) {
            log.error("下载文件，适配样例数据  bill数据为空:{}", GsonUtils.beanToJson(demoOrderDTO));
            throw new BizException("订单-bill数据为空");
        }

        if (ObjectUtils.isEmpty(demoOrderDTO.getBills().get(0).getBusinesstype())
                || ObjectUtils.isEmpty(demoOrderDTO.getBills().get(0).getChannels())
                || ObjectUtils.isEmpty(demoOrderDTO.getBills().get(0).getSource())) {
            log.error("下载文件，适配样例数据  bill数据-判断条件为空:{}", GsonUtils.beanToJson(demoOrderDTO));
            throw new BizException("订单-bill数据-判断条件为空");
        }
        if (Objects.equals(demoOrderDTO.getBills().get(0).getBusinesstype(), "1")
                && demoOrderDTO.getBills().get(0).getChannels() == 2
                && demoOrderDTO.getBills().get(0).getSource() == 2) {
            return;
        }

        if (Objects.equals(demoOrderDTO.getBills().get(0).getBusinesstype(), "2")
                && demoOrderDTO.getBills().get(0).getChannels() == 4
                && demoOrderDTO.getBills().get(0).getSource() == 2) {
            if (ObjectUtils.isEmpty(demoOrderDTO.getBills().get(0).getTatype())) {
                log.error("下载文件，适配样例数据  bill数据-判断条件为空:{}", GsonUtils.beanToJson(demoOrderDTO));
                throw new BizException("订单-bill数据-判断条件为空");
            }
            if (demoOrderDTO.getBills().get(0).getTatype() == 4) {
                return;
            }
        }

        boolean orderExistCondition = orderConvert.queryOrderSyncResult(demoOrderDTO.getBills().get(0).getBillid());
        //boolean orderExistCondition = orderConvert.queryOrderSyncResult("123");
        if (orderExistCondition) {
            return;
        }
        RLock lock = null;

        try {
            lock = redissionClient.getRLock(RedisOrderKey.ORDER_SYNC_LOCK_KEY + demoOrderDTO.getBills().get(0).getBillid());
            lock.lock(60 * 1, TimeUnit.SECONDS);
            orderExistCondition = orderConvert.queryOrderSyncResult(demoOrderDTO.getBills().get(0).getBillid());
            //orderExistCondition = orderConvert.queryOrderSyncResult("123");
            if (orderExistCondition) {
                return;
            }


            orderService.pullOrder(demoOrderDTO);


        } finally {
            assert lock != null;
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }


    }


}
