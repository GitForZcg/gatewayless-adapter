package com.personal.demo.adapter.internal.compute.convert;

import com.personal.demo.pojo.dto.compute.PricePromotionDTO;
import com.personal.demo.pojo.dto.compute.RuleSpecialDishesDTO;
import com.personal.demo.pojo.dto.member.response.coupon.CouponRespDto;
import com.personal.demo.response.compute.PricePromotionResDto;
import com.personal.demo.response.compute.SpecialDishListDto;
import com.personal.demo.response.compute.UnusedCouponListDto;
import com.personal.demo.response.compute.ViewDishesItemResDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author sulu
 * @date 2026年02月04日 6:21 PM
 */
@Mapper
public interface ComputeConver {

    ComputeConver INSTANCE = Mappers.getMapper(ComputeConver.class);


    @Mapping(target = "productCode", source = "dishesNo")
    @Mapping(target = "productName", source = "dishesName")
    SpecialDishListDto convert(RuleSpecialDishesDTO ruleSpecialDishesDTO);

    List<SpecialDishListDto> convert(List<RuleSpecialDishesDTO> ruleSpecialDishesDTOS);


    List<PricePromotionResDto> convertPro(List<PricePromotionDTO> pricePromotionDTOS);


    PricePromotionResDto convertPro(PricePromotionDTO pricePromotionDTO);

    ViewDishesItemResDto convert(PricePromotionDTO pricePromotionDTO);

    List<ViewDishesItemResDto> convertDish(List<PricePromotionDTO> pricePromotionDTOS);

    List<UnusedCouponListDto> convertCoupon(List<CouponRespDto> pricePromotionDTOS);

    //ceType取自cExtend中第一条
    @Mapping(target = "subType", source = "couponRespDto", qualifiedByName = "getCeTypeFromExtend")
    @Mapping(target = "summary", source = "couponRespDto", qualifiedByName = "getSummary")
    UnusedCouponListDto convertCoupon(CouponRespDto couponRespDto);


    @Named("getCeTypeFromExtend")
    default String getCeTypeFromExtend(CouponRespDto dto) {
        // 1. 判空：避免空指针异常（必须加，否则运行时会报错）
        if (dto == null || dto.getCExtend() == null || dto.getCExtend().isEmpty()) {
            return null; // 或返回默认值，如 ""
        }
        // 2. 安全获取第一个元素的 ceType
        return dto.getCExtend().get(0).getCeType();
    }

    @Named("getSummary")
    default String getSummary(CouponRespDto dto) {
        // 1. 判空：避免空指针异常（必须加，否则运行时会报错）
        if (dto == null || dto.getCSummary() == null ) {
            return null; // 或返回默认值，如 ""
        }
        // 2. 安全获取第一个元素的 ceType
        return dto.getCSummary();
    }



}
