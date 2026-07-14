package com.personal.demo.rule.convert;

import com.personal.demo.conf.GsonFactory;
import com.personal.demo.enu.ServiceType;
import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.pojo.base.RequestContext;
import com.personal.demo.pojo.base.WhitelistConfig;
import com.personal.demo.rule.initalizer.InternalFlowNodeMappingInitializer;
import com.personal.demo.rule.registry.InternalFlowNodeMappingRegistry;
import com.personal.demo.rule.handler.WhitelistServiceHandler;
import com.personal.demo.utils.HttpRequestUtil;
import com.common.base.exception.BizException;
import com.google.gson.reflect.TypeToken;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static com.personal.demo.consts.BizCode.*;


/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 数据适配器转换器
 * @date 2025/7/2 10:49
 */
@Component
@Slf4j
public class DataAdapterConvert {

    private final Map<ServiceType, Map<BaseNode, Class<?>>> allFlowMap;
    private final WhitelistServiceHandler whitelistService;

    @Autowired
    public DataAdapterConvert(List<InternalFlowNodeMappingRegistry> configs, WhitelistServiceHandler whitelistService) {
        this.allFlowMap = InternalFlowNodeMappingInitializer.initMappings(configs);
        this.whitelistService = whitelistService;
    }

    /**
     * 内部数据转化
     */
    public <T> T convertData(ServiceType flowType, BaseNode action, Map<String, Object> bizDataMap) {

        Map<BaseNode, Class<?>> nodeClassMap = allFlowMap.get(flowType);
        if (nodeClassMap == null) {
            throw new BizException(TYPE_NOT_SUPPORTED, String.format("不支持的服务类型[%s]", flowType));
        }

        Class<?> targetClass = nodeClassMap.get(action);

        if (targetClass == null) {
            throw new BizException(CLASS_NOT_FOUND, String.format("找不到对应实体类, 服务类型[%s], 调用动作[%s]", flowType, action));
        }
        try {
            // 使用泛型方法，减少类型转换错误
            return GsonFactory.getInstance().fromJson(GsonFactory.getInstance().toJson(bizDataMap), TypeToken.get(targetClass).getType());
        } catch (Exception e) {
            throw new BizException(PARAMS_CONVERT_ERROR, String.format("数据转换异常: %s", e.getMessage()));
        }
    }

    /**
     * 上下文数据转化
     */
    public <T> T convertData(HttpServletRequest request, RequestContext context) {
        Map<String, Object> requestParams = HttpRequestUtil.requestParams(request);
        List<WhitelistConfig> serviceConfigs = whitelistService.getConfigsByServiceName(context.getServiceName());
        WhitelistConfig config = serviceConfigs.stream()
                .filter(conf -> conf.matchesApi(context.getApiPath(), context.getHttpMethod()))
                .findFirst().orElse(null);
        assert config != null;
        Class<?> clazz;
        try {
            clazz = Class.forName(config.getClassName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return GsonFactory.getInstance().fromJson(GsonFactory.getInstance().toJson(requestParams), TypeToken.get(clazz).getType());
    }
}
