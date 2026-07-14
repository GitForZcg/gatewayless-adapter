package com.personal.demo.serivce;

import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.pojo.dto.request.product.ExternalProductCommonRequest;
import com.personal.demo.pojo.dto.request.product.ExternalProductDetailRequest;
import com.personal.demo.pojo.dto.response.product.*;
import com.personal.demo.request.product.ProductNotificationParam;

import java.util.List;

/**
 * 内部系统菜项服务
 * @author 86136
 */
public interface IInternalProductService {
    /**
     * 菜项同步通知
     * @param bizData
     * @param node
     * @return
     */
    Object notification(ProductNotificationParam bizData, BaseNode node);
}
