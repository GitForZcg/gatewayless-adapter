package com.personal.demo.rule.registry;

import com.personal.demo.anno.ExternalApiType;
import com.personal.demo.enu.external.base.ExternalBaseNode;
import com.personal.demo.rule.exception.AdapterException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static com.personal.demo.pojo.wrapper.AdapterErrorCode.NODE_NOT_FOUND_ERROR;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 外部节点注册表
 * @date 2025/7/7 10:57
 */
@Component
@Slf4j
public class ExternalNodeRegistry implements InitializingBean {

    private final Map<String, Map<String, ExternalBaseNode>> nodeCache = new ConcurrentHashMap<>();


    @Override
    public void afterPropertiesSet() throws Exception {
        scanAndRegisterNodes();
    }

    /**
     * 扫描注册节点
     */

    @SuppressWarnings("unchecked")
    private void scanAndRegisterNodes() {
        // 使用Spring自带的扫描器
        ClassPathScanningCandidateComponentProvider scanner =
                new ClassPathScanningCandidateComponentProvider(false);

        // 添加过滤器，扫描带@ExternalApiType注解的类
        scanner.addIncludeFilter(new AnnotationTypeFilter(ExternalApiType.class));

        // 扫描指定包路径（可以从配置中读取）
        String basePackage = "com.personal.demo.enu.external"; // 替换为你的包路径
        Set<BeanDefinition> candidates = scanner.findCandidateComponents(basePackage);

        for (BeanDefinition candidate : candidates) {
            try {
                Class<?> clazz = Class.forName(candidate.getBeanClassName());
                if (clazz.isEnum() && ExternalBaseNode.class.isAssignableFrom(clazz)) {
                    registerNodeClass((Class<? extends ExternalBaseNode>) clazz);
                }
            } catch (ClassNotFoundException e) {
                log.warn("无法加载枚举类: {}", candidate.getBeanClassName(), e);
            }
        }
    }

    /**
     * 注册节点
     */

    private void registerNodeClass(Class<? extends ExternalBaseNode> nodeClass) {
        ExternalApiType annotation = nodeClass.getAnnotation(ExternalApiType.class);
        if (annotation != null) {
            String apiType = annotation.value();
            Map<String, ExternalBaseNode> nodes = new HashMap<>();
            if (nodeClass.isEnum()) {
                ExternalBaseNode[] enumConstants = nodeClass.getEnumConstants();
                for (ExternalBaseNode node : enumConstants) {
                    nodes.put(((Enum<?>) node).name(), node);
                }
            }
            nodeCache.put(apiType, nodes);
        }
    }

    /**
     * 获取节点
     */
    public ExternalBaseNode getNode(String apiType, String apiSubType) {
        Map<String, ExternalBaseNode> nodes = nodeCache.get(apiType);
        ExternalBaseNode node = nodes.get(apiSubType);
        if (node == null) {
            throw new AdapterException(NODE_NOT_FOUND_ERROR);
        }
        return node;
    }

    public Set<String> getAllApiTypes() {
        return nodeCache.keySet();
    }
}
