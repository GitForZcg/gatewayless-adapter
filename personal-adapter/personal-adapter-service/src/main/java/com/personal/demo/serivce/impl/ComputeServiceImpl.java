package com.personal.demo.serivce.impl;

import com.personal.demo.adapter.internal.compute.convert.DemoToCompute;
import com.personal.demo.adapter.internal.compute.convert.ComputeConver;
import com.personal.demo.adapter.internal.compute.convert.ComputeToDemo;
import com.personal.demo.adapter.internal.member.MemberFlow;
import com.personal.demo.adapter.internal.member.convert.MemberConvert;
import com.personal.demo.conf.RedisFactory;
import com.personal.demo.enu.RedisOrderKey;
import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.enu.internal.node.ComputeNode;
import com.personal.demo.enu.internal.node.MemberNode;
import com.personal.demo.pojo.dto.compute.*;
import com.personal.demo.pojo.dto.member.request.ListParamReqDto;
import com.personal.demo.pojo.dto.member.response.coupon.CouponListRespDto;
import com.personal.demo.pojo.dto.member.response.coupon.CouponRespDto;
import com.personal.demo.request.compute.CartPriceRequest;
import com.personal.demo.request.compute.PreOrderPriceRequest;
import com.personal.demo.request.compute.StorePromotionRequest;
import com.personal.demo.request.member.ListParam;
import com.personal.demo.request.member.OpenidParamParam;
import com.personal.demo.response.compute.CartPriceDto;
import com.personal.demo.response.compute.PreOrderResDto;
import com.personal.demo.response.compute.SpecialDishListDto;
import com.personal.demo.response.member.MemberInfoResultDto;
import com.personal.demo.serivce.AbstractComputeMD5Service;
import com.personal.demo.serivce.IComputeService;
import com.personal.demo.serivce.member.Sm2Invoker;
import com.personal.demo.serivce.member.impl.IMemberServiceImpl;
import com.common.base.exception.BizException;
import com.common.dto.response.Result;
import com.common.redis.sdk.RedissionClient;
import com.common.tools.GsonUtils;
import com.personal.member.MembersApi;
import com.personal.member.dto.member.MembersDetailsDto;
import com.personal.store.dto.NdShopDTO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author sulu
 * @date 2026年01月16日 4:49 PM
 */
@Service
@Slf4j
public class ComputeServiceImpl implements IComputeService {


    @Resource
    private MembersApi membersApi;

    @Resource
    private IMemberServiceImpl memberService;

    @Resource
    private AbstractComputeMD5Service md5Service;

    @Resource
    private ComputeToDemo computeToDemo;

    @Resource
    private DemoToCompute demoToCompute;

    @Resource
    private MemberFlow  memberFlow;

    @Resource
    private  Sm2Invoker sm2Invoker;

    private final RedissionClient commonClient = RedisFactory.getClient(RedisFactory.RedisClient.DEFAULT_1);

    @Override
    public Object promotionCompute(StorePromotionRequest params, BaseNode node) throws Exception {

        //查会员信息 1.根据 根据会员 code 查 openId，2.根据 会员 code 查会员等级编号
        String openId = params.getOpenId();
        String gradeId = params.getGradeId();
        PromotionRequestDTO request = new PromotionRequestDTO();
        PromotionStoreDTO promotionStoreDTO = new PromotionStoreDTO();
        request.setIsAllPeople("true");
        request.setIsLogin("true");
        request.setChannel("1");
        request.setMajorVersion(1);
        if (!Objects.isNull(openId)){
            request.setOpenId(openId);
        }else {
            try {
                log.info("promotionCompute 获取会员信息  入参:{}", params.getMemberCode());
                Result<MembersDetailsDto> membersDetailsDtoResult = membersApi.membersDetails(params.getMemberCode());
                log.info("promotionCompute 获取会员信息  结果:{}", GsonUtils.beanToJson(membersDetailsDtoResult.getData()));
                if (!membersDetailsDtoResult.isSuccess()) {
                    log.error("promotionCompute 获取会员信息失败，返回结果:{}", GsonUtils.beanToJson(membersDetailsDtoResult));
                    throw new BizException("promotionCompute 获取会员信息失败");
                }
                openId = membersDetailsDtoResult.getData().getOpenId();
                request.setOpenId(openId);
            } catch (Exception e) {
                log.error("promotionCompute 获取会员信息  rpc异常 ", e);
                throw new BizException("promotionCompute 获取会员信息失败");
            }
        }

        if (!Objects.isNull(gradeId)){
            request.setGradeId(gradeId);
        }else{
            try {
                //调用适配样例（改成 memberCode可以异步）
                BaseNode memberNode = MemberNode.MEMBER_GET_USER_BY_OPENID;
                OpenidParamParam reqDto = new OpenidParamParam();
                reqDto.setOpenid(openId);
                log.info("promotionCompute 获取适配样例会员信息  入参:{}", params.getMemberCode());
                MemberInfoResultDto memberInfoRespDto = memberService.getUserByOpenId(reqDto, memberNode);
                log.info("promotionCompute 获取适配样例会员信息  出参:{}", GsonUtils.beanToJson(memberInfoRespDto));
                if (Objects.isNull(memberInfoRespDto) || Objects.isNull(memberInfoRespDto.getGrade())) {
                    log.error("promotionCompute 获取适配样例会员信息，返回结果:{}", GsonUtils.beanToJson(memberInfoRespDto));
                    throw new BizException("promotionCompute 获取适配样例会员信息失败");
                }
                //组装请求适配样例活动接口参数
                request.setGradeId(memberInfoRespDto.getGrade().toString());
            } catch (Exception e) {
                log.error("promotionCompute.getUserByOpenId 调用适配样例查询用户信息失败 ", e);
                throw new BizException("promotionCompute.getUserByOpenId 调用适配样例查询用户信息失败");
            }
        }
        //调用适配样例

        //根据门店code查 sid
        String objValue = commonClient.getObjValue(RedisOrderKey.STORE_BINDING_STORE_KEY.getPrefix());
        List<NdShopDTO> storeCodes = GsonUtils.jsonToList(objValue, NdShopDTO.class);
        log.info("【promotionCompute】查询适配样例门店id缓存，stores：{}", GsonUtils.beanToJson(storeCodes));
        if (CollectionUtils.isEmpty(storeCodes)) {
            log.error("【promotionCompute】查询适配样例门店id失败，门店编号：{}", params.getStoreCode());
            throw new BizException("【promotionCompute】查询适配样例门店id失败");
        }
        storeCodes = storeCodes.stream().filter(Objects::nonNull).collect(Collectors.toList());
        storeCodes = storeCodes.stream().filter(t -> Objects.equals(t.getStoreCode(), params.getStoreCode())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(storeCodes)) {
            log.error("【promotionCompute】查询适配样例门店id失败，门店编号：{}", params.getStoreCode());
            return null;
        }
        request.setSid(storeCodes.get(0).getId());
        request.setMid(storeCodes.get(0).getMerchantId());
        try {
            //请求适配样例活动接口
            Map<String, Object> result = md5Service.executeNoAccess(request, node);
            //组装返回结果
            promotionStoreDTO = md5Service.executeResult(result, PromotionStoreDTO.class);
            log.info("【promotionCompute】查询适配样例门店菜单特价活动", GsonUtils.beanToJson(promotionStoreDTO));
            if (Objects.isNull(promotionStoreDTO)) {
                log.error("promotionCompute 调用适配样例活动接口失败，返回结果:{}", GsonUtils.beanToJson(result));
                throw new BizException("promotionCompute 调用适配样例活动接口失败");
            }
            //特价菜品列表
            List<RuleSpecialDishesDTO> specialDishesList = promotionStoreDTO.getSpecialDishesList();
            //校验返回结果
            if (CollectionUtils.isEmpty(specialDishesList)) {
                return null;
            }else{
                for (RuleSpecialDishesDTO ruleSpecialDishesDTO : specialDishesList) {
                   if (!ruleSpecialDishesDTO.resultCheck(ruleSpecialDishesDTO)){
                       log.error("promotionCompute】查询适配样例门店菜单特价活动，特价菜品列表校验失败，返回结果:{}", GsonUtils.beanToJson(specialDishesList));
                       return null;
                   }
                }
            }
            //转换返回结果
            List<SpecialDishListDto> specialDishList = ComputeConver.INSTANCE.convert(specialDishesList);
            return specialDishList;
        } catch (Exception e) {
            log.error("promotionCompute 调用适配样例活动接口失败 ", e);
            throw new BizException("promotionCompute 调用适配样例活动接口失败");
        }
    }


    @Override
    public Object cartPriceCompute(CartPriceRequest params, BaseNode node) throws Exception {
        CartPromotionRequestDTO request = new CartPromotionRequestDTO();
        String openId = null;
        try {
            log.info("promotionCompute 获取会员信息  入参:{}", params.getMemberCode());
            Result<MembersDetailsDto> membersDetailsDtoResult = membersApi.membersDetails(params.getMemberCode());
            log.info("promotionCompute 获取会员信息  结果:{}", GsonUtils.beanToJson(membersDetailsDtoResult.getData()));
            if (!membersDetailsDtoResult.isSuccess()) {
                log.error("promotionCompute 获取会员信息失败，返回结果:{}", GsonUtils.beanToJson(membersDetailsDtoResult));
                throw new BizException("promotionCompute 获取会员信息失败");
            }
            openId = membersDetailsDtoResult.getData().getOpenId();
            request.setOpenId(openId);
        } catch (Exception e) {
            log.error("promotionCompute 获取会员信息  rpc异常 ", e);
            throw new BizException("promotionCompute 获取会员信息失败");
        }
        List<PricePromotionDTO> pricePromotionListDTO = new ArrayList<>();
        //购物车接口不需要上报,转化参数
        request.setIsUseCoupon(0);
        try {
            NdShopDTO storeCodes = getStoreInfo(params.getStoreCode());
            request.setMid(storeCodes.getMerchantId());
            request.setBid(storeCodes.getBrandId());
            request.setSid(storeCodes.getId());
            request.setDemoId(storeCodes.getWshId().toString());
        } catch (Exception e) {
            log.error("preOrderPrice 获取门店信息  rpc异常 ", e);
            throw new BizException("preOrderPrice 获取门店信息失败");
        }
        computeToDemo.cartParamConvert(params, request);
        try {
            //请求适配样例活动接口
            Map<String, Object> result = md5Service.executeNoAccess(request, node);
            //组装返回结果
            pricePromotionListDTO = md5Service.executeResultList(result, PricePromotionDTO.class);
            log.info("购物车算价", GsonUtils.beanToJson(pricePromotionListDTO));
            if (CollectionUtils.isEmpty(pricePromotionListDTO)) {
                log.error("promotionCompute 调用适配样例活动接口失败，返回结果:{}", GsonUtils.beanToJson(result));
                throw new BizException("promotionCompute 调用适配样例活动接口失败");
            }
            PricePromotionDTO pricePromotionDTO = pricePromotionListDTO.get(0);
            //返回购物车价格
            CartPriceDto cartPriceDto = demoToCompute.cartConvert(pricePromotionDTO, request.getDishesList());
            //转换返回结果
            return cartPriceDto;
        } catch (Exception e) {
            log.error("promotionCompute 调用适配样例活动接口失败 ", e);
            throw new BizException("promotionCompute 调用适配样例活动接口失败");
        }
    }


    @Override
    public Object preOrderPrice(PreOrderPriceRequest params, BaseNode node) throws Exception {
        //todo 参数校验
        //1.资产上报 2.算价
        //资产上报查询会员基本信息，会员优惠券信息
        MemberAssetReportDTO request = new MemberAssetReportDTO();
        CartPromotionRequestDTO priceDTO = new CartPromotionRequestDTO();
        String openId = params.getMemberCode();
        priceDTO.setOpenId(openId);
        MemberAssetPosReportDTO posReportDTO = new MemberAssetPosReportDTO();
//        try {
//            MembersDetailsDto membersDetailsDtoResult = getMembersDetailsInfo(params.getMemberCode());
//            openId = membersDetailsDtoResult.getOpenId();
//            request.setOpenId(openId);
//            priceDTO.setOpenId(openId);
//        } catch (Exception e) {
//            log.error("preOrderPrice 获取会员信息  rpc异常 ", e);
//            throw new Exception("preOrderPrice 获取会员信息失败");
//        }
//        request.setOpenId("oz5F661jKg24UxuY88Vf3-kmDwUk");
//        priceDTO.setOpenId("oz5F661jKg24UxuY88Vf3-kmDwUk");

//        try{
//            OpenidParamParam openidParamParam = new OpenidParamParam();
//            openidParamParam.setOpenid(openId);
//            BaseNode  memberNode = MemberNode.MEMBER_GET_USER_BY_OPENID;
//            BaseParams baseParams = new BaseParams();
//            baseParams.setBizData(openidParamParam);
//            MemberInfoResultDto memberInfoResultDto = memberFlow.getUserByOpenId(baseParams, memberNode);
//            MemberInfoDTO memberInfoDTO = new MemberInfoDTO();
//            memberInfoDTO.setGrade(memberInfoResultDto.getGrade().toString());
//            memberInfoDTO.setBalance(memberInfoResultDto.getBalance());
//            request.setInfo(memberInfoDTO);
//        } catch (Exception e){
//            log.error("preOrderPrice 获取会员信息  rpc异常 ", e);
//            throw new Exception("preOrderPrice 获取会员信息失败");
//        }
        try {
            NdShopDTO storeCodes = getStoreInfo(params.getStoreCode());
            request.setMid(storeCodes.getMerchantId());
            priceDTO.setMid(storeCodes.getMerchantId());
            priceDTO.setBid(storeCodes.getBrandId());
            priceDTO.setSid(storeCodes.getId());
            priceDTO.setDemoId(storeCodes.getWshId().toString());
            posReportDTO.setBid(storeCodes.getBrandId());
            posReportDTO.setMid(storeCodes.getMerchantId());
        } catch (Exception e) {
            log.error("preOrderPrice 获取门店信息  rpc异常 ", e);
            throw new Exception("preOrderPrice 获取门店信息失败");
        }
        List<CouponRespDto>  couponList = new ArrayList<>();
        //如果是首次计算，需要资产上报。如果不是，就不需要
        try {
            couponList = getCouponList(params.getMemberCode());
        } catch (Exception e) {
            log.error("preOrderPrice 获取优惠券信息  rpc异常 ", e);

            //算价

            //资产上报结果

//            boolean assetResult = assetResult(request, params.getMemberCode());
//            if (!assetResult){
//                //资产上报失败怎么办？不能影响流程
//                log.error("资产上报失败");
//            }
            //把所有的优惠券传进去

        }
//        else {
//            CouponRespDto couponRespDto = new CouponRespDto();
//            CouponChooseRequest couponChooseRequest =params.getCouponChooseRequest();
//            couponRespDto.setCoupon_ids(Arrays.asList(couponChooseRequest.getCouponCode()));
//            couponRespDto.setTemplate_id(couponChooseRequest.getTemplateCode());
//            couponRespDto.setType(couponChooseRequest.getCouponType());
//
//        }
        computeToDemo.preOrderParamConvert(params, priceDTO);

        List<PricePromotionDTO> pricePromotionListDTO = new ArrayList<>();

        //资产上报
        assetPosResult(posReportDTO, params.getMemberCode());


        try {
            //请求适配样例活动接口
            Map<String, Object> result = md5Service.executeNoAccess(priceDTO, node);
            //组装返回结果
            pricePromotionListDTO = md5Service.executeResultList(result, PricePromotionDTO.class);
            log.info("promotionCompute 调用适配样例算价接口返回结果（未过滤）:{}", GsonUtils.beanToJson(pricePromotionListDTO));
            if (CollectionUtils.isEmpty(pricePromotionListDTO)) {
                log.error("promotionCompute 调用适配样例算价接口失败，返回结果:{}", GsonUtils.beanToJson(result));
                throw new Exception("promotionCompute 调用适配样例算价接口失败");
            }
            //转换返回结果
            PreOrderResDto preOrderResDto =demoToCompute.preOrderParamConvert(pricePromotionListDTO,couponList);
            return preOrderResDto;
        } catch (Exception e) {
            log.error("promotionCompute 调用适配样例活动接口失败 ", e);
            throw new BizException("promotionCompute 调用适配样例活动接口失败");
        }

    }


    public boolean assetResult(MemberAssetReportDTO membersDetailsDto, String memberCode){
        MemberAssetReportDTO request = membersDetailsDto;
        MemberInfoDTO info = Objects.isNull(membersDetailsDto.getInfo())? new MemberInfoDTO() : membersDetailsDto.getInfo();
        info.setUid(memberCode);
        info.setIsLogin("true");
        List<CouponRespDto> couponList = new ArrayList<>();
        try {
            couponList = getCouponList(memberCode);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if(!CollectionUtils.isEmpty(couponList)){
            List<CouponAssetDTO> coupons = new ArrayList<>();
            computeToDemo.couponParamConvert(coupons, couponList);
            info.setCoupons(coupons);
        }
        request.setInfo(info);
        try{
            //调用资产上报
            log.info("preOrderPrice 资产上报入参:{}",GsonUtils.beanToJson( request));
            BaseNode assetNode = ComputeNode.COMPUTE_MEMBER_ASSETS;
            Map<String, Object> assetResult = md5Service.executeNoAccess(request, assetNode);
            MemberAssetReportDTO memberAssetReportDTO = md5Service.executeResult(assetResult, MemberAssetReportDTO.class);
            //返回的是上报的内容，只需要知道 是否成功即可
        }catch (Exception e){
            log.error("ComputeServiceImpl 调用资产上报接口失败 ", e);
            return false;
        }
        return true;
    }

    public List<CouponRespDto> getCouponList(String memberCode) throws Exception {
        List<CouponRespDto> allCoupons = new ArrayList<>();
        ListParam listParam = new ListParam();
        listParam.setCno(memberCode);
        int page = 1;
        listParam.setPage(page);
        BaseNode memberNode = MemberNode.MEMBER_USER_UNUSE_COUPON_LIST;

        Object unusedCouponList = null;
        // 循环调用，直到返回空数据
        while (true) {
            try {
                // 1. 设置当前页号
                listParam.setPage(page);
                // 2. 调用接口获取当前页数据
                ListParamReqDto reqDto = MemberConvert.INSTANCE.listParamReqDtoConvert(listParam);
                CouponListRespDto invoke = sm2Invoker.invoke(reqDto, memberNode, CouponListRespDto.class);
                log.info("【查询会员未使用的券】返回结果：{}", GsonUtils.beanToJson(invoke));

                if (Objects.isNull( invoke) || CollectionUtils.isEmpty(invoke.getData())) {
                    break;
                }
                List<CouponRespDto> couponDetailDtoList = invoke.getData();
                // 3. 判断当前页是否有数据
                if (CollectionUtils.isEmpty(couponDetailDtoList)) {
                    // 无数据则终止循环
                    break;
                }
                // 4. 有数据则添加到总列表，页码+1
                allCoupons.addAll(couponDetailDtoList);
                page++;
            } catch (Exception e) {
                log.error("ComputeServiceImpl 调用资产上报接口失败-查用户优惠券失败（页码：{}） ", page, e);
                // 异常时终止循环，避免无限重试
                break;
            }
        }
        return allCoupons;
    }
    public MembersDetailsDto getMembersDetailsInfo(String memberCode) throws Exception {
        try {
            log.info("ComputeServiceImpl 获取会员信息  入参:{}", memberCode);
            Result<MembersDetailsDto> membersDetailsDtoResult = membersApi.membersDetails(memberCode);
            log.info("ComputeServiceImpl 获取会员信息  结果:{}", GsonUtils.beanToJson(membersDetailsDtoResult.getData()));
            if (!membersDetailsDtoResult.isSuccess() || Objects.isNull(membersDetailsDtoResult.getData())) {
                log.error("ComputeServiceImpl 获取会员信息失败，返回结果:{}", GsonUtils.beanToJson(membersDetailsDtoResult));
                throw new BizException("ComputeServiceImpl 获取会员信息失败");
            }
            return membersDetailsDtoResult.getData();
        } catch (Exception e) {
            log.error("ComputeServiceImpl 获取会员信息  rpc异常 ", e);
            throw new BizException("ComputeServiceImpl 获取会员信息失败");
        }
    }

    public NdShopDTO getStoreInfo(String storeCode) throws Exception {
        try {
            String objValue = commonClient.getObjValue(RedisOrderKey.STORE_BINDING_STORE_KEY.getPrefix());
            List<NdShopDTO> storeCodes = GsonUtils.jsonToList(objValue, NdShopDTO.class);
            log.info("【ComputeServiceImpl】查询适配样例门店id缓存，stores：{}", GsonUtils.beanToJson(storeCodes));
            if (CollectionUtils.isEmpty(storeCodes)) {
                log.error("【ComputeServiceImpl】查询适配样例门店id失败，门店编号：{}", storeCode);
                throw new BizException("【ComputeServiceImpl】查询适配样例门店id失败");
            }
            storeCodes = storeCodes.stream().filter(Objects::nonNull).collect(Collectors.toList());
            storeCodes = storeCodes.stream().filter(t -> Objects.equals(t.getStoreCode(), storeCode)).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(storeCodes)) {
                log.error("【ComputeServiceImpl】查询适配样例门店id失败，门店编号：{}", storeCode);
                throw new BizException("【ComputeServiceImpl】查询适配样例门店id失败");
            }
            return storeCodes.get(0);
        } catch (Exception e) {
            log.error("【ComputeServiceImpl】查询适配样例门店id失败，门店编号：{}", storeCode);
            throw new BizException("【ComputeServiceImpl】查询适配样例门店id失败");
        }
    }

    public boolean assetPosResult(MemberAssetPosReportDTO posReportDTO, String memberCode){
        MemberAssetPosReportDTO request = posReportDTO;
        request.setOpenId(memberCode);
        List<CouponRespDto> couponList = new ArrayList<>();
        try{
            //调用资产上报
            log.info("preOrderPrice 资产上报入参:{}",GsonUtils.beanToJson( request));
            BaseNode assetNode = ComputeNode.COMPUTE_POS_MEMBER_ASSETS;
            Map<String, Object> assetResult = md5Service.executeNoAccess(request, assetNode);
            log.info("preOrderPrice 资产上报结果:{}",GsonUtils.beanToJson( assetResult));
            Boolean result = md5Service.executeResultBoolean(assetResult);
            if (!result){
                log.error("资产上报失败,入参:{}",GsonUtils.beanToJson(request));
            }
            return result;
            //返回的是上报的内容，只需要知道 是否成功即可
        }catch (Exception e){
            log.error("ComputeServiceImpl 调用资产上报接口失败 ", e);
            return false;
        }
    }




}
