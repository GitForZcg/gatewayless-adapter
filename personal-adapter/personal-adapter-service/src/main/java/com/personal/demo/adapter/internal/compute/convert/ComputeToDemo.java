package com.personal.demo.adapter.internal.compute.convert;

import com.personal.demo.enu.internal.base.CouponType;
import com.personal.demo.pojo.dto.compute.CartPromotionRequestDTO;
import com.personal.demo.pojo.dto.compute.CouponAssetDTO;
import com.personal.demo.pojo.dto.compute.DishesDTO;
import com.personal.demo.pojo.dto.compute.WXCouponsDTO;
import com.personal.demo.pojo.dto.member.response.coupon.CExtend;
import com.personal.demo.pojo.dto.member.response.coupon.CouponRespDto;
import com.personal.demo.request.compute.CartPriceRequest;
import com.personal.demo.request.compute.CouponChooseRequest;
import com.personal.demo.request.compute.PreOrderPriceRequest;
import com.personal.demo.response.compute.ProductPriceDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author sulu
 * @date 2026年02月25日 4:49 PM
 */
@Slf4j
@Component
public class ComputeToDemo {

    @Value("${size-no.size}")
    public String sizeNo;


    public void cartParamConvert(CartPriceRequest request, CartPromotionRequestDTO result) {
        //处理通用信息
        result.setSource(1);
        result.setMajorVersion(1);
        result.setTeaPrice(0);
        result.setServicePrice(0);
        result.setDeliveryPrice(0);
        result.setTipPrice(0);
        result.setIsLogin("true");
        List<DishesDTO> dishesList = new ArrayList<>();
        if (CollectionUtils.isEmpty(request.getProductPriceDtoList())) {
            log.error("cartParamConvert 菜品列表为空");
        }
        for (ProductPriceDto productPriceDto : request.getProductPriceDtoList()) {
            DishesDTO dishes = new DishesDTO();
            Integer crossedPrice = productPriceDto.getCrossedPrice().multiply(new BigDecimal("100")).intValue();
            Integer addPrice = Objects.isNull(productPriceDto.getAddPrice()) ? 0 : productPriceDto.getAddPrice().multiply(new BigDecimal("100")).intValue();
            dishes.setAddPrice(addPrice);
            dishes.setDishesName(productPriceDto.getProductName());
            dishes.setDishesNo(productPriceDto.getProductCode());
            dishes.setCount(productPriceDto.getSkuNum());
            dishes.setMemberPrice(crossedPrice);
            dishes.setOriginalPrice(crossedPrice);
            dishes.setPrice(crossedPrice);
            dishes.setSalePrice(crossedPrice);
            dishes.setSizeNo(sizeNo);
            dishes.setGroupFlag(productPriceDto.getSkuCode());
            dishes.setGoodsType(productPriceDto.getProductType());
            dishesList.add(dishes);
        }
        result.setDishesList(dishesList);

    }

    public void preOrderParamConvert(PreOrderPriceRequest request, CartPromotionRequestDTO result) {
        //处理通用信息
        result.setSource(1);
        result.setMajorVersion(1);
        result.setTeaPrice(0);
        result.setServicePrice(0);
        result.setDeliveryPrice(0);
        result.setTipPrice(0);
        result.setIsLogin("true");
        result.setPaymentList(request.getPaymentList());
        List<DishesDTO> dishesList = new ArrayList<>();
        if (CollectionUtils.isEmpty(request.getProductPriceDtoList())) {
            log.error("cartParamConvert 菜品列表为空");
        }
        for (ProductPriceDto productPriceDto : request.getProductPriceDtoList()) {
            DishesDTO dishes = new DishesDTO();
            Integer crossedPrice = productPriceDto.getCrossedPrice().multiply(new BigDecimal("100")).intValue();
            Integer addPrice = Objects.isNull(productPriceDto.getAddPrice()) ? 0 : productPriceDto.getAddPrice().multiply(new BigDecimal("100")).intValue();
            dishes.setAddPrice(addPrice);
            dishes.setDishesName(productPriceDto.getProductName());
            dishes.setDishesNo(productPriceDto.getProductCode());
            dishes.setCount(productPriceDto.getSkuNum());
            dishes.setMemberPrice(crossedPrice);
            dishes.setOriginalPrice(crossedPrice);
            dishes.setPrice(crossedPrice);
            dishes.setSalePrice(crossedPrice);
            dishes.setSizeNo(sizeNo);
            dishes.setGroupFlag(productPriceDto.getSkuCode());
            dishes.setGoodsType(productPriceDto.getProductType());
            dishes.setSkuId(productPriceDto.getSkuCode());


            dishesList.add(dishes);
        }
        result.setDishesList(dishesList);
        //优惠券
//        List<WXCouponsDTO> weixinCoupons = new ArrayList<>();
//        List<String> couponIds = new ArrayList<>();
//        if (CollectionUtils.isNotEmpty(couponList)){
//            for (CouponRespDto couponDetail : couponList) {
//                for (String couponId : couponDetail.getCoupon_ids()) {
//                    WXCouponsDTO wxCoupons = new WXCouponsDTO();
//                    wxCoupons.setId(couponId);
//                    wxCoupons.setTemplateId(couponDetail.getTemplate_id());
//                    wxCoupons.setPmid(CouponType.valueOf(couponDetail.getType()).pmid);
//                    weixinCoupons.add(wxCoupons);
//                    couponIds.add(couponId);
//                }
//            }
//        }
        if (request.getFirstMatch()) {
            //是首次计算
            result.setIsUseCoupon(1);
        }else{
            result.setIsUseCoupon(0);
        }
        CouponChooseRequest couponChooseRequest = request.getCouponChooseRequest();
        if (!Objects.isNull(couponChooseRequest) && !Objects.isNull(couponChooseRequest.getCouponCode())){
            WXCouponsDTO wxCoupons = new WXCouponsDTO();
            wxCoupons.setId(couponChooseRequest.getCouponCode());
            wxCoupons.setTemplateId(couponChooseRequest.getTemplateCode());
            wxCoupons.setPmid(CouponType.DAI_JIN_QUAN.pmid);
            result.setWeixinCoupons(Arrays.asList(wxCoupons));
        }
//        result.setCouponList(couponIds);
//        result.setWeixinCoupons(weixinCoupons);                                                                     1`````````````````
    }


    public void couponParamConvert(List<CouponAssetDTO> coupons, List<CouponRespDto> couponList) {
        for (CouponRespDto coupon : couponList) {
            CouponAssetDTO couponDetail = new CouponAssetDTO();
            BigDecimal deno = new BigDecimal(coupon.getDeno()).multiply(new BigDecimal("100"));
            couponDetail.setCoupon_id(coupon.getCoupon_ids());
            couponDetail.setTemplate_id(coupon.getTemplate_id());
            couponDetail.setType(coupon.getType());
            couponDetail.setMoney(deno.intValue());
            couponDetail.setTitle(coupon.getTitle());
            couponDetail.setProducts(coupon.getProducts().stream().filter(Objects::nonNull).map(String::valueOf).collect(Collectors.toList()));
            couponDetail.setSids(coupon.getSids().stream().filter(Objects::nonNull).map(String::valueOf).collect(Collectors.toList()));
            couponDetail.setIs_diy_deno(coupon.getIs_diy_deno());

            couponDetail.setIs_diy_deno(coupon.getIs_diy_deno());
            couponDetail.setLimit_mix_coupon(coupon.getLimit_mix_coupon().stream().filter(Objects::nonNull).map(String::valueOf).collect(Collectors.toList()));
            couponDetail.setMix_use_value(coupon.getMix_use_value());
            if (CollectionUtils.isEmpty(coupon.getCExtend())){
                log.error("couponParamConvert 券扩展字段为空");
                return;
            }
            //取第一条
            CExtend cExtend = coupon.getCExtend().get(0);
            Integer ceType = Integer.valueOf(cExtend.getCeType());
            couponDetail.setCetype(ceType);
            if (Objects.equals(ceType, 2)) {
                couponDetail.setExpand_id(coupon.getProducts_ext());
                couponDetail.setProducts(new ArrayList<>());
            } else if (Objects.equals(ceType, 7)) {
                couponDetail.setCetype(ceType);
                List<String> products = coupon.getProducts().stream().filter(Objects::nonNull).map(String::valueOf).collect(Collectors.toList());
                couponDetail.setExpand_id(coupon.getDiscountRate().toString() + "," + StringUtils.join(products, ","));
                couponDetail.setProducts(new ArrayList<>());
            }else {
                if (CollectionUtils.isNotEmpty(coupon.getProducts())){
                    List<String> products = coupon.getProducts().stream().filter(Objects::nonNull).map(String::valueOf).collect(Collectors.toList());
//                    couponDetail.setExpand_id(StringUtils.join(products, ","));
                    couponDetail.setProducts(coupon.getProducts());
                }
            }
            String originalTimeStr = coupon.getFailure_time();
            LocalDateTime localDateTime = LocalDateTime.parse(
                    originalTimeStr.replace(" ", "T"), // 仅替换空格为T，复用ISO_LOCAL_DATE_TIME的解析逻辑
                    DateTimeFormatter.ISO_LOCAL_DATE_TIME // JDK预定义的ISO本地时间格式化器，无需手写格式
            );
            String targetTimeStr = localDateTime
                    .atOffset(ZoneOffset.UTC) // 绑定UTC时区，生成OffsetDateTime（比ZonedDateTime更轻量）
                    .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
            couponDetail.setFailureTime(targetTimeStr);
            coupons.add(couponDetail);
        }
    }
}
