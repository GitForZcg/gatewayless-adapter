package com.personal.demo.request.compute;

import com.personal.demo.request.group.ValidationGroups;
import com.personal.demo.response.compute.ProductPriceDto;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author sulu
 * @date 2026年02月11日 4:59 PM
 */
@Data
@Accessors(chain = true)
public class CartPriceRequest implements Serializable {

    /**
     * 门店编码
     */
    @NotEmpty(message = "门店编码不能为空", groups = ValidationGroups.computeGroup.class)
    private String storeCode;

    /**
     * 会员编码
     */
    @NotEmpty(message = "会员编码不能为空", groups = ValidationGroups.computeGroup.class)
    private String memberCode;


    /**
     * 产品列表
     */
    private List<ProductPriceDto> productPriceDtoList;




}
