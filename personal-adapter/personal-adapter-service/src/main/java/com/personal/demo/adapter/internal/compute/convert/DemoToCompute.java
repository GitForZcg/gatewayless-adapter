package com.personal.demo.adapter.internal.compute.convert;

import com.personal.demo.pojo.dto.compute.CartPromotionRequestDTO;
import com.personal.demo.pojo.dto.compute.DishesDTO;
import com.personal.demo.pojo.dto.compute.PricePromotionDTO;
import com.personal.demo.pojo.dto.member.response.coupon.CouponRespDto;
import com.personal.demo.response.compute.*;
import com.common.tools.GsonUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author sulu
 * @date 2026年02月25日 6:04 PM
 */
@Slf4j
@Component
public class DemoToCompute {

    public CartPriceDto cartConvert(PricePromotionDTO pricePromotionDTO, List<DishesDTO> dishesList) {
        log.info("购物车出参转化 pricePromotionDTO:{}", GsonUtils.beanToJson(pricePromotionDTO));
        CartPriceDto cartPriceDto = new CartPriceDto();
        if(Objects.isNull(pricePromotionDTO) || Objects.isNull(pricePromotionDTO.getOrderSumAmount())){
            log.error("pricePromotionDTO is null or orderSumAmount is null");
            return null;
        }
        // 预计到手金额
        // 1. 获取售价，处理null（根据业务需求，null时可设为0或抛异常）
        Integer finalAmount = pricePromotionDTO.getFinalAmount();
        if (Objects.isNull(finalAmount)) {
            finalAmount  = 0; // 业务上如果不允许为null，可抛IllegalArgumentException
        }
        BigDecimal payableAmount = BigDecimal.valueOf(finalAmount)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        cartPriceDto.setPayableAmount(payableAmount);
        // 原价总和
        Integer originalPriceSum = pricePromotionDTO.getItemOriginalPriceSum();
        if (Objects.isNull(originalPriceSum)) {
            originalPriceSum = 0; // 同上，处理null或抛异常
        }
        BigDecimal totalAmount = BigDecimal.valueOf(originalPriceSum)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        cartPriceDto.setTotalAmount(totalAmount);

        //优惠金额
        Integer discountAmount = pricePromotionDTO.getDiscountAmount();
        if (Objects.isNull(discountAmount)) {
            discountAmount = 0; // 同上，处理null或抛异常
        }
        BigDecimal totalReducedAmount = BigDecimal.valueOf(discountAmount)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        cartPriceDto.setTotalReducedAmount(totalReducedAmount);

        // 产品列表
        List<ProductPriceDto> productPriceDtoList = new ArrayList<>();
        if (Objects.isNull(pricePromotionDTO.getExecuteInfo()) || CollectionUtils.isEmpty(pricePromotionDTO.getExecuteInfo().getExecuteList())){
            log.error("executeInfo is null or executeList is empty");
            return null;
        }
        pricePromotionDTO.getExecuteInfo().getExecuteList().forEach(execute -> {
            ProductPriceDto productPriceDto = new ProductPriceDto();
            productPriceDto.setProductCode(execute.getDishesNo());
            // 原价需要
            DishesDTO dishesDTO = dishesList.stream().filter(d -> d.getDishesNo().equals(execute.getDishesNo())).findFirst().orElse(null);
            if (Objects.isNull(dishesDTO)){
                log.error("dishesDTO is null");
                return;
            }
            productPriceDto.setCrossedPrice(BigDecimal.valueOf(dishesDTO.getOriginalPrice()).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));
            productPriceDto.setGroupFlag(execute.getGroupFlag());
            productPriceDto.setSkuCode(execute.getGroupFlag());

            if (Objects.isNull(execute.getSpecialUnitPrice()) && !Objects.isNull(execute.getDiscountRate())){
                productPriceDto.setPrice(BigDecimal.valueOf(dishesDTO.getOriginalPrice()).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP).multiply(new BigDecimal(execute.getDiscountRate())).add(BigDecimal.valueOf(execute.getAddPrice()).divide(BigDecimal.valueOf(100))));
            }else{
                productPriceDto.setPrice((BigDecimal.valueOf(execute.getPrice()).add(BigDecimal.valueOf(execute.getAddPrice()))).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));
            }
            productPriceDtoList.add(productPriceDto);
        });
        cartPriceDto.setProductPriceDtoList(productPriceDtoList);
        return cartPriceDto;

    }

    /**
     * 预下单参数转化
     * @param pricePromotionListDTO
     * @param couponList
     * @return
     */
    public PreOrderResDto preOrderParamConvert(List<PricePromotionDTO> pricePromotionListDTO, List<CouponRespDto> couponList) {
        log.info("预下单参数转化 pricePromotionListDTO:{}", GsonUtils.beanToJson(pricePromotionListDTO));
        PreOrderResDto preOrderResDto = new PreOrderResDto();
        if (CollectionUtils.isEmpty(pricePromotionListDTO)) {
            log.error("pricePromotionListDTO is empty");
            return preOrderResDto;
        }
        List<PricePromotionResDto> resultList = ComputeConver.INSTANCE.convertPro(pricePromotionListDTO);
        List<UnusedCouponListDto>  resultCouponList = ComputeConver.INSTANCE.convertCoupon(couponList);
        preOrderResDto.setPricePromotionListDTO(resultList);
        preOrderResDto.setCouponList(resultCouponList);
        return preOrderResDto;
    }
}
