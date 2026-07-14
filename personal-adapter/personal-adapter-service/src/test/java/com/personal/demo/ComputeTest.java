package com.personal.demo;

import com.personal.demo.adapter.internal.compute.ComputeFlow;
import com.personal.demo.adapter.internal.compute.convert.DemoToCompute;
import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.enu.internal.node.ComputeNode;
import com.personal.demo.pojo.dto.compute.CartPromotionRequestDTO;
import com.personal.demo.pojo.dto.compute.PricePromotionDTO;
import com.personal.demo.pojo.dto.compute.PromotionRequestDTO;
import com.personal.demo.pojo.dto.compute.PromotionStoreDTO;
import com.personal.demo.request.base.BaseParams;
import com.personal.demo.request.compute.CartPriceRequest;
import com.personal.demo.request.compute.PreOrderPriceRequest;
import com.personal.demo.request.compute.StorePromotionRequest;
import com.personal.demo.response.compute.CartPriceDto;
import com.personal.demo.response.compute.ProductPriceDto;
import com.personal.demo.response.compute.SpecialDishListDto;
import com.personal.demo.serivce.AbstractComputeMD5Service;
import com.common.tools.GsonUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author sulu
 * @date 2026年01月27日 2:14 PM
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class ComputeTest {

    @Resource
    private ComputeFlow computeFlow;
    @Resource
    private AbstractComputeMD5Service md5Service;

    @DisplayName("测试门店活动")
    @org.junit.jupiter.api.Test
    void testPush() {
        StorePromotionRequest params = new StorePromotionRequest();

        params.setMemberCode("MR295663433272524800");
        params.setStoreCode("MD00000011");
        BaseParams base = new BaseParams<>();
        base.setBizData(params);
        BaseNode node = ComputeNode.COMPUTE_PROMOTION;
        Object o ;
        try {
            o = computeFlow.promotionCompute(base, node);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        List<SpecialDishListDto> list = (List<SpecialDishListDto>) o;
        System.out.println( "list:" + GsonUtils.beanToJson(list));


    }


    @DisplayName("测试菜单价格")
    @org.junit.jupiter.api.Test
    void testMenu() {
        PromotionRequestDTO request = new PromotionRequestDTO();
        PromotionStoreDTO promotionStoreDTO = new PromotionStoreDTO();
        request.setIsAllPeople("true");
        request.setIsLogin("true");
        request.setChannel("1");
        request.setMajorVersion(1);
        BaseNode node = ComputeNode.COMPUTE_PROMOTION;
        request.setGradeId("0001");
        request.setOpenId("123");
        request.setMid("9799cf5e-1856-466e-a232-1ed3bc0cf299");
        request.setSid("97af7e68-766f-411c-bab4-6ac4372bfeab");

        try {
            //请求适配样例活动接口
            Map<String, Object> result = md5Service.executeNoAccess(request, node);
            if (Objects.isNull(result) || CollectionUtils.isEmpty(result)) {
                log.error("promotionCompute 调用适配样例活动接口失败，返回结果:{}", GsonUtils.beanToJson(result));
                return;
            }
            //组装返回结果
            promotionStoreDTO = md5Service.executeResult(result, PromotionStoreDTO.class);
            if (Objects.isNull(promotionStoreDTO)) {
                log.error("promotionCompute 调用适配样例活动接口失败，返回结果:{}", GsonUtils.beanToJson(result));
                throw new Exception("promotionCompute 调用适配样例活动接口失败");
            }

        } catch (Exception e) {
            log.error("promotionCompute 调用适配样例活动接口失败 ", e);
        }
    }

    @Resource
    private DemoToCompute demoToCompute;

    @DisplayName("测试购物车算价")
    @org.junit.jupiter.api.Test
    void testCart() {
        String a ="{\"storeCode\":\"MD00000011\",\"memberCode\":\"1199669\",\"productPriceDtoList\":[{\"skuCode\":\"SKUB6146100743ce\",\"price\":27.000000,\"crossedPrice\":27.000000,\"productCode\":\"CPJ43281657545a\",\"skuNum\":2,\"productType\":1,\"groupFlag\":\"SKUB6146100743ce\"}]}";
        CartPriceRequest request = GsonUtils.jsonToBean(a, CartPriceRequest.class);
        BaseNode node = ComputeNode.COMPUTE_CART_PRICE;
        List<PricePromotionDTO> pricePromotionListDTO = new ArrayList<>();
        BaseParams base = new BaseParams<>();
        base.setBizData(request);
        try {
            Object result = computeFlow.cartPriceCompute(base, node);
            System.out.println("result:"+ GsonUtils.beanToJson(result));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
//
//        try {
//            //请求适配样例活动接口
//            Map<String, Object> result = md5Service.executeNoAccess(request, node);
//            //组装返回结果
//            pricePromotionListDTO = md5Service.executeResultList(result, PricePromotionDTO.class);
//            log.info("promotionCompute 调用适配样例活动接口 pricePromotionListDTO", GsonUtils.beanToJson(pricePromotionListDTO));
//            if (CollectionUtils.isEmpty(pricePromotionListDTO)) {
//                log.error("promotionCompute 调用适配样例活动接口失败，返回结果:{}", GsonUtils.beanToJson(result));
//                throw new Exception("promotionCompute 调用适配样例活动接口失败");
//            }
//            PricePromotionDTO pricePromotionDTO = pricePromotionListDTO.get(0);
//            //返回购物车价格
//            CartPriceDto cartPriceDto = demoToCompute.cartConvert(pricePromotionDTO,request.getDishesList());
//            //转换返回结果
//            System.out.println("cartPriceDto:"+ GsonUtils.beanToJson(cartPriceDto));
//        } catch (Exception e) {
//            log.error("promotionCompute 调用适配样例活动接口失败 ", e);
//        }
    }


    @DisplayName("测试预下单")
    @org.junit.jupiter.api.Test
    void testPreOrderPrice() {

        BaseNode node = ComputeNode.COMPUTE_PRE_ORDER_PRICE;
        BaseParams param = new BaseParams();
        String a = "{\"storeCode\":\"MD00000011\",\"memberCode\":\"1199669\",\"openId\":\"oz5F661jKg24UxuY88Vf3-kmDwUk\",\"firstMatch\":true,\"paymentList\":[\"2\",\"1003\"],\"productPriceDtoList\":[{\"skuCode\":\"SKUB6146100743ce\",\"price\":27.000000,\"crossedPrice\":27.000000,\"salePrice\":27.000000,\"productCode\":\"CPJ43281657545a\",\"skuNum\":4,\"productType\":1,\"groupFlag\":\"SKUB6146100743ce\"}]}";
        PreOrderPriceRequest requestDTO = GsonUtils.jsonToBean(a, PreOrderPriceRequest.class);
//        PreOrderPriceRequest requestDTO = new PreOrderPriceRequest();
//        requestDTO.setPaymentList(Arrays.asList("2","1003"));
//        requestDTO.setFirstMatch( true);
//        requestDTO.setStoreCode("MD00000011");
//        requestDTO.setMemberCode("1199669");
//        ProductPriceDto product = new ProductPriceDto();
//        product.setProductCode("CPJ43281657545a");
//        product.setProductName("拿铁咖啡");
//        product.setSkuCode("SKUB6146100743ce");
//        product.setProductType(1);
//        product.setCrossedPrice(BigDecimal.valueOf(27));
//        product.setPrice(BigDecimal.valueOf(27));
//        product.setSalePrice(BigDecimal.valueOf(27));
//        product.setSkuNum(2);
//        product.setGroupFlag("879eed35-5685-486e-ba6f-30a3f7c19891");
//        requestDTO.setProductPriceDtoList(Arrays.asList(product));
        param.setBizData(requestDTO);
        try {
            Object result = computeFlow.preOrderPrice(param, node);
            System.out.println("result:"+ GsonUtils.beanToJson( result));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }



    }



}
