package com.personal.demo.rule.handler;

import com.personal.demo.consts.Separator;
import com.personal.demo.enu.external.base.ExternalBaseNode;
import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.rule.exception.AdapterException;
import com.personal.demo.rule.initalizer.ValidationGroupInitializer;
import com.personal.demo.rule.registry.ValidationExternalGroupRegistry;
import com.personal.demo.rule.registry.ValidationGroupRegistry;
import com.personal.demo.rule.validation.BaseValidationException;
import com.personal.demo.rule.validation.ExternalBaseValidationException;
import com.personal.demo.rule.validation.ValidationError;
import com.common.base.exception.BizException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.personal.demo.consts.BizCode.VALID_GROUP_VALID;
import static com.personal.demo.pojo.wrapper.AdapterErrorCode.VALID_GROUP_MISSING;


/**
 * @author zhangjunyi
 * @version 1.0
 * @description: 参数校验器
 * @date 2025/7/2 10:53
 */
@Component
@Slf4j
public class BeanValidationProcessor {
    private final Validator validator;

    private final Map<BaseNode, Class<?>> nodeValidationGroupMap;
    private final Map<ExternalBaseNode, Class<?>> externalApiNodeClassMap;

    // 新增：字段反射信息缓存，提高性能
    private final ConcurrentHashMap<Class<?>, CachedFieldInfo[]> fieldCache = new ConcurrentHashMap<>();

    @Autowired
    public BeanValidationProcessor(Validator validator, List<ValidationGroupRegistry> configs, List<ValidationExternalGroupRegistry> externalConfigs) throws ClassNotFoundException {
        this.validator = validator;
        this.nodeValidationGroupMap = ValidationGroupInitializer.initGroups(configs);
        this.externalApiNodeClassMap = ValidationGroupInitializer.initExternalApiGroups(externalConfigs);
    }

    /**
     * 根据动作选择合适的验证组
     */
    public Class<?> getValidationGroup(BaseNode node) {
        Class<?> groupClass = nodeValidationGroupMap.get(node);
        if (ObjectUtils.isEmpty(groupClass)) {
            throw new BizException(VALID_GROUP_VALID, String.format("找不到对应的验证组[%s]", node));
        }
        return groupClass;
    }

    /**
     * 外部服务验证组
     */
    public Class<?> getValidationGroup(ExternalBaseNode node) {
        Class<?> groupClass = externalApiNodeClassMap.get(node);
        if (ObjectUtils.isEmpty(groupClass)) {
            throw new AdapterException(VALID_GROUP_MISSING.code,
                    String.format(VALID_GROUP_MISSING.message, groupClass),
                    VALID_GROUP_MISSING.type
            );
        }
        return groupClass;
    }


    /**
     * 内部验证方法
     */
    public void validate(Object object, Class<?>... groups) {

        if (log.isDebugEnabled()) {
            log.debug("开始内部验证: {}", object.getClass().getSimpleName());
        }

        // 调用公共验证逻辑，传入内部异常处理器
        ValidationError firstError = findFirstValidationError(object, groups);

        if (firstError != null) {
            if (log.isDebugEnabled()) {
                log.debug("内部验证失败: {} - {}", firstError.field(), firstError.message());
            }
            throw new BaseValidationException(firstError);
        }

        if (log.isDebugEnabled()) {
            log.debug("内部验证通过");
        }
    }

    /**
     * 外部验证方法 - 重构后无重复逻辑
     */
    public void validateExternalParams(Object object, Class<?>... groups) {

        if (log.isDebugEnabled()) {
            log.debug("开始内部验证: {}", object.getClass().getSimpleName());
            log.debug("对象实际类型: {}", object.getClass().getName());
        }

        // 调用公共验证逻辑，传入外部异常处理器
        ValidationError firstError = findFirstValidationError(object, groups);

        if (firstError != null) {
            log.debug("验证失败，返回最浅层错误: {} - {}", firstError.field(), firstError.message());
            throw new ExternalBaseValidationException(firstError);
        }

        log.debug("验证通过");
    }

    /**
     * 核心公共验证逻辑 - 修复随机性问题
     */
    private ValidationError findFirstValidationError(Object object, Class<?>... groups) {

        if (object == null) {
            return null;
        }

        // 1. 优先检查当前对象的错误（深度0，最高优先级）
        Set<ConstraintViolation<Object>> violations = validator.validate(object, groups);
        if (!violations.isEmpty()) {
            // 修复：对违规信息排序，确保结果稳定
            ConstraintViolation<Object> first = violations.stream()
                    .min(Comparator.comparing(v -> v.getPropertyPath().toString()))
                    .orElse(violations.iterator().next());

            return ValidationError.build(
                    first.getPropertyPath().toString(),
                    first.getMessage()
            );
        }

        // 2. 递归检查嵌套对象错误
        return findNestedValidationError(object, "", groups, 1);
    }

    /**
     * 验证单个对象 - 修复随机性问题
     */
    private ValidationError findSingleObjectValidationError(Object object, String fieldPath,
                                                            Class<?>[] groups, int depth) {

        // 先验证当前对象本身
        Set<ConstraintViolation<Object>> violations = validator.validate(object, groups);
        if (!violations.isEmpty()) {
            // 修复：对违规信息排序，确保结果稳定
            ConstraintViolation<Object> first = violations.stream()
                    .min(Comparator.comparing(v -> v.getPropertyPath().toString()))
                    .orElse(violations.iterator().next());

            String fullPath = fieldPath + "." + first.getPropertyPath();
            return ValidationError.build(fullPath, first.getMessage());
        }

        // 递归验证更深层的嵌套对象
        return findNestedValidationError(object, fieldPath, groups, depth + 1);
    }

    /**
     * 递归查找嵌套对象的验证错误
     */
    private ValidationError findNestedValidationError(Object object, String prefix, Class<?>[] groups, int depth) {

        if (object == null || depth > 10) { // 限制递归深度防止栈溢出
            return null;
        }

        // 使用缓存的字段信息
        CachedFieldInfo[] cachedFields = getOrCacheFields(object.getClass());

        for (CachedFieldInfo fieldInfo : cachedFields) {
            try {
                Object fieldValue = fieldInfo.field.get(object);
                if (fieldValue == null) {
                    continue;
                }
                //构建属性路径
                String fieldPath = buildPath(prefix, fieldInfo.fieldName);

                ValidationError error;

                if (fieldInfo.isCollection) {
                    error = findCollectionValidationError((Collection<?>) fieldValue, fieldPath, groups, depth);
                } else if (fieldInfo.isArray) {
                    error = findArrayValidationError(fieldValue, fieldPath, groups, depth);
                } else {
                    error = findSingleObjectValidationError(fieldValue, fieldPath, groups, depth);
                }

                if (error != null) {
                    return error; // 找到第一个错误立即返回
                }

            } catch (IllegalAccessException e) {
                log.warn("无法访问字段: {}", fieldInfo.fieldName);
            }
        }

        return null;
    }

    /**
     * 验证集合中的对象
     */
    private ValidationError findCollectionValidationError(Collection<?> collection, String fieldPath,
                                                          Class<?>[] groups, int depth) {

        int index = 0;
        for (Object item : collection) {
            if (item != null) {
                String itemPath = fieldPath + "[" + index + "]";
                ValidationError error = findSingleObjectValidationError(item, itemPath, groups, depth);
                if (error != null) {
                    return error;
                }
            }
            index++;
        }
        return null;
    }

    /**
     * 验证数组中的对象
     */
    private ValidationError findArrayValidationError(Object array, String fieldPath,
                                                     Class<?>[] groups, int depth) {

        int length = Array.getLength(array);
        for (int i = 0; i < length; i++) {
            Object item = Array.get(array, i);
            if (item != null) {
                String itemPath = fieldPath + "[" + i + "]";
                ValidationError error = findSingleObjectValidationError(item, itemPath, groups, depth);
                if (error != null) {
                    return error;
                }
            }
        }
        return null;
    }


    /**
     * 获取或缓存字段信息
     */
    private CachedFieldInfo[] getOrCacheFields(Class<?> clazz) {
        return fieldCache.computeIfAbsent(clazz, this::cacheFieldsForClass);
    }

    /**
     * 为类缓存字段信息
     */
    private CachedFieldInfo[] cacheFieldsForClass(Class<?> clazz) {
        List<CachedFieldInfo> cachedFields = new ArrayList<>();

        Class<?> currentClass = clazz;
        while (currentClass != null && currentClass != Object.class) {

            Field[] fields = currentClass.getDeclaredFields();
            for (Field field : fields) {

                if (field.isAnnotationPresent(Valid.class)) {
                    field.setAccessible(true);

                    CachedFieldInfo fieldInfo = new CachedFieldInfo();
                    fieldInfo.field = field;
                    fieldInfo.fieldName = field.getName();
                    fieldInfo.isCollection = Collection.class.isAssignableFrom(field.getType());
                    fieldInfo.isArray = field.getType().isArray();

                    cachedFields.add(fieldInfo);
                }
            }

            currentClass = currentClass.getSuperclass();
        }

        //简单排序确保顺序稳定
        cachedFields.sort((a, b) -> {
            // 简单字段优先，复杂字段靠后
            boolean aIsComplex = a.isCollection || a.isArray || isCustomObject(a.field.getType());
            boolean bIsComplex = b.isCollection || b.isArray || isCustomObject(b.field.getType());

            if (aIsComplex != bIsComplex) {
                return aIsComplex ? 1 : -1; // 简单字段优先
            }

            // 同类型按名称排序
            return a.fieldName.compareTo(b.fieldName);
        });

        return cachedFields.toArray(new CachedFieldInfo[0]);
    }

    /**
     * 判断是否为自定义对象（需要递归验证的）
     */
    private boolean isCustomObject(Class<?> type) {
        return !type.isPrimitive()
                && !type.getName().startsWith("java.")
                && !type.isEnum();
    }

    /**
     * 优化的路径构建
     */
    private String buildPath(String prefix, String fieldName) {
        if (prefix == null || prefix.isEmpty()) {
            return fieldName;
        }
        return prefix + Separator.DOT + fieldName;
    }

    /**
     * 缓存的字段信息内部类
     */
    private static class CachedFieldInfo {
        Field field;
        String fieldName;
        boolean isCollection;
        boolean isArray;
    }
}