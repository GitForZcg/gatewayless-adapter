package com.personal.demo.adapter.internal.order.convert;

import com.personal.demo.adapter.internal.member.MemberFlow;
import com.personal.demo.conf.RedisFactory;
import com.personal.demo.dto.order.*;
import com.personal.demo.enu.RedisOrderKey;
import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.enu.internal.node.MemberNode;
import com.personal.demo.pojo.dto.order.DemoOrderInfoDTO;
import com.personal.demo.pojo.dto.order.RatioTranslateDTO;
import com.personal.demo.request.base.BaseParams;
import com.personal.demo.request.member.CouponDetailParam;
import com.personal.demo.response.member.coupon.CouponDetailResultDto;
import com.personal.demo.utils.SpecialCharacterDetector;
import com.common.base.exception.BizException;
import com.common.dto.constant.NumberConstant;
import com.common.dto.response.Result;
import com.common.redis.sdk.RedissionClient;
import com.common.tools.EncryptUtils;
import com.common.tools.GsonUtils;
import com.personal.demo.order.PersonalOrderSyncApi;
import com.personal.demo.order.PersonalOrderSyncLogApi;
import com.personal.demo.order.dto.PersonalOrderSyncDto;
import com.personal.demo.order.dto.PersonalOrderSyncLogDto;
import com.personal.member.MembersCouponApi;
import com.personal.member.dto.member.MembersDetailsDto;
import com.personal.member.request.coupon.MembersNoPageParam;
import com.personal.order.dto.order.*;
import com.personal.order.dto.refundOrder.PersonalOrderRefundInfoDto;
import com.personal.order.enu.order.OrderTypeEnum;
import com.personal.order.enu.order.PromotionTypeEnum;
import com.personal.pay.IPersonalPayApi;
import com.personal.pay.pojo.request.OrderCommitRequest;
import com.personal.product.IPersonalProductApi;
import com.personal.product.dto.system.ProdOrderDetailsDto;
import com.personal.product.request.system.ProdOrderDetailsParam;
import com.personal.store.api.IStoreApi;
import com.personal.store.dto.NdShopDTO;
import com.personal.store.dto.ShopBusinessHoursDto;
import com.personal.store.request.BusinessHoursRequest;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static com.common.dto.constant.NumberConstant.THREE;

/**
 * @author sulu
 * @date 2025年08月08日 4:54 PM
 */
@Slf4j
@Component
public class DemoConvert {

    @Resource
    private MembersCouponApi membersCouponApi;

    @Resource
    private IStoreApi iStoreApi;

    @Resource
    private PersonalOrderSyncLogApi personalOrderSyncLogApi;

    @Resource
    private PersonalOrderSyncApi personalOrderSyncApi;

    @Resource
    private IPersonalProductApi personalProductApi;

    @Resource
    private SpecialCharacterDetector specialCharacterDetector;

    @Resource
    private MemberFlow memberFlow;

    private final RedissionClient commonClient = RedisFactory.getClient(RedisFactory.RedisClient.DEFAULT_1);

    @Value("${pm-id.wx}")
    public Integer weChatPmId;

    @Value("${pm-id.djq}")
    public Integer walletPmId;

    @Value("${pm-id.cpq}")
    public Integer couponPmId;

    @Value("${pm-id.cz}")
    public Integer balancePmId;


    private static final String orderSource = "YZ";

    private static final Integer negativeOne = -1;
    private static final Integer One = 1;
    private static final Integer Zero = 0;
    private static final Integer Two = 2;
    private static final Integer Four = 4;
    private static final Integer Six = 6;
    private static final Double zeroDouble = 0.0;
    private static final Integer bilId = 1;

    private static final String deliveryName = "外送费";

    private static final String deliveryNo = "00003";
    private static final Integer deliveryDishesId = 3;

    private static final Double shishoupercent = 100.0;
    // private  final Integer couponPmId = cpq;
    // private  final Integer weChatPmId = wx;
    // private  final Integer walletPmId = djq;

    // private  final Integer balancePmId = cz;

    private static final String billState = "CJ01";
    private static final String TT = "TT";
    private static final Integer payPtId = 18;



    /**
     * bills:将personal对象转换为demo对象
     *
     * @return 转换后的DemoBillDTO对象
     */

    public DemoOrderInfoDTO billConvert(AppletOrderDetailDto orderDetail, MembersDetailsDto memberDetailsDt) {
        DemoOrderInfoDTO demoOrderDTO = new DemoOrderInfoDTO();
        DemoBillDTO billDTO = new DemoBillDTO();
        //查会员基本信息
        PersonalOrderInfoDto personalOrderInfoDto = orderDetail.getPersonalOrderInfoDto();
        memberConvert(billDTO, memberDetailsDt);
        storeConvert(billDTO, personalOrderInfoDto);
        Map<String, BigDecimal> walletCostPrice = new HashMap<>();
        DemoBalancePayDTO balancePayDTO = new DemoBalancePayDTO();
        List<PersonalOrderInfoPromotionDto> promotions = orderDetail.getPersonalOrderInfoPromotionDtos();
        billDTO.setBillid(UUID.randomUUID().toString());
        billDTO.setBillno(personalOrderInfoDto.getOrderNo());
        billDTO.setBill_num(personalOrderInfoDto.getOrderNo());
        if (Objects.isNull(personalOrderInfoDto.getCrossPromotionAmount())){
            personalOrderInfoDto.setCrossPromotionAmount(BigDecimal.ZERO);
        }
        if (Objects.isNull(personalOrderInfoDto.getComboSubtractAmount())){
            personalOrderInfoDto.setComboSubtractAmount(BigDecimal.ZERO);
        }
        if (Objects.isNull(personalOrderInfoDto.getOrderDiscountAmount())){
            personalOrderInfoDto.setOrderDiscountAmount(BigDecimal.ZERO);
        }
        // 处理订单总金额，适配样例的配送费是当做一个菜品，所以需要加上配送费的原价
        if (Objects.equals(personalOrderInfoDto.getOrderType(), NumberConstant.ONE)) {
            //自提
            billDTO.setTotalconsume(personalOrderInfoDto.getTotalAmount().subtract(personalOrderInfoDto.getCrossPromotionAmount()).subtract(personalOrderInfoDto.getComboSubtractAmount()).doubleValue());
            billDTO.setConsume(personalOrderInfoDto.getTotalAmount().doubleValue());
            billDTO.setBill_amount(personalOrderInfoDto.getTotalAmount().doubleValue());
            billDTO.setTatype(Zero);
            billDTO.setBusinesstype(One.toString());
            billDTO.setChannels(Two);
        } else {
            //配送
            BigDecimal total = personalOrderInfoDto.getTotalAmount().add(personalOrderInfoDto.getDeliveryOriginalAmount());
            billDTO.setTotalconsume(total.subtract(personalOrderInfoDto.getCrossPromotionAmount()).subtract(personalOrderInfoDto.getComboSubtractAmount()).doubleValue());
            billDTO.setConsume(total.doubleValue());
            billDTO.setBill_amount(total.doubleValue());
            billDTO.setTatype(NumberConstant.FOUR);
            billDTO.setBusinesstype(Two.toString());
            billDTO.setChannels(NumberConstant.FOUR);

        }
        //先给个初始值，后面还有处理,代金券处理一次，储值处理一次
        billDTO.setRealprice(personalOrderInfoDto.getPayableAmount().doubleValue());
        billDTO.setRemark(specialCharacterDetector.washText(personalOrderInfoDto.getOrderComment()));
        if (Objects.isNull(personalOrderInfoDto.getPayTime())){
            billDTO.setPaytime(timeChange(personalOrderInfoDto.getCreatedTime().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime()));
        }else {
            billDTO.setPaytime(timeChange(personalOrderInfoDto.getPayTime().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime()));
        }
        billDTO.setSource(NumberConstant.TWO);
        billDTO.setSpecialoffer(zeroDouble);
        billDTO.setNotype(One);
        billDTO.setRbillno(personalOrderInfoDto.getPickupCode());
        billDTO.setIsmemberbill(One);
        billDTO.setIswxorder(One);
        billDTO.setChoosepayment(One);
        billDTO.setPmtype(negativeOne);
        billDTO.setOrder_source(orderSource);
        billDTO.setServiceflag(Zero);
        billDTO.setService(zeroDouble);
        billDTO.setTeaflag(Zero);
        billDTO.setTea(zeroDouble);
        billDTO.setTeaamount(zeroDouble);
        billDTO.setBlid(bilId);
        billDTO.setChangeamount(zeroDouble);
        billDTO.setFreeAmount(zeroDouble);
        billDTO.setPayflag(One);
        billDTO.setVouchernoconsume(zeroDouble);
        billDTO.setNoconsumerealprice(zeroDouble);
        billDTO.setNoconsumecdid(330);
        billDTO.setBill_state("");
        billDTO.setPayptid(payPtId);
        billDTO.setOpenptid(One);
        billDTO.setExpandsale(personalOrderInfoDto.getSkuCouponAmount().add(personalOrderInfoDto.getWalletAmount()).doubleValue());
        billDTO.setDatetime(timeChange(personalOrderInfoDto.getCreatedTime().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()));
        //需要分析才能给出的数据
//        if (personalOrderInfoDto.getCrossPromotionAmount().compareTo(BigDecimal.ZERO) > 0) {
//            //有优惠价
//            billDTO.setSpecialoffer(personalOrderInfoDto.getCrossPromotionAmount().doubleValue());
//            billDTO.setSpecialoffercount(One);
//        }
        if (personalOrderInfoDto.getOrderCouponAmount().compareTo(BigDecimal.ZERO) > 0) {
            //实收要计入钱包的成本价
            if (CollectionUtils.isEmpty(promotions)) {
                log.error("【推送订单到适配样例】订单{}没有营销活动，但是代金券有优惠金额", personalOrderInfoDto.getOrderNo());
            }
            List<PersonalOrderInfoPromotionDto> walletPromotions = promotions.stream().filter(t -> Objects.equals(t.getType(), One) && Objects.equals(t.getPromotionType(), Two)).collect(Collectors.toList());
            log.info("【推送订单到适配样例】订单{}有代金券优惠金额,walletPromotions:{}", personalOrderInfoDto.getOrderNo(), GsonUtils.beanToJson(walletPromotions));
            if (CollectionUtils.isEmpty(walletPromotions)) {
                log.error("【推送订单到适配样例】订单{}没有营销活动，但是有钱包优惠金额", personalOrderInfoDto.getOrderNo());
            }
            walletCostPrice = walletCostPrice(personalOrderInfoDto.getMemberCode(), walletPromotions);
            if (!CollectionUtils.isEmpty(walletCostPrice)) {
                BigDecimal walletAmount = BigDecimal.ZERO;
                for (PersonalOrderInfoPromotionDto walletPromotion : walletPromotions) {
                    BigDecimal walletCost = walletCostPrice.get(walletPromotion.getOrderNo());
                    walletAmount = walletAmount.add(walletCost);
                }
                billDTO.setRealprice(personalOrderInfoDto.getPayableAmount().add(walletAmount).doubleValue());
            }
        }
        if (!Objects.isNull(personalOrderInfoDto.getBalanceAmount()) && personalOrderInfoDto.getBalanceAmount().compareTo(BigDecimal.ZERO) > 0){
            balancePayDTO = balanceConvert(personalOrderInfoDto);
            if (Objects.isNull(balancePayDTO) || Objects.isNull(balancePayDTO.getStored_sale_pay())){
                throw new RuntimeException("【推送订单到适配样例】会员储值支付金额有误");
            }
            BigDecimal realPrice = BigDecimal.valueOf(billDTO.getRealprice());
            BigDecimal storePay = BigDecimal.valueOf(balancePayDTO.getStored_sale_pay()).divide(BigDecimal.valueOf(100)).add(realPrice);
            billDTO.setRealprice(storePay.doubleValue());
        }
        List<DemoBillDetailedDTO> billdetaileds = new ArrayList<>();
        Map<String, String> prodCodeMap = getProductCode(orderDetail);
        //应收 ：原价之和-套餐/会员价减免金额-特价活动优惠金额
        BigDecimal totalPromotion = personalOrderInfoDto.getCrossPromotionAmount().add(personalOrderInfoDto.getComboSubtractAmount()).add(personalOrderInfoDto.getSkuDiscountAmount());
        billDTO.setDiscountamount(totalPromotion.doubleValue());
        BigDecimal consume = BigDecimal.valueOf(billDTO.getConsume());
        billDTO.setTotalconsume(consume.subtract(totalPromotion).doubleValue());

        //菜品信息
        log.info("【推送订单到适配样例】开始转换菜品详情");

        //钱包已经分摊的价格
        BigDecimal wallet = BigDecimal.ZERO;
        // 分摊比例 = 券成本/券实际抵扣的金额
        BigDecimal walletRatio = BigDecimal.ZERO;
        BigDecimal walletPay = BigDecimal.ZERO;
        if ( !CollectionUtils.isEmpty(walletCostPrice)){
            walletPay = walletCostPrice.get(personalOrderInfoDto.getOrderNo());
            walletRatio = walletPay.divide(personalOrderInfoDto.getTotalAmount(), 2, RoundingMode.HALF_UP);
        }
        // 储值已经分摊的价格
        BigDecimal balance = BigDecimal.ZERO;
        // 分摊比例
        BigDecimal balanceRatio = BigDecimal.ZERO;
        BigDecimal balancePay = BigDecimal.ZERO;
        if (!Objects.isNull(balancePayDTO.getStored_sale_pay())) {
            balanceRatio = BigDecimal.valueOf(balancePayDTO.getStored_sale_pay()).divide(personalOrderInfoDto.getBalanceAmount().multiply(BigDecimal.valueOf(100)), 2, RoundingMode.HALF_UP);
            balancePay = BigDecimal.valueOf(balancePayDTO.getStored_sale_pay()).divide(BigDecimal.valueOf(100));
        }
        for (int i = 0; i < orderDetail.getOrderInfoItemDtoList().size(); i++) {
            PersonalOrderInfoItemDto itemDto = orderDetail.getOrderInfoItemDtoList().get(i);
            RatioTranslateDTO ratioTranslateDTO = new RatioTranslateDTO();
            if ( i== orderDetail.getOrderInfoItemDtoList().size()-1){
                ratioTranslateDTO.setLastItem(true);
            }
            ratioTranslateDTO.setProdCodeMap(prodCodeMap);
            ratioTranslateDTO.setOrderDetail(itemDto);
            ratioTranslateDTO.setBillDTO(billDTO);
            ratioTranslateDTO.setOrderDTO(orderDetail);
            ratioTranslateDTO.setWalletRatio(walletRatio);
            ratioTranslateDTO.setBalanceRatio(balanceRatio);
            ratioTranslateDTO.setWallet(wallet);
            ratioTranslateDTO.setBalance(balance);
            ratioTranslateDTO.setBalancePay(balancePay);
            ratioTranslateDTO.setWalletPay(walletPay);
            RatioTranslateDTO ratioResult = detailedConvertV2(ratioTranslateDTO);
            wallet = ratioResult.getWallet();
            balance = ratioResult.getBalance();
            billdetaileds.addAll(ratioResult.getBillDetailedDTOList());
        }
        log.info("【推送订单到适配样例】菜品详情转换完成");
        //如果是外卖单，需要配送费菜品明细
        log.info("【推送订单到适配样例】开始转换外送费");
        if (Objects.equals(personalOrderInfoDto.getOrderType(), NumberConstant.TWO)) {
            DemoBillDetailedDTO deliveryDto = deliConvert(personalOrderInfoDto, billDTO);
            billdetaileds.add(deliveryDto);
        }
        log.info("【推送订单到适配样例】外送费转换完成");

        log.info("【推送订单到适配样例】开始转换支付信息");
        //支付信息
        List<DemoBillPayDTO> billPays = payConvertV2(billDTO, orderDetail, walletCostPrice,balancePayDTO);
        demoOrderDTO.setBills(Collections.singletonList(billDTO));
        demoOrderDTO.setBilldetaileds(billdetaileds);
        demoOrderDTO.setBillpays(billPays);
        log.info("【推送订单到适配样例】支付信息转换完成");

        return demoOrderDTO;
    }


    public void storeConvert(DemoBillDTO billDTO, PersonalOrderInfoDto orderDetail) {
        //加上门店营业日
        BusinessHoursRequest businessHours = new BusinessHoursRequest();
        businessHours.setStoreCode(orderDetail.getStoreCode());
        LocalDateTime formattedTime = convert(orderDetail.getCreatedTime());
        businessHours.setPayTime(formattedTime);
        log.info("【推送订单到适配样例】开始查询门店营业日，门店编号：{}", orderDetail.getStoreCode());
        Result<ShopBusinessHoursDto> result = iStoreApi.getShopBusinessHours(businessHours);
        if (!result.isSuccess() || Objects.isNull(result.getData())) {
            log.error("【推送订单到适配样例】查询门店营业日失败，门店编号：{}", orderDetail.getStoreCode());
        }
        LocalDate date = result.getData().getBusinessHoursDay();
        billDTO.setPaydate(date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        //加上门店 id
        log.info("【推送订单到适配样例】开始查询门店映射 id，门店编号：{}", orderDetail.getStoreCode());
        String objValue = commonClient.getObjValue(RedisOrderKey.STORE_BINDING_STORE_KEY.getPrefix());
        List<NdShopDTO> storeCodes = GsonUtils.jsonToList(objValue, NdShopDTO.class);
        log.info("【推送订单到适配样例】查询适配样例门店id缓存，stores：{}", GsonUtils.beanToJson(storeCodes));
        if (CollectionUtils.isEmpty(storeCodes)) {
            log.error("【推送订单到适配样例】查询适配样例门店id失败，门店编号：{}", orderDetail.getStoreCode());
            throw new BizException("【推送订单到适配样例】查询适配样例门店id失败");
        }
        storeCodes = storeCodes.stream().filter(Objects::nonNull).collect(Collectors.toList());
        storeCodes = storeCodes.stream().filter(t -> Objects.equals(t.getStoreCode(), orderDetail.getStoreCode())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(storeCodes)) {
            log.error("【推送订单到适配样例】查询适配样例门店id失败，门店编号：{}", orderDetail.getStoreCode());
            throw new BizException("【推送订单到适配样例】查询适配样例门店id失败");
        }
        billDTO.setOgnid(storeCodes.get(0).getId());
    }

    public static LocalDateTime convert(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    public void memberConvert(DemoBillDTO billDTO, MembersDetailsDto memberDetailsDto) {
        log.info("【推送订单到适配样例】开始查询会员映射信息，会员信息：{}", GsonUtils.beanToJson(memberDetailsDto));
        //TODO 加上会员信息
        billDTO.setMemberno(memberDetailsDto.getMembersCode());
        //会员名称可能有特殊字符，有的话自动改成默认名称"小喏"
        billDTO.setMembername(specialCharacterDetector.washName(memberDetailsDto.getMembersName()));
        if (StringUtils.isNotBlank(memberDetailsDto.getPhoneNumber())){
            try {
                billDTO.setMemberphone(EncryptUtils.desDecrypt(memberDetailsDto.getPhoneNumber()));
            } catch (Exception e) {
                throw new BizException("推送订单到适配样例】用户手机号解密失败",e.getMessage());
            }
        }else {
            billDTO.setMemberphone(memberDetailsDto.getPhoneNumber());
        }
        billDTO.setMembertype(memberDetailsDto.getMembersLevel().toString());

    }

    @Resource
    private IPersonalPayApi  personalPayApi;
    public DemoBalancePayDTO balanceConvert(PersonalOrderInfoDto orderDetail){
        OrderCommitRequest orderCommitRequest = new OrderCommitRequest();
        orderCommitRequest.setOrderNo(orderDetail.getOrderNo());

        Result<Map<String, Object>> result = personalPayApi.queryCommit(orderCommitRequest);
        log.info("【推送订单到适配样例】查询储值支付信息成功，订单号:{},结果：{}", orderDetail.getOrderNo(), GsonUtils.beanToJson(result));
        DemoBalancePayDTO balancePayDTO = new DemoBalancePayDTO();
        if (!result.isSuccess() || Objects.isNull(result.getData())) {
            log.error("【推送订单到适配样例】查询储值支付信息失败，订单编号：{}", orderDetail.getOrderNo());
            throw new BizException("【推送订单到适配样例】查询储值支付信息失败");
        }
        Map<String, Object> data = result.getData();
        if (CollectionUtils.isEmpty(data)){
            log.error("【推送订单到适配样例】查询储值支付信息失败，订单编号：{},结果：{}", orderDetail.getOrderNo(), GsonUtils.beanToJson(result));
            throw new BizException("【推送订单到适配样例】查询储值支付信息失败");
        }
        @SuppressWarnings("unchecked")
        Map<String, Object> res = (Map<String, Object>) data.get("res");
        if (CollectionUtils.isEmpty(res)){
            log.error("【推送订单到适配样例】查询储值支付信息失败，订单编号：{},res为空，结果：{}", orderDetail.getOrderNo(), GsonUtils.beanToJson(result));
            throw new BizException("【推送订单到适配样例】查询储值支付信息失败");
        }
        balancePayDTO.setStored_pay(((Number) res.get("stored_pay")).intValue());
        balancePayDTO.setStored_sale_pay(((Number) res.get("stored_sale_pay")).intValue());
        balancePayDTO.setStored_give_pay(((Number) res.get("stored_give_pay")).intValue());
        balancePayDTO.setDeal_id((String) res.get("deal_id"));
        log.info("【推送订单到适配样例】查询储值支付信息成功，订单号:{},结果：{}", orderDetail.getOrderNo(), GsonUtils.beanToJson(balancePayDTO));
        return  balancePayDTO;
    }

    /**
     * 将订单明细转换为DemoBillDetailedDTO对象列表
     *
     * @param billDTO DemoBillDTO对象，包含账单信息
     * @param orderDetail PersonalOrderInfoItemDto对象，包含订单明细信息
     * @param orderDTO AppletOrderDetailDto对象，包含小程序订单信息
     * @param walletCostPrice Map对象，包含钱包成本价格信息
     * @param prodCodeMap Map对象，包含产品编码映射信息
     * @return DemoBillDetailedDTO对象列表
     */
    public List<DemoBillDetailedDTO> detailedConvert(DemoBillDTO billDTO, PersonalOrderInfoItemDto orderDetail, AppletOrderDetailDto orderDTO, Map<String, BigDecimal> walletCostPrice, Map<String, String> prodCodeMap) {
        List<DemoBillDetailedDTO> demoBillDetailList = new ArrayList<>();
        DemoBillDetailedDTO detailedDTO = new DemoBillDetailedDTO();
        List<PersonalOrderInfoPromotionDto> promotionDtos = orderDTO.getPersonalOrderInfoPromotionDtos();
        List<BigDecimal> walletCostList = new ArrayList<>();
        String billDetailedId = UUID.randomUUID().toString();
        Integer isTakeOut = Zero;
        if (Objects.equals(billDTO.getBusinesstype(), One.toString())) {
            //自提
        } else {
            //配送
            isTakeOut = One;
        }

        detailedDTO.setBillid(billDTO.getBillid());
        detailedDTO.setBillno(billDTO.getBillno());
        detailedDTO.setOgnid(billDTO.getOgnid());
        detailedDTO.setBilldetailedid(billDetailedId);
        detailedDTO.setFreebillamount(zeroDouble);
        detailedDTO.setDishesamount(orderDetail.getCrossedPrice().multiply(BigDecimal.valueOf(orderDetail.getSkuNum())).doubleValue());
        detailedDTO.setPricea(orderDetail.getCrossedPrice().doubleValue());
        detailedDTO.setReceivableprice(orderDetail.getCrossedPrice().multiply(BigDecimal.valueOf(orderDetail.getSkuNum())).doubleValue());
        detailedDTO.setPrice(orderDetail.getProdPayPrice().doubleValue());
        detailedDTO.setRealprice(orderDetail.getProdPayPrice().multiply(BigDecimal.valueOf(orderDetail.getSkuNum())).doubleValue());
        detailedDTO.setFreeafterprice(orderDetail.getProdPayPrice().multiply(BigDecimal.valueOf(orderDetail.getSkuNum())).doubleValue());
        detailedDTO.setCount(orderDetail.getSkuNum().doubleValue());
        detailedDTO.setCdid(Zero);
        detailedDTO.setDishesid(Zero);
        detailedDTO.setRpjiajiaamount(zeroDouble);
        if (Objects.equals(orderDetail.getProdPayPrice(), orderDetail.getCrossedPrice())) {
            //使用原价
            detailedDTO.setBenefitflag(Zero);
            detailedDTO.setBenefitamount(zeroDouble);
        } else if (Objects.equals(orderDetail.getProdPayPrice(), orderDetail.getPrice())) {
            //使用优惠价
            detailedDTO.setBenefitflag(Two);
            detailedDTO.setDandiscountflag(One);
            detailedDTO.setBenefitamount(orderDetail.getCrossedPrice().subtract(orderDetail.getProdPayPrice()).multiply(BigDecimal.valueOf(orderDetail.getSkuNum())).doubleValue());
        } else if (orderDetail.getProdPayPrice().compareTo(orderDetail.getCrossedPrice().subtract(orderDetail.getProdCouponPrice())) == 0) {
            //使用券 要去 promotion 表查，确认是优惠券还是钱包，要给出钱包的成本
            detailedDTO.setBenefitflag(Six);
            detailedDTO.setBenefitamount(orderDetail.getCrossedPrice().subtract(orderDetail.getProdPayPrice()).multiply(BigDecimal.valueOf(orderDetail.getSkuNum())).doubleValue());

        } else if (!Objects.equals(orderDetail.getProdPayPrice(), orderDetail.getCrossedPrice()) && orderDetail.getProdCouponPrice().compareTo(BigDecimal.ZERO) == 0 && !Objects.equals(orderDetail.getProdPayPrice(), orderDetail.getPrice()) ){
            //如果是钱包，需要给钱包的实付价增加成本
            detailedDTO.setBenefitflag(Six);
            List<PersonalOrderInfoPromotionDto> walletPromotions = promotionDtos.stream().filter(t -> Objects.equals(t.getOrderItemCode(), orderDetail.getOrderItemCode()) && Objects.equals(t.getPromotionType(), PromotionTypeEnum.WALLET.getCode())).collect(Collectors.toList());
            BigDecimal walletCost = new BigDecimal(0);
            if (!CollectionUtils.isEmpty(walletPromotions)) {
                // TODO 这里要拆分了
                for (PersonalOrderInfoPromotionDto walletPromotion : walletPromotions) {
                    walletCost = walletCostPrice.get(walletPromotion.getPromotionCode());
                    walletCostList.add(walletCost);
                }
                //如果只有一个，就是这个值了
                detailedDTO.setPrice(orderDetail.getProdPayPrice().add(walletCost).doubleValue());
                detailedDTO.setRealprice(orderDetail.getProdPayPrice().add(walletCost).doubleValue());
                detailedDTO.setFreeafterprice(orderDetail.getProdPayPrice().add(walletCost).doubleValue());
            }
            detailedDTO.setBenefitamount(orderDetail.getCrossedPrice().subtract(orderDetail.getProdPayPrice().add(walletCost)).doubleValue());
        }else {
            log.error("【推送订单到适配样例】菜品价格异常，菜品原价：{}，菜品优惠价：{},菜品支付价:{}", orderDetail.getCrossedPrice(), orderDetail.getPrice(), orderDetail.getProdPayPrice());
            throw new BizException("【推送订单到适配样例】菜品价格异常");
        }
        detailedDTO.setIsnotakeaway(isTakeOut);
        detailedDTO.setSpecialOffer(Zero);
        detailedDTO.setWeightprice(zeroDouble);
        detailedDTO.setWeightpricea(zeroDouble);
        detailedDTO.setWeightpricediscount(zeroDouble);
        detailedDTO.setOriginalprice(zeroDouble);
        detailedDTO.setDishesname(orderDetail.getSkuName());
        detailedDTO.setWholenote("");
        detailedDTO.setDnid(Zero);
        detailedDTO.setPaydate(billDTO.getPaydate());
        if (Objects.equals(orderDetail.getSkuType(), One)) {
            //单品
            detailedDTO.setPackageflag(Zero);
            detailedDTO.setPackagezfflag(Zero);
            //需要根据 sku 查询产品 code
            String productCode = prodCodeMap.get(orderDetail.getSkuCode() + "_" + orderDetail.getSkuVersion());
            if (StringUtils.isBlank(productCode)) {
                log.error("【推送订单到适配样例】菜品产品编码为空，菜品名称：{}", orderDetail.getSkuName());
                throw new BizException("【推送订单到适配样例】菜品产品编码为空");
            }
            detailedDTO.setDishesno(productCode);
            //如果单个品下了多个，钱包成本需要拆分
            if (!CollectionUtils.isEmpty(walletCostList) && walletCostList.size() > 1) {
                for (int i = 0; i < walletCostList.size(); i++) {
                    BigDecimal cost = walletCostList.get(i);
                    try {
                        DemoBillDetailedDTO detailed = (DemoBillDetailedDTO)detailedDTO.clone();
                        BigDecimal realPrice = orderDetail.getProdPayPrice().add(cost);
                        detailed.setCount(One.doubleValue());
                        detailed.setPrice(realPrice.doubleValue());
                        detailed.setRealprice(realPrice.doubleValue());
                        detailed.setFreeafterprice(realPrice.doubleValue());
                        detailed.setBenefitamount(orderDetail.getCrossedPrice().subtract(realPrice).doubleValue());
                        detailed.setDishesamount(orderDetail.getCrossedPrice().doubleValue());
                        demoBillDetailList.add(detailed);
                    } catch (CloneNotSupportedException e) {
                        throw new BizException("【推送订单到适配样例】咖啡钱包数据深拷贝失败:{}",e.getMessage());
                    }
                }
            }else {
                demoBillDetailList.add(detailedDTO);
            }



        } else {
            //套餐
            detailedDTO.setPackageflag(One);
            detailedDTO.setPackageitemid(billDetailedId);
            demoBillDetailList.add(detailedDTO);
            detailedDTO.setDishesno(orderDetail.getSkuCode());
            detailedDTO.setPackagezfflag(Zero);
            //将套餐的子菜品拿出来
            List<PersonalOrderInfoItemDetailDto> personalOrderInfoItemDetailDtoList = orderDTO.getPersonalOrderInfoItemDetailDtoList();
            personalOrderInfoItemDetailDtoList = personalOrderInfoItemDetailDtoList.stream().filter(item -> Objects.equals(orderDetail.getOrderItemCode(), item.getOrderItemCode())).collect(Collectors.toList());
            Integer finalIsTakeOut = isTakeOut;
            personalOrderInfoItemDetailDtoList.forEach(item -> {
                //套餐明细，其实相当于售卖价优惠
                DemoBillDetailedDTO detailed = new DemoBillDetailedDTO();
                detailed.setDishesno(prodCodeMap.get(item.getSkuCode() + "_" + item.getSkuVersion()));
                detailed.setBillid(billDTO.getBillid());
                detailed.setBillno(billDTO.getBillno());
                detailed.setPackageitemid(billDetailedId);
                detailed.setIsnotakeaway(finalIsTakeOut);
                detailed.setPaydate(billDTO.getPaydate());
                detailed.setOgnid(billDTO.getOgnid());
                detailedConvert(detailed, item ,orderDetail.getSkuNum());
                String productCode = prodCodeMap.get(item.getSkuCode() + "_" + item.getSkuVersion());
                detailed.setDishesno(productCode);
                demoBillDetailList.add(detailed);
            });
        }
        //这里不能再有逻辑处理了，都在上面处理完
        return demoBillDetailList;
    }

    /**
     * 套餐子品转换
     *
     * @param detailedDTO
     * @param orderDetail
     */
    public void detailedConvert(DemoBillDetailedDTO detailedDTO, PersonalOrderInfoItemDetailDto orderDetail , Integer comboNum) {
        BigDecimal num = BigDecimal.valueOf(orderDetail.getSkuNum()).multiply(BigDecimal.valueOf(comboNum));
        detailedDTO.setBilldetailedid(UUID.randomUUID().toString());
        detailedDTO.setBenefitflag(Zero);
        detailedDTO.setBenefitamount(orderDetail.getCrossedPrice().subtract(orderDetail.getProdPayPrice()).multiply(num).doubleValue());
        detailedDTO.setDandiscountflag(One);
        detailedDTO.setFreebillamount(zeroDouble);
        detailedDTO.setDishesamount(orderDetail.getCrossedPrice().multiply(num).doubleValue());
        detailedDTO.setPricea(orderDetail.getCrossedPrice().doubleValue());
        detailedDTO.setReceivableprice(orderDetail.getCrossedPrice().multiply(num).doubleValue());
        detailedDTO.setPrice(orderDetail.getProdPayPrice().doubleValue());
        detailedDTO.setRealprice(orderDetail.getProdPayPrice().multiply(num).doubleValue());
        detailedDTO.setFreeafterprice(orderDetail.getProdPayPrice().multiply(num).doubleValue());
        detailedDTO.setPackdetailprice(zeroDouble);
        detailedDTO.setCount(num.doubleValue());
        detailedDTO.setSpecialOffer(Zero);
        detailedDTO.setWeightprice(zeroDouble);
        detailedDTO.setWeightpricea(zeroDouble);
        detailedDTO.setWeightpricediscount(zeroDouble);
        detailedDTO.setOriginalprice(zeroDouble);
        detailedDTO.setDishesname(orderDetail.getSkuName());
        detailedDTO.setPackageflag(Zero);
        detailedDTO.setPackagezfflag(One);
        detailedDTO.setWholenote("");
        detailedDTO.setCdid(Zero);
        detailedDTO.setDishesid(Zero);
        detailedDTO.setDnid(Zero);
        detailedDTO.setRpjiajiaamount(zeroDouble);
    }

    /**
     * 配送费转换
     *
     * @param orderDetail
     * @param billDTO
     * @return
     */
    public DemoBillDetailedDTO deliConvert(PersonalOrderInfoDto orderDetail, DemoBillDTO billDTO) {
        DemoBillDetailedDTO detailedDTO = new DemoBillDetailedDTO();
        detailedDTO.setBillid(billDTO.getBillid());
        detailedDTO.setBillno(billDTO.getBillno());
        detailedDTO.setOgnid(billDTO.getOgnid());
        detailedDTO.setBilldetailedid(UUID.randomUUID().toString());
        detailedDTO.setOgnid(billDTO.getOgnid());
        detailedDTO.setDishesid(deliveryDishesId);
        detailedDTO.setBenefitflag(Zero);
        detailedDTO.setBenefitamount(orderDetail.getDeliveryOriginalAmount().subtract(orderDetail.getDeliveryAmount()).doubleValue());
        detailedDTO.setDandiscountflag(One);
        detailedDTO.setFreebillamount(orderDetail.getDeliveryOriginalAmount().subtract(orderDetail.getDeliveryAmount()).doubleValue());
        detailedDTO.setDishesamount(orderDetail.getDeliveryOriginalAmount().doubleValue());
        detailedDTO.setPricea(orderDetail.getDeliveryOriginalAmount().doubleValue());
        detailedDTO.setReceivableprice(orderDetail.getDeliveryOriginalAmount().doubleValue());
        detailedDTO.setPrice(orderDetail.getDeliveryAmount().doubleValue());
        detailedDTO.setFreeafterprice(orderDetail.getDeliveryAmount().doubleValue());
        detailedDTO.setRealprice(orderDetail.getDeliveryAmount().doubleValue());
        detailedDTO.setPackdetailprice(zeroDouble);
        detailedDTO.setCount(One.doubleValue());
        detailedDTO.setSpecialOffer(Zero);
        detailedDTO.setWeightprice(zeroDouble);
        detailedDTO.setWeightpricea(zeroDouble);
        detailedDTO.setWeightpricediscount(zeroDouble);
        detailedDTO.setOriginalprice(zeroDouble);
        detailedDTO.setDishesname(deliveryName);
        detailedDTO.setDishesno(deliveryNo);
        detailedDTO.setPackageflag(Zero);
        detailedDTO.setPackagezfflag(Zero);
        detailedDTO.setIsnotakeaway(One);
        detailedDTO.setIsnotakeaway(One);
//        detailedDTO.setPackageitemid("");
        detailedDTO.setRcid(Four);
        detailedDTO.setDnid(Zero);
        detailedDTO.setPaydate(billDTO.getPaydate());
        detailedDTO.setWholenote("");
        detailedDTO.setCdid(Zero);
        detailedDTO.setDishesid(Zero);
        detailedDTO.setRpjiajiaamount(zeroDouble);
        return detailedDTO;
    }


    /**
     * 钱包成本价转换
     *
     * @param memberCode
     * @param list
     * @return
     */
    public Map<String, BigDecimal> walletCostPrice(String memberCode, List<PersonalOrderInfoPromotionDto> list) {
        if (CollectionUtils.isEmpty(list)) {
            log.error("【推送订单到适配样例】钱包数据为空");
        }
        Map<String, BigDecimal> map = new HashMap<>();
        for (PersonalOrderInfoPromotionDto personalOrderInfoPromotionDto : list) {
            MembersNoPageParam membersNoPageParam = new MembersNoPageParam();
            membersNoPageParam.setCouponType(Two);
            membersNoPageParam.setMembersCode(memberCode);
            membersNoPageParam.setCouponCode(personalOrderInfoPromotionDto.getPromotionCode());
            log.info("【推送订单到适配样例】查询会员钱包成本，入参：{}", GsonUtils.beanToJson(membersNoPageParam));
            //调适配样例接口查本券的成本
            BaseParams baseParams = new BaseParams();
            BaseNode node = MemberNode.MEMBER_USER_COUPON_DETAIL;
            CouponDetailParam couponDetailParam = new CouponDetailParam();
            couponDetailParam.setCouponCode(personalOrderInfoPromotionDto.getPromotionCode());
            couponDetailParam.setMembersCode(memberCode);
            baseParams.setBizData(couponDetailParam);
            CouponDetailResultDto couponDetailResultDto = null;
            try {
                log.info("【推送订单到适配样例】查询会员钱包成本，入参：{}", GsonUtils.beanToJson(baseParams));
                Object o = memberFlow.queryCouponData(baseParams,node);
                log.info("【推送订单到适配样例】查询会员钱包成本，返回：{}", GsonUtils.beanToJson(o));
                String jsonStr = "";
                if (o == null){
                    log.error("【推送订单到适配样例】查询会员钱包成本失败，入参：{}", GsonUtils.beanToJson(membersNoPageParam));
                }
                if (o instanceof String) {
                    jsonStr = (String) o;
                } else {
                    jsonStr = GsonUtils.beanToJson(o); // 统一用 Gson 序列化，保证格式正确
                }
                couponDetailResultDto = GsonUtils.jsonToBean(jsonStr, CouponDetailResultDto.class);
                if (Objects.isNull(couponDetailResultDto) || Objects.isNull(couponDetailResultDto.getSaleMoney())){
                    log.error("【推送订单到适配样例】查询会员钱包成本失败，入参：{}", GsonUtils.beanToJson(membersNoPageParam));
                    continue;
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            log.info("【推送订单到适配样例】查询会员优惠券成功，返回结果：{}", GsonUtils.beanToJson(couponDetailResultDto));
            BigDecimal costPrice = Objects.isNull(couponDetailResultDto.getSaleMoney()) ? BigDecimal.ZERO : BigDecimal.valueOf(couponDetailResultDto.getSaleMoney()).divide(new BigDecimal(100));
            map.put(personalOrderInfoPromotionDto.getOrderNo(), costPrice);
        }
        return map;
    }

    private List<DemoBillPayDTO> payConvert(DemoBillDTO billDTO, AppletOrderDetailDto orderDetail, Map<String, BigDecimal> walletCostPrice) {
        //利用订单的明细去计算，但也不能用所有的去计算
        //先处理实体，再进行产品明细处理
        List<DemoBillPayDTO> payList = new ArrayList<>();
        PersonalOrderInfoDto personalOrderInfoDto = orderDetail.getPersonalOrderInfoDto();
        List<PersonalOrderInfoPromotionDto> promotionList = orderDetail.getPersonalOrderInfoPromotionDtos();
        DemoBillPayDTO weChatPay = pmConvert(billDTO, personalOrderInfoDto, weChatPmId);
        DemoBillPayDTO couponPay = pmConvert(billDTO, personalOrderInfoDto, couponPmId);
        DemoBillPayDTO walletPay = pmConvert(billDTO, personalOrderInfoDto, walletPmId);

        for (PersonalOrderInfoItemDto itemDto : orderDetail.getOrderInfoItemDtoList()) {
            if (itemDto.getProdPayPrice().compareTo(BigDecimal.ZERO) > 0) {
                //微信
                BigDecimal baseAmount = BigDecimal.valueOf(
                        weChatPay.getAmount() != null ? weChatPay.getAmount() : 0.0
                );
                BigDecimal baseSourceAmount = BigDecimal.valueOf(
                        weChatPay.getSourceamount() != null ? weChatPay.getSourceamount() : 0.0
                );

                BigDecimal amount = baseAmount.add((itemDto.getProdPayPrice().multiply(BigDecimal.valueOf(itemDto.getSkuNum()))));
                BigDecimal sourceAmount = baseSourceAmount.add((itemDto.getProdPayPrice().multiply(BigDecimal.valueOf(itemDto.getSkuNum()))));
                weChatPay.setAmount(amount.doubleValue());
                weChatPay.setSourceamount(sourceAmount.doubleValue());
                weChatPay.setStoredpay(sourceAmount.multiply(new BigDecimal("100")).setScale(0, RoundingMode.HALF_UP).intValue());
            }
            if (itemDto.getProdCouponPrice().compareTo(BigDecimal.ZERO) > 0 ) {
                //用了优惠券或者是钱包,需要分开处理
                if (CollectionUtils.isEmpty(promotionList)) {
                    log.error("【推送订单到适配样例】订单{}没有营销活动，但是有优惠金额", personalOrderInfoDto.getOrderNo());
                    throw new BizException("【推送订单到适配样例】订单没有营销活动，但是有优惠金额");
                }
                List<PersonalOrderInfoPromotionDto> itemPromotions = promotionList.stream().filter(t -> Objects.equals(t.getOrderItemCode(), itemDto.getOrderItemCode())).collect(Collectors.toList());
                if (CollectionUtils.isEmpty(itemPromotions)) {
                    log.error("【推送订单到适配样例】订单{}没有营销活动，但是有优惠金额", personalOrderInfoDto.getOrderNo());
                    throw new BizException("【推送订单到适配样例】订单没有营销活动，但是有优惠金额");
                }
                //优惠券
                BigDecimal baseAmount = BigDecimal.valueOf(couponPay.getAmount() != null ? couponPay.getAmount() : 0.0);
                BigDecimal baseSourceAmount = BigDecimal.valueOf(
                        couponPay.getSourceamount() != null ? couponPay.getSourceamount() : 0.0
                );
                BigDecimal amount = baseAmount.add(itemDto.getProdCouponPrice().multiply(BigDecimal.valueOf(itemDto.getSkuNum())));
                BigDecimal sourceAmount = baseSourceAmount.add(itemDto.getProdCouponPrice().multiply(BigDecimal.valueOf(itemDto.getSkuNum())));
                couponPay.setAmount(amount.doubleValue());
                couponPay.setSourceamount(sourceAmount.doubleValue());
                couponPay.setStoredpay(Zero);
            }
            if (!Objects.equals(itemDto.getProdPayPrice(), itemDto.getCrossedPrice()) && itemDto.getProdCouponPrice().compareTo(BigDecimal.ZERO) == 0 && !Objects.equals(itemDto.getProdPayPrice(), itemDto.getPrice())) {
                List<PersonalOrderInfoPromotionDto> itemPromotions = promotionList.stream().filter(t -> Objects.equals(t.getOrderItemCode(), itemDto.getOrderItemCode())).toList();
                for (PersonalOrderInfoPromotionDto promotionDto : itemPromotions) {
                    //钱包
                    if (Objects.equals(promotionDto.getPromotionType(), THREE)) {
                        //获取钱包的成本价
                        //钱包的金额计算，虽然乘了 sku 数量，其实都是 1 个
                        BigDecimal walletCost = walletCostPrice.get(promotionDto.getPromotionCode());
                        BigDecimal itemCost = walletCost;
                        BigDecimal baseAmount = BigDecimal.valueOf(walletPay.getAmount() != null ? walletPay.getAmount() : 0.0);
                        BigDecimal baseSourceAmount = BigDecimal.valueOf(
                                walletPay.getSourceamount() != null ? walletPay.getSourceamount() : 0.0
                        );
                        BigDecimal amount = baseAmount.add(promotionDto.getPromotionPrice());
                        BigDecimal sourceAmount = baseSourceAmount.add(itemCost);
                        walletPay.setAmount(amount.doubleValue());
                        walletPay.setSourceamount(sourceAmount.doubleValue());
                        //这里需要乘以100，因为适配样例那边需要传入分
                        BigDecimal baseStoredPay = sourceAmount.multiply(new BigDecimal("100"))
                                .setScale(0, RoundingMode.HALF_UP);
                        walletPay.setStoredpay(baseStoredPay.intValue());
                    }
                }
            }

        }
        //如果是外卖单，加上配送费
        if (Objects.equals(personalOrderInfoDto.getOrderType(), OrderTypeEnum.DELIVERY_ORDER.getCode())){
            BigDecimal baseAmount = BigDecimal.valueOf(weChatPay.getAmount() != null ? weChatPay.getAmount() : 0.0);
            BigDecimal baseSourceAmount = BigDecimal.valueOf(
                    weChatPay.getSourceamount() != null ? weChatPay.getSourceamount() : 0.0
            );
            BigDecimal amount = baseAmount.add(personalOrderInfoDto.getDeliveryAmount());
            BigDecimal sourceAmount = baseSourceAmount.add(personalOrderInfoDto.getDeliveryAmount());
            weChatPay.setAmount(amount.doubleValue());
            weChatPay.setSourceamount(sourceAmount.doubleValue());
            weChatPay.setStoredpay(sourceAmount.multiply(new BigDecimal("100")).setScale(0, RoundingMode.HALF_UP).intValue());
        }

        if (!Objects.isNull(weChatPay.getStoredpay()) && weChatPay.getAmount() > 0) {
            payList.add(weChatPay);
        }
        if (!Objects.isNull(couponPay.getStoredpay()) && couponPay.getAmount() > 0) {
            payList.add(couponPay);
        }
        if (!Objects.isNull(walletPay.getStoredpay() ) && walletPay.getAmount() > 0) {
            payList.add(walletPay);
        }
        if (CollectionUtils.isEmpty(payList)) {
            log.error("【推送订单到适配样例】支付方式转换失败，订单编号：{}", personalOrderInfoDto.getOrderNo());
            throw new BizException("推送订单到适配样例】支付方式转换失败");
        }
        return payList;
    }

    /**
     * 支付方式统一转换
     *
     * @param billDTO
     * @param orderDetail
     * @param pmId
     * @return
     */
    private DemoBillPayDTO pmConvert(DemoBillDTO billDTO, PersonalOrderInfoDto orderDetail, Integer pmId) {
        DemoBillPayDTO result = new DemoBillPayDTO();
        result.setBillno(billDTO.getBillno());
        result.setBillid(billDTO.getBillid());
        result.setOgnid(billDTO.getOgnid());
        result.setPayplatform(Zero);
        result.setMembername(billDTO.getMembername());
        result.setMemberno(billDTO.getMemberno());
        result.setMembertype(billDTO.getMembertype());
        result.setRemark("");
        result.setShishoupercent(shishoupercent);
        result.setPaydate(billDTO.getPaydate());
        if (Objects.nonNull(orderDetail.getPayTime())){
            result.setPaytime(timeChange(orderDetail.getPayTime().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime()));
            result.setPaymenttime(timeChange(orderDetail.getPayTime().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime()));
        }else {
            result.setPaytime(timeChange(orderDetail.getCreatedTime().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime()));
            result.setPaymenttime(timeChange(orderDetail.getCreatedTime().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime()));
        }

        result.setPmtype(One);

        result.setPassages(THREE);
        result.setCouponAmount(zeroDouble);
        result.setStoredpay(Zero);
        result.setAmount(zeroDouble);
        result.setSourceamount(zeroDouble);
        result.setPmid(pmId);
        if (Objects.equals(pmId, weChatPmId)) {
            //微信
            result.setPaymode(One);
            result.setIssplit(Zero);
            result.setSerialno(orderDetail.getPayCode());

        } else if (Objects.equals(pmId, couponPmId)) {
            //菜品券
            result.setPaymode(Zero);
            result.setIssplit(Zero);
            result.setSerialno(orderDetail.getOrderNo());
            result.setStoredpay(Zero);
        } else if (Objects.equals(pmId, walletPmId)) {
            result.setPaymode(One);
            result.setIssplit(One);
            result.setSerialno(orderDetail.getOrderNo());
        } else if (Objects.equals(pmId, balancePmId)) {
            result.setPaymode(One);
            result.setIssplit(One);
            result.setStoredsalepay(Zero);
            result.setSerialno(orderDetail.getOrderNo());
        }
        return result;
    }


    public void offSetBillConvert(DemoOrderInfoDTO orderDTO, PersonalOrderRefundInfoDto data, PersonalOrderSyncDto syncDto) {
        DemoOrderDTO order = GsonUtils.jsonToBean(syncDto.getSyncOrderInfo(), DemoOrderDTO.class);
        DemoBillDTO reBill = order.getBills().get(0);
        String reBillId = reBill.getBillid();
        String reBillNum = reBill.getBill_num();
        //冲减单处理
        for (DemoBillDTO bill : orderDTO.getBills()) {
            offsetBillConvert(bill);
            bill.setCopy_bill_num(reBillNum);
        }
        for (DemoBillDetailedDTO detail : orderDTO.getBilldetaileds()) {
            offsetDetailConvert(detail);
        }
        for (DemoBillPayDTO pay : orderDTO.getBillpays()) {
            offsetPayConvert(pay);
            pay.setSerialno(data.getRefundPayCode());
        }
    }


    /**
     * 将账单信息转换为负数账单信息-冲减单
     *
     * @param billDTO 账单信息对象
     * @return 转换后的负数账单信息对象
     */
    public DemoBillDTO offsetBillConvert(DemoBillDTO billDTO) {
        billDTO.setBill_state(billState);
        billDTO.setBill_num(billDTO.getBill_num() + TT);
        billDTO.setConsume(-billDTO.getConsume());
        billDTO.setTotalconsume(-billDTO.getTotalconsume());
        billDTO.setRealprice(-billDTO.getRealprice());
        billDTO.setDiscountamount(-billDTO.getDiscountamount());
        billDTO.setBill_amount(-billDTO.getBill_amount());
        return billDTO;
    }


    /**
     * 将菜品明细转换为负数账单信息-冲减单
     *
     * @param billDetailed 账单信息对象
     * @return 转换后的负数账单信息对象
     */
    public DemoBillDetailedDTO offsetDetailConvert(DemoBillDetailedDTO billDetailed) {
        billDetailed.setCount(-billDetailed.getCount());
        billDetailed.setFreeafterprice(-billDetailed.getFreeafterprice());
        billDetailed.setRealprice(-billDetailed.getRealprice());
        billDetailed.setDishesamount(-billDetailed.getDishesamount());
        return billDetailed;
    }


    /**
     * 将支付信息转换为负数账单信息-冲减单
     *
     * @param billPayDTO 账单信息对象
     * @return 转换后的负数账单信息对象
     */
    public DemoBillPayDTO offsetPayConvert(DemoBillPayDTO billPayDTO) {
        billPayDTO.setAmount(-billPayDTO.getAmount());
        billPayDTO.setSourceamount(-billPayDTO.getSourceamount());
        billPayDTO.setStoredpay(-billPayDTO.getStoredpay());
        return billPayDTO;
    }


    public boolean syncLog(PersonalOrderSyncLogDto syncLog) {
        syncLog.setSyncType(One);
        syncLog.setPlatformType(One);
        syncLog.setSyncTime(new Date());
        Result<Void> insertResult = personalOrderSyncLogApi.saveSyncOrderLog(syncLog);
        if (!insertResult.isSuccess()) {
            log.error("【推送订单到适配样例】插入同步日志失败，入参：{}", syncLog);
            return false;
        }
        return true;
    }


    public boolean sync(String orderNo, DemoOrderInfoDTO orderDTO) {
        PersonalOrderSyncDto syncDto = new PersonalOrderSyncDto();
        syncDto.setSyncType(1);
        syncDto.setOrderNo(orderNo);
        syncDto.setSyncOrderInfo(GsonUtils.beanToJson(orderDTO));
        log.info("【推送订单到适配样例】新增订单数据表，orderDTO:{}", GsonUtils.beanToJson(syncDto));
        Result<Boolean> search = personalOrderSyncApi.searchSaveOrderSync(syncDto);
        if (!search.isSuccess()) {
            log.error("【推送订单到适配样例】新增订单数据表失败，入参：{}", syncDto);
            return false;
        }
        return true;
    }

    public Map<String, String> getProductCode(AppletOrderDetailDto orderDetail) {
        //先看下有没有单品
        Map<String, String> productCodeMap = new HashMap<>();
        List<ProdOrderDetailsParam> resultList = new ArrayList<>();
        List<PersonalOrderInfoItemDto> filteredItems = Optional.ofNullable(orderDetail.getOrderInfoItemDtoList())
                .orElse(Collections.emptyList())
                .stream()
                .filter(item -> Objects.equals(item.getSkuType(), One))
                .collect(Collectors.toList());

        filteredItems.forEach(itemDto -> {
            boolean isExisting = resultList.stream()
                    .anyMatch(param -> Objects.equals(param.getSkuCode(), itemDto.getSkuCode()) && Objects.equals(param.getSkuVersion(), itemDto.getSkuVersion()));

            if (!isExisting) {
                ProdOrderDetailsParam param = new ProdOrderDetailsParam();
                param.setSkuCode(itemDto.getSkuCode());
                param.setSkuVersion(itemDto.getSkuVersion());
                resultList.add(param);
            }
        });

        List<PersonalOrderInfoItemDetailDto> detailDtoList = orderDetail.getPersonalOrderInfoItemDetailDtoList();
        detailDtoList.forEach(detailDto -> {
            boolean isExisting = resultList.stream()
                    .anyMatch(param -> Objects.equals(param.getSkuCode(), detailDto.getSkuCode()) && Objects.equals(param.getSkuVersion(), detailDto.getSkuVersion()));

            if (!isExisting) {
                ProdOrderDetailsParam param = new ProdOrderDetailsParam();
                param.setSkuCode(detailDto.getSkuCode());
                param.setSkuVersion(detailDto.getSkuVersion());
                resultList.add(param);
            }
        });
        if (CollectionUtils.isEmpty(resultList)) {
            return productCodeMap;
        }
        // 调用接口获取产品 code
        Result<List<ProdOrderDetailsDto>> prodOrderDetails = personalProductApi.getProdOrderDetails(resultList);
        if (prodOrderDetails.isSuccess() && !CollectionUtils.isEmpty(prodOrderDetails.getData())) {
            List<ProdOrderDetailsDto> prodOrderDetailsDtos = prodOrderDetails.getData();
            for (ProdOrderDetailsDto dto : prodOrderDetailsDtos) {
                productCodeMap.put(dto.getSkuCode() + "_" + dto.getSkuVersion(), dto.getProdCode());
            }
        }
        log.info("【推送订单到适配样例】菜品产品编码查询结果：{}", productCodeMap);
//        productCodeMap.put("SKUB6204100714e7"+ "_" +4, "CPB62041007146d");
        return productCodeMap;
    }

    public String timeChange(LocalDateTime localDateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatted = localDateTime.format(formatter);
        return formatted;
    }

    /**
     * 将订单明细转换为DemoBillDetailedDTO对象列表
     *
     *
     * @return DemoBillDetailedDTO对象列表
     */
    public RatioTranslateDTO detailedConvertV2(RatioTranslateDTO translateDTO) {
        //入参整理

        DemoBillDTO billDTO = translateDTO.getBillDTO();
        PersonalOrderInfoItemDto orderDetail = translateDTO.getOrderDetail();
        AppletOrderDetailDto orderDTO = translateDTO.getOrderDTO();
        Map<String, String> prodCodeMap = translateDTO.getProdCodeMap();
        List<DemoBillDetailedDTO> billDetailedDTOList = translateDTO.getBillDetailedDTOList();
        BigDecimal wallet = translateDTO.getWallet();
        BigDecimal walletRatio = translateDTO.getWalletRatio();
        BigDecimal balance = translateDTO.getBalance();
        BigDecimal balanceRatio = translateDTO.getBalanceRatio();
        BigDecimal walletPay = translateDTO.getWalletPay();
        BigDecimal balancePay = translateDTO.getBalancePay();
        Boolean lastItem = translateDTO.getLastItem();
        log.info("【推送订单到适配样例】菜品详情入参整理，translateDTO:{}", GsonUtils.beanToJson(translateDTO));
        // 出参整理
        RatioTranslateDTO result = new RatioTranslateDTO();
        List<DemoBillDetailedDTO> demoBillDetailList = new ArrayList<>();
        DemoBillDetailedDTO detailedDTO = new DemoBillDetailedDTO();
        List<PersonalOrderInfoPromotionDto> promotionDtos = orderDTO.getPersonalOrderInfoPromotionDtos();
        PersonalOrderInfoDto personalOrderInfoDto = orderDTO.getPersonalOrderInfoDto();
        List<BigDecimal> walletCostList = new ArrayList<>();
        BigDecimal payPrice = orderDetail.getProdPayPrice().add(orderDetail.getBalancePrice());
        String billDetailedId = UUID.randomUUID().toString();
        Integer isTakeOut = Zero;
        if (Objects.equals(billDTO.getBusinesstype(), One.toString())) {
            //自提
        } else {
            //配送
            isTakeOut = One;
        }

        detailedDTO.setBillid(billDTO.getBillid());
        detailedDTO.setBillno(billDTO.getBillno());
        detailedDTO.setOgnid(billDTO.getOgnid());
        detailedDTO.setBilldetailedid(billDetailedId);
        detailedDTO.setFreebillamount(zeroDouble);
        detailedDTO.setDishesamount(orderDetail.getCrossedPrice().multiply(BigDecimal.valueOf(orderDetail.getSkuNum())).doubleValue());
        detailedDTO.setPricea(orderDetail.getCrossedPrice().doubleValue());
        detailedDTO.setReceivableprice(orderDetail.getCrossedPrice().multiply(BigDecimal.valueOf(orderDetail.getSkuNum())).doubleValue());
        detailedDTO.setPrice(payPrice.doubleValue());
        detailedDTO.setRealprice(payPrice.multiply(BigDecimal.valueOf(orderDetail.getSkuNum())).doubleValue());
        detailedDTO.setFreeafterprice(orderDetail.getPrice().multiply(BigDecimal.valueOf(orderDetail.getSkuNum())).doubleValue());
        detailedDTO.setCount(orderDetail.getSkuNum().doubleValue());
        detailedDTO.setCdid(Zero);
        detailedDTO.setDishesid(Zero);
        detailedDTO.setRpjiajiaamount(zeroDouble);
        //现金支付的钱/储值支付的钱
        BigDecimal realPrice = BigDecimal.ZERO;
        realPrice = (orderDetail.getProdPayPrice()).multiply(BigDecimal.valueOf(orderDetail.getSkuNum()));
        if (Objects.equals(orderDetail.getPrice(), orderDetail.getCrossedPrice())) {
            //使用原价
            detailedDTO.setBenefitflag(Zero);
            detailedDTO.setBenefitamount(zeroDouble);
        } else if (!Objects.isNull(orderDetail.getProdDiscountPrice()) && orderDetail.getProdDiscountPrice().compareTo(BigDecimal.ZERO) > 0) {
            //使用优惠价
            detailedDTO.setBenefitflag(Two);
            detailedDTO.setDandiscountflag(One);
            detailedDTO.setBenefitamount(orderDetail.getCrossedPrice().subtract(payPrice).multiply(BigDecimal.valueOf(orderDetail.getSkuNum())).doubleValue());
        } else if (!Objects.isNull(orderDetail.getProdCouponPrice()) && orderDetail.getProdCouponPrice().compareTo(BigDecimal.ZERO) > 0) {
            //使用优惠券
            detailedDTO.setBenefitflag(Six);
            detailedDTO.setBenefitamount(orderDetail.getCrossedPrice().subtract(payPrice).multiply(BigDecimal.valueOf(orderDetail.getSkuNum())).doubleValue());
        } else {
            //缺少一个优惠方式
            if (Objects.equals(orderDetail.getSkuType(), Two)){
                detailedDTO.setBenefitflag(Two);
                detailedDTO.setDandiscountflag(One);
                detailedDTO.setBenefitamount(orderDetail.getCrossedPrice().subtract(payPrice).multiply(BigDecimal.valueOf(orderDetail.getSkuNum())).doubleValue());
            }else{
                log.error("【推送订单到适配样例】菜品价格异常，菜品原价：{}，菜品优惠价：{},菜品支付价:{}", orderDetail.getCrossedPrice(), orderDetail.getPrice(), orderDetail.getProdPayPrice());
                throw new BizException("【推送订单到适配样例】菜品价格异常");
            }
        }

        detailedDTO.setIsnotakeaway(isTakeOut);
        detailedDTO.setSpecialOffer(Zero);
        detailedDTO.setWeightprice(zeroDouble);
        detailedDTO.setWeightpricea(zeroDouble);
        detailedDTO.setWeightpricediscount(zeroDouble);
        detailedDTO.setOriginalprice(zeroDouble);
        detailedDTO.setDishesname(orderDetail.getSkuName());
        detailedDTO.setWholenote("");
        detailedDTO.setDnid(Zero);
        detailedDTO.setPaydate(billDTO.getPaydate());
        if (Objects.equals(orderDetail.getSkuType(), One)) {
            //单品
            detailedDTO.setPackageflag(Zero);
            detailedDTO.setPackagezfflag(Zero);
            //需要根据 sku 查询产品 code
            String productCode = prodCodeMap.get(orderDetail.getSkuCode() + "_" + orderDetail.getSkuVersion());
            if (StringUtils.isBlank(productCode)) {
                log.error("【推送订单到适配样例】菜品产品编码为空，菜品名称：{}", orderDetail.getSkuName());
                throw new BizException("【推送订单到适配样例】菜品产品编码为空");
            }
            detailedDTO.setDishesno(productCode);
            //分摊
            DemoBillDetailedDTO dealCopy = new DemoBillDetailedDTO();
            if (walletPay.compareTo(BigDecimal.ZERO) > 0 || balancePay.compareTo(BigDecimal.ZERO) > 0){
                //需要分摊，但可能是代金券也可能是储值
                if (!lastItem) {
                    //有代金券,且不是最后一条
                    BigDecimal walletProduct = orderDetail.getPrice().multiply(walletRatio).multiply(BigDecimal.valueOf(orderDetail.getSkuNum())).setScale(2, RoundingMode.HALF_UP);
                    BigDecimal balanceProduct = orderDetail.getBalancePrice().multiply(balanceRatio).multiply(BigDecimal.valueOf(orderDetail.getSkuNum())).setScale(2, RoundingMode.HALF_UP);
                    realPrice = realPrice.add(walletProduct).add(balanceProduct);
                    wallet = wallet.add(walletProduct);
                    balance = balance.add(balanceProduct);
                    detailedDTO.setRealprice(realPrice.doubleValue());
                }else{
                    //有代金券,是最后一条
                    if (orderDetail.getSkuNum() >1){
                        //前面的都按照比例乘
                        BigDecimal walletProduct = orderDetail.getPrice().multiply(walletRatio).multiply(BigDecimal.valueOf(orderDetail.getSkuNum()-1)).setScale(2, RoundingMode.HALF_UP);
                        BigDecimal balanceProduct = orderDetail.getBalancePrice().multiply(balanceRatio).multiply(BigDecimal.valueOf(orderDetail.getSkuNum()-1)).setScale(2, RoundingMode.HALF_UP);
                        realPrice = realPrice.add(walletProduct).add(balanceProduct);
                        wallet = wallet.add(walletProduct);
                        balance = balance.add(balanceProduct);
                        detailedDTO.setRealprice(realPrice.doubleValue());
                        detailedDTO.setCount((double) (orderDetail.getSkuNum() - 1));
                        detailedDTO.setDishesamount(orderDetail.getCrossedPrice().multiply(BigDecimal.valueOf(orderDetail.getSkuNum() - 1)).doubleValue());
                        detailedDTO.setReceivableprice(orderDetail.getCrossedPrice().multiply(BigDecimal.valueOf(orderDetail.getSkuNum() - 1)).doubleValue());
                        detailedDTO.setFreeafterprice(orderDetail.getPrice().multiply(BigDecimal.valueOf(orderDetail.getSkuNum() - 1)).doubleValue());
                        detailedDTO.setBenefitamount((orderDetail.getCrossedPrice().subtract(realPrice)).multiply(BigDecimal.valueOf(orderDetail.getSkuNum() - 1)).doubleValue());

                        BigDecimal lastWalletPrice = walletPay.subtract(wallet);
                        BigDecimal lastBalancePrice = balancePay.subtract(balance);
                        wallet = wallet.add(lastWalletPrice);
                        balance = balance.add(lastBalancePrice);
                        //这里为什么不对比最后一条实收和前面的实收是否相同，因为涉及到储值可能也需要拆条，直接把最后一套拆掉比较保险，适配样例只做加法
                        //最后一条复制出来直接改价
                        BeanUtils.copyProperties(detailedDTO, dealCopy);
                        dealCopy.setCount(Double.valueOf(1));
                        dealCopy.setDishesamount(orderDetail.getCrossedPrice().doubleValue());
                        dealCopy.setReceivableprice(orderDetail.getCrossedPrice().doubleValue());
                        dealCopy.setFreeafterprice(orderDetail.getPrice().doubleValue());
                        dealCopy.setRealprice(lastBalancePrice.add(lastWalletPrice).add(orderDetail.getProdPayPrice()).doubleValue());
                    }else{
                        //最后一条数据是单个品，直接给值即可
                        BigDecimal walletProduct = walletPay.subtract(wallet);
                        BigDecimal balanceProduct = balancePay.subtract(balance);
                        realPrice = realPrice.add(walletProduct).add(balanceProduct);
                        wallet = wallet.add(walletProduct);
                        balance = balance.add(balanceProduct);
                        detailedDTO.setRealprice(realPrice.doubleValue());
                    }
                }
            }

            if (!Objects.isNull(dealCopy.getDishesno())){
                demoBillDetailList.add(dealCopy);
            }
            //如果单个品下了多个，钱包成本需要拆分
            demoBillDetailList.add(detailedDTO);

        } else {
            //套餐
            detailedDTO.setPackageflag(One);
            detailedDTO.setPackageitemid(billDetailedId);
            detailedDTO.setDishesno(orderDetail.getSkuCode());
            detailedDTO.setPackagezfflag(Zero);
            //分摊
            DemoBillDetailedDTO dealCopy = new DemoBillDetailedDTO();
            if ((!Objects.isNull(walletPay) && walletPay.compareTo(BigDecimal.ZERO) > 0) || (!Objects.isNull(balancePay) && balancePay.compareTo(BigDecimal.ZERO) > 0)){
                //需要分摊，但可能是代金券也可能是储值
                if (!lastItem) {
                    //有代金券or 储值,且不是最后一条
                    BigDecimal walletProduct = orderDetail.getPrice().multiply(walletRatio).multiply(BigDecimal.valueOf(orderDetail.getSkuNum())).setScale(2, RoundingMode.HALF_UP);
                    BigDecimal balanceProduct = orderDetail.getBalancePrice().multiply(balanceRatio).multiply(BigDecimal.valueOf(orderDetail.getSkuNum())).setScale(2, RoundingMode.HALF_UP);
                    realPrice = realPrice.add(walletProduct).add(balanceProduct);
                    wallet = wallet.add(walletProduct);
                    balance = balance.add(balanceProduct);
                    detailedDTO.setRealprice(realPrice.doubleValue());
                }else {
                    //有代金券,是最后一条
                    if (orderDetail.getSkuNum() >1){
                        //前面的都按照比例乘
                        BigDecimal walletProduct = orderDetail.getPrice().multiply(walletRatio).multiply(BigDecimal.valueOf(orderDetail.getSkuNum()-1)).setScale(2, RoundingMode.HALF_UP);
                        BigDecimal balanceProduct = orderDetail.getBalancePrice().multiply(balanceRatio).multiply(BigDecimal.valueOf(orderDetail.getSkuNum()-1)).setScale(2, RoundingMode.HALF_UP);
//                        realPrice = payPrice.multiply(BigDecimal.valueOf(orderDetail.getSkuNum()-1));
                        realPrice = realPrice.add(walletProduct).add(balanceProduct);
                        wallet = wallet.add(walletProduct);
                        balance = balance.add(balanceProduct);
                        detailedDTO.setRealprice(realPrice.doubleValue());
                        detailedDTO.setCount((double) (orderDetail.getSkuNum() - 1));
                        detailedDTO.setDishesamount(orderDetail.getCrossedPrice().multiply(BigDecimal.valueOf(orderDetail.getSkuNum() - 1)).doubleValue());
                        detailedDTO.setReceivableprice(orderDetail.getCrossedPrice().multiply(BigDecimal.valueOf(orderDetail.getSkuNum() - 1)).doubleValue());
                        detailedDTO.setFreeafterprice(orderDetail.getPrice().multiply(BigDecimal.valueOf(orderDetail.getSkuNum() - 1)).doubleValue());
                        detailedDTO.setBenefitamount((orderDetail.getCrossedPrice().subtract(realPrice)).multiply(BigDecimal.valueOf(orderDetail.getSkuNum() - 1)).doubleValue());
                        log.info("【推送订单到适配样例】多个套餐拆分完毕，拆分后为:{}", GsonUtils.beanToJson(detailedDTO));

                        BigDecimal lastWalletPrice = walletPay.subtract(wallet);
                        BigDecimal lastBalancePrice = balancePay.subtract(balance);
                        wallet = wallet.add(lastWalletPrice);
                        balance = balance.add(lastBalancePrice);
                        //这里为什么不对比最后一条实收和前面的实收是否相同，因为涉及到储值可能也需要拆条，直接把最后一套拆掉比较保险，适配样例只做加法
                        //最后一条复制出来直接改价
                        BeanUtils.copyProperties(detailedDTO, dealCopy);
                        dealCopy.setCount(1.0);
                        dealCopy.setDishesamount(orderDetail.getCrossedPrice().doubleValue());
                        dealCopy.setReceivableprice(orderDetail.getCrossedPrice().doubleValue());
                        dealCopy.setFreeafterprice(orderDetail.getPrice().doubleValue());
                        dealCopy.setRealprice(lastBalancePrice.add(lastWalletPrice).add(orderDetail.getProdPayPrice()).doubleValue());
                        log.info("【推送订单到适配样例】多个套餐拆分完毕，拆分后最后一条:{}", GsonUtils.beanToJson(dealCopy));
                    }else{
                        //最后一条数据是单个品，直接给值即可
                        BigDecimal walletProduct = wallet.subtract(walletPay);
                        BigDecimal balanceProduct = balance.subtract(balancePay);
                        realPrice = realPrice.add(walletProduct).add(balanceProduct);
                        wallet = wallet.add(walletProduct);
                        balance = balance.add(balanceProduct);
                        detailedDTO.setRealprice(realPrice.doubleValue());
                    }
                }
            }

            if (!Objects.isNull(dealCopy.getDishesno())){
                demoBillDetailList.add(dealCopy);
            }
            //套餐
            demoBillDetailList.add(detailedDTO);
            //将套餐的子菜品拿出来
            List<PersonalOrderInfoItemDetailDto> personalOrderInfoItemDetailDtoList = orderDTO.getPersonalOrderInfoItemDetailDtoList();
            personalOrderInfoItemDetailDtoList = personalOrderInfoItemDetailDtoList.stream().filter(item -> Objects.equals(orderDetail.getOrderItemCode(), item.getOrderItemCode())).collect(Collectors.toList());
            Integer finalIsTakeOut = isTakeOut;
            personalOrderInfoItemDetailDtoList.forEach(item -> {
                //套餐明细，其实相当于售卖价优惠
                DemoBillDetailedDTO detailed = new DemoBillDetailedDTO();
                detailed.setDishesno(prodCodeMap.get(item.getSkuCode() + "_" + item.getSkuVersion()));
                detailed.setBillid(billDTO.getBillid());
                detailed.setBillno(billDTO.getBillno());
                detailed.setPackageitemid(billDetailedId);
                detailed.setIsnotakeaway(finalIsTakeOut);
                detailed.setPaydate(billDTO.getPaydate());
                detailed.setOgnid(billDTO.getOgnid());
                detailedConvert(detailed, item ,orderDetail.getSkuNum());
                String productCode = prodCodeMap.get(item.getSkuCode() + "_" + item.getSkuVersion());
                detailed.setDishesno(productCode);
                demoBillDetailList.add(detailed);
            });
        }
        //这里不能再有逻辑处理了，都在上面处理完
        billDetailedDTOList.addAll(demoBillDetailList);
        result.setBalance(balance);
        result.setWallet(wallet);
        result.setBillDetailedDTOList(billDetailedDTOList);
        result.setBalancePay(balancePay);
        result.setWalletPay(walletPay);
        return result;
    }


    private List<DemoBillPayDTO> payConvertV2(DemoBillDTO billDTO, AppletOrderDetailDto orderDetail, Map<String, BigDecimal> walletCostPrice,DemoBalancePayDTO balancePayDTO) {
        //利用订单的明细去计算，但也不能用所有的去计算
        //先处理实体，再进行产品明细处理
        List<com.personal.demo.dto.order.DemoBillPayDTO> payList = new ArrayList<>();
        PersonalOrderInfoDto personalOrderInfoDto = orderDetail.getPersonalOrderInfoDto();
        List<PersonalOrderInfoPromotionDto> promotionList = orderDetail.getPersonalOrderInfoPromotionDtos();
        com.personal.demo.dto.order.DemoBillPayDTO weChatPay = pmConvert(billDTO, personalOrderInfoDto, weChatPmId);
        com.personal.demo.dto.order.DemoBillPayDTO couponPay = pmConvert(billDTO, personalOrderInfoDto, couponPmId);
        com.personal.demo.dto.order.DemoBillPayDTO walletPay = pmConvert(billDTO, personalOrderInfoDto, walletPmId);
        com.personal.demo.dto.order.DemoBillPayDTO balancePay = pmConvert(billDTO, personalOrderInfoDto, balancePmId);
        if (!Objects.isNull(personalOrderInfoDto.getPayableAmount()) && personalOrderInfoDto.getPayableAmount().compareTo(BigDecimal.ZERO) > 0){
            //本订单包含微信支付
            weChatPay.setAmount(personalOrderInfoDto.getPayableAmount().doubleValue());
            weChatPay.setSourceamount(personalOrderInfoDto.getPayableAmount().doubleValue());
            weChatPay.setStoredpay(personalOrderInfoDto.getPayableAmount().multiply(BigDecimal.valueOf(100)).setScale(0, RoundingMode.HALF_UP).intValue());

        }
        if (!Objects.isNull(personalOrderInfoDto.getSkuCouponAmount()) && personalOrderInfoDto.getSkuCouponAmount().compareTo(BigDecimal.ZERO) > 0){
            //优惠券
            couponPay.setAmount(personalOrderInfoDto.getSkuCouponAmount().doubleValue());
            couponPay.setSourceamount(personalOrderInfoDto.getSkuCouponAmount().doubleValue());
            couponPay.setStoredpay(Zero);
        }
        if(!Objects.isNull(personalOrderInfoDto.getOrderCouponAmount()) && personalOrderInfoDto.getOrderCouponAmount().compareTo(BigDecimal.ZERO) > 0){
            //代金券
            if (CollectionUtils.isEmpty(walletCostPrice)){
                log.error("【推送订单到适配样例】咖啡钱包数据为空:{}",orderDetail);
                throw new BizException("【推送订单到适配样例】咖啡钱包数据为空:{}",GsonUtils.beanToJson(walletCostPrice));
            }
            BigDecimal walletCost = walletCostPrice.values().stream()
                    .filter(Objects::nonNull) // 先过滤掉所有null值，防止NPE
                    .reduce(BigDecimal.ZERO, BigDecimal::add); //
            walletPay.setAmount(personalOrderInfoDto.getOrderCouponAmount().doubleValue());
            walletPay.setSourceamount(personalOrderInfoDto.getOrderCouponAmount().doubleValue());
            walletPay.setStoredpay(walletCost.multiply(BigDecimal.valueOf(100)).setScale(0, RoundingMode.HALF_UP).intValue());
        }
        if (!Objects.isNull(personalOrderInfoDto.getBalanceAmount()) && personalOrderInfoDto.getBalanceAmount().compareTo(BigDecimal.ZERO) > 0){
            //储值余额
            balancePay.setAmount(personalOrderInfoDto.getBalanceAmount().doubleValue());
            balancePay.setSourceamount(personalOrderInfoDto.getBalanceAmount().doubleValue());
            balancePay.setStoredpay(balancePayDTO.getStored_sale_pay());
            balancePay.setStoredsalepay(balancePayDTO.getStored_sale_pay());
        }

        //如果是外卖单，加上配送费
        if (Objects.equals(personalOrderInfoDto.getOrderType(), OrderTypeEnum.DELIVERY_ORDER.getCode())){
            BigDecimal baseAmount = BigDecimal.valueOf(weChatPay.getAmount() != null ? weChatPay.getAmount() : 0.0);
            BigDecimal baseSourceAmount = BigDecimal.valueOf(
                    weChatPay.getSourceamount() != null ? weChatPay.getSourceamount() : 0.0
            );
            BigDecimal amount = baseAmount.add(personalOrderInfoDto.getDeliveryAmount());
            BigDecimal sourceAmount = baseSourceAmount.add(personalOrderInfoDto.getDeliveryAmount());
            weChatPay.setAmount(amount.doubleValue());
            weChatPay.setSourceamount(sourceAmount.doubleValue());
            weChatPay.setStoredpay(sourceAmount.multiply(new BigDecimal("100")).setScale(0, RoundingMode.HALF_UP).intValue());
        }

        if (!Objects.isNull(weChatPay.getStoredpay()) && weChatPay.getAmount() > 0) {
            payList.add(weChatPay);
        }
        if (!Objects.isNull(couponPay.getStoredpay()) && couponPay.getAmount() > 0) {
            payList.add(couponPay);
        }
        if (!Objects.isNull(walletPay.getStoredpay() ) && walletPay.getAmount() > 0) {
            payList.add(walletPay);
        }
        if (!Objects.isNull(balancePay.getStoredpay()) && balancePay.getAmount() > 0) {
            payList.add(balancePay);
        }
        if (CollectionUtils.isEmpty(payList)) {
            log.error("【推送订单到适配样例】支付方式转换失败，订单编号：{}", personalOrderInfoDto.getOrderNo());
            throw new BizException("推送订单到适配样例】支付方式转换失败");
        }
        return payList;
    }

        public List<DemoBillDetailedDTO> detailedConvertV3(DemoBillDTO billDTO, PersonalOrderInfoItemDto orderDetail, AppletOrderDetailDto orderDTO, Map<String, BigDecimal> walletCostPrice, Map<String, String> prodCodeMap,DemoBalancePayDTO balancePayDTO) {
        //实付金额拿出来
        List<DemoBillDetailedDTO> demoBillDetailList = new ArrayList<>();
        DemoBillDetailedDTO detailedDTO = new DemoBillDetailedDTO();
        List<PersonalOrderInfoPromotionDto> promotionDtos = orderDTO.getPersonalOrderInfoPromotionDtos();
        PersonalOrderInfoDto personalOrderInfoDto = orderDTO.getPersonalOrderInfoDto();
        List<BigDecimal> walletCostList = new ArrayList<>();
        BigDecimal payPrice = orderDetail.getProdPayPrice().add(orderDetail.getBalancePrice());
        String billDetailedId = UUID.randomUUID().toString();
        Integer isTakeOut = Zero;
        if (Objects.equals(billDTO.getBusinesstype(), One.toString())) {
            //自提
        } else {
            //配送
            isTakeOut = One;
        }

        detailedDTO.setBillid(billDTO.getBillid());
        detailedDTO.setBillno(billDTO.getBillno());
        detailedDTO.setOgnid(billDTO.getOgnid());
        detailedDTO.setBilldetailedid(billDetailedId);
        detailedDTO.setFreebillamount(zeroDouble);
        // 计算理论总金额
        BigDecimal totalOriginDishesAmount = orderDetail.getCrossedPrice().multiply(BigDecimal.valueOf(orderDetail.getSkuNum()));
        BigDecimal totalPayPrice = payPrice.multiply(BigDecimal.valueOf(orderDetail.getSkuNum()));
        BigDecimal totalRealPrice = orderDetail.getProdPayPrice().multiply(BigDecimal.valueOf(orderDetail.getSkuNum()));
        BigDecimal totalFreeAfterPrice = orderDetail.getProdPayPrice().multiply(BigDecimal.valueOf(orderDetail.getSkuNum()));
        BigDecimal totalBenefitAmount = orderDetail.getCrossedPrice().subtract(payPrice).multiply(BigDecimal.valueOf(orderDetail.getSkuNum()));

        detailedDTO.setDishesamount(totalOriginDishesAmount.doubleValue());
        detailedDTO.setPricea(orderDetail.getCrossedPrice().doubleValue());
        detailedDTO.setReceivableprice(totalOriginDishesAmount.doubleValue());
        detailedDTO.setPrice(payPrice.doubleValue());
        detailedDTO.setRealprice(totalRealPrice.doubleValue());
        detailedDTO.setFreeafterprice(totalFreeAfterPrice.doubleValue());
        detailedDTO.setCount(orderDetail.getSkuNum().doubleValue());
        detailedDTO.setCdid(Zero);
        detailedDTO.setDishesid(Zero);
        detailedDTO.setRpjiajiaamount(zeroDouble);
        //现金支付的钱/储值支付的钱

        if (Objects.equals(orderDetail.getPrice(), orderDetail.getCrossedPrice())) {
            //使用原价
            detailedDTO.setBenefitflag(Zero);
            detailedDTO.setBenefitamount(zeroDouble);
        } else if (!Objects.isNull(orderDetail.getProdDiscountPrice()) && orderDetail.getProdDiscountPrice().compareTo(BigDecimal.ZERO) > 0) {
            //使用优惠价
            detailedDTO.setBenefitflag(Two);
            detailedDTO.setDandiscountflag(One);
            detailedDTO.setBenefitamount(totalBenefitAmount.doubleValue());
        } else if (!Objects.isNull(orderDetail.getProdCouponPrice()) && orderDetail.getProdCouponPrice().compareTo(BigDecimal.ZERO) > 0) {
            //使用券 要去 promotion 表查，确认是优惠券还是钱包，要给出钱包的成本
            detailedDTO.setBenefitflag(Six);
            detailedDTO.setBenefitamount(totalBenefitAmount.doubleValue());
        } else {
            //缺少一个优惠方式
            log.error("【推送订单到适配样例】菜品价格异常，菜品原价：{}，菜品优惠价：{},菜品支付价:{}", orderDetail.getCrossedPrice(), orderDetail.getPrice(), orderDetail.getProdPayPrice());
            throw new BizException("【推送订单到适配样例】菜品价格异常");
        }
        detailedDTO.setIsnotakeaway(isTakeOut);
        detailedDTO.setSpecialOffer(Zero);
        detailedDTO.setWeightprice(zeroDouble);
        detailedDTO.setWeightpricea(zeroDouble);
        detailedDTO.setWeightpricediscount(zeroDouble);
        detailedDTO.setOriginalprice(zeroDouble);
        detailedDTO.setDishesname(orderDetail.getSkuName());
        detailedDTO.setWholenote("");
        detailedDTO.setDnid(Zero);
        detailedDTO.setPaydate(billDTO.getPaydate());
        if (Objects.equals(orderDetail.getSkuType(), One)) {
            //单品
            detailedDTO.setPackageflag(Zero);
            detailedDTO.setPackagezfflag(Zero);
            //需要根据 sku 查询产品 code
            String productCode = prodCodeMap.get(orderDetail.getSkuCode() + "_" + orderDetail.getSkuVersion());
            if (StringUtils.isBlank(productCode)) {
                log.error("【推送订单到适配样例】菜品产品编码为空，菜品名称：{}", orderDetail.getSkuName());
                throw new BizException("【推送订单到适配样例】菜品产品编码为空");
            }
            detailedDTO.setDishesno(productCode);

            // 如果下单数量大于1，需要拆分每个数量，最后一个使用减法保证总金额一致
            int skuNum = orderDetail.getSkuNum();
            if (skuNum > 1) {
                BigDecimal accDishesAmount = BigDecimal.ZERO;
                BigDecimal accPayPrice = BigDecimal.ZERO;
                BigDecimal accRealPrice = BigDecimal.ZERO;
                BigDecimal accFreeAfterPrice = BigDecimal.ZERO;
                BigDecimal accBenefitAmount = BigDecimal.ZERO;

                // 前 n-1 个正常计算
                for (int i = 1; i < skuNum; i++) {
                    try {
                        DemoBillDetailedDTO detailed = (DemoBillDetailedDTO) detailedDTO.clone();
                        BigDecimal currentDishesAmount = orderDetail.getCrossedPrice();
                        BigDecimal currentPayPrice = payPrice;
                        BigDecimal currentRealPrice = orderDetail.getProdPayPrice();
                        BigDecimal currentFreeAfterPrice = orderDetail.getProdPayPrice();
                        BigDecimal currentBenefitAmount = orderDetail.getCrossedPrice().subtract(payPrice);

                        detailed.setBilldetailedid(UUID.randomUUID().toString());
                        detailed.setCount(One.doubleValue());
                        detailed.setDishesamount(currentDishesAmount.doubleValue());
                        detailed.setPrice(currentPayPrice.doubleValue());
                        detailed.setRealprice(currentRealPrice.doubleValue());
                        detailed.setFreeafterprice(currentFreeAfterPrice.doubleValue());
                        detailed.setBenefitamount(currentBenefitAmount.doubleValue());
                        detailed.setReceivableprice(currentDishesAmount.doubleValue());

                        demoBillDetailList.add(detailed);

                        // 累加金额用于最后计算
                        accDishesAmount = accDishesAmount.add(currentDishesAmount);
                        accPayPrice = accPayPrice.add(currentPayPrice);
                        accRealPrice = accRealPrice.add(currentRealPrice);
                        accFreeAfterPrice = accFreeAfterPrice.add(currentFreeAfterPrice);
                        accBenefitAmount = accBenefitAmount.add(currentBenefitAmount);
                    } catch (CloneNotSupportedException e) {
                        throw new BizException("【推送订单到适配样例】菜品分账深拷贝失败:{}", e.getMessage());
                    }
                }

                // 最后一个使用总金额减去前面累加的金额，保证总金额一致
                try {
                    DemoBillDetailedDTO lastDetailed = (DemoBillDetailedDTO) detailedDTO.clone();
                    lastDetailed.setBilldetailedid(UUID.randomUUID().toString());
                    lastDetailed.setCount(One.doubleValue());

                    // 剩余金额计算，保证总和与总金额一致
                    BigDecimal lastDishesAmount = totalOriginDishesAmount.subtract(accDishesAmount);
                    BigDecimal lastPayPrice = totalPayPrice.subtract(accPayPrice);
                    BigDecimal lastRealPrice = totalRealPrice.subtract(accRealPrice);
                    BigDecimal lastFreeAfterPrice = totalFreeAfterPrice.subtract(accFreeAfterPrice);
                    BigDecimal lastBenefitAmount = totalBenefitAmount.subtract(accBenefitAmount);

                    lastDetailed.setDishesamount(lastDishesAmount.doubleValue());
                    lastDetailed.setPrice(lastPayPrice.doubleValue());
                    lastDetailed.setRealprice(lastRealPrice.doubleValue());
                    lastDetailed.setFreeafterprice(lastFreeAfterPrice.doubleValue());
                    lastDetailed.setBenefitamount(lastBenefitAmount.doubleValue());
                    lastDetailed.setReceivableprice(lastDishesAmount.doubleValue());

                    demoBillDetailList.add(lastDetailed);
                } catch (CloneNotSupportedException e) {
                    throw new BizException("【推送订单到适配样例】最后菜品分账深拷贝失败:{}", e.getMessage());
                }
            } else {
                // 数量为1，直接添加即可
                demoBillDetailList.add(detailedDTO);
            }

        } else {
            //套餐
            detailedDTO.setPackageflag(One);
            detailedDTO.setPackageitemid(billDetailedId);
            demoBillDetailList.add(detailedDTO);
            detailedDTO.setDishesno(orderDetail.getSkuCode());
            detailedDTO.setPackagezfflag(Zero);
            //将套餐的子菜品拿出来
            List<PersonalOrderInfoItemDetailDto> personalOrderInfoItemDetailDtoList = orderDTO.getPersonalOrderInfoItemDetailDtoList();
            personalOrderInfoItemDetailDtoList = personalOrderInfoItemDetailDtoList.stream().filter(item -> Objects.equals(orderDetail.getOrderItemCode(), item.getOrderItemCode())).collect(Collectors.toList());

            // 对子菜品也处理精度问题
            if (!CollectionUtils.isEmpty(personalOrderInfoItemDetailDtoList)) {
                int itemSize = personalOrderInfoItemDetailDtoList.size();
                Integer finalIsTakeOut = isTakeOut;
                // 先累加前面所有子菜品的各个金额字段
                BigDecimal accSubDishesAmount = BigDecimal.ZERO;
                BigDecimal accSubBenefitAmount = BigDecimal.ZERO;
                BigDecimal accSubRealPrice = BigDecimal.ZERO;
                BigDecimal accSubFreeAfterPrice = BigDecimal.ZERO;

                // 计算子菜品总金额
                BigDecimal totalSubOriginDishesAmount = BigDecimal.ZERO;
                BigDecimal totalSubBenefitAmount = BigDecimal.ZERO;
                BigDecimal totalSubRealPrice = BigDecimal.ZERO;
                BigDecimal totalSubFreeAfterPrice = BigDecimal.ZERO;
                for (PersonalOrderInfoItemDetailDto item : personalOrderInfoItemDetailDtoList) {
                    BigDecimal num = BigDecimal.valueOf(item.getSkuNum()).multiply(BigDecimal.valueOf(orderDetail.getSkuNum()));
                    BigDecimal crossed = item.getCrossedPrice().multiply(num);
                    BigDecimal pay = item.getProdPayPrice().multiply(num);
                    BigDecimal benefit = item.getCrossedPrice().subtract(item.getProdPayPrice()).multiply(num);
                    totalSubOriginDishesAmount = totalSubOriginDishesAmount.add(crossed);
                    totalSubBenefitAmount = totalSubBenefitAmount.add(benefit);
                    totalSubRealPrice = totalSubRealPrice.add(pay);
                    totalSubFreeAfterPrice = totalSubFreeAfterPrice.add(pay);
                }

                // 处理前 n-1 个子菜品
                for (int i = 0; i < itemSize - 1; i++) {
                    PersonalOrderInfoItemDetailDto item = personalOrderInfoItemDetailDtoList.get(i);
                    DemoBillDetailedDTO detailed = new DemoBillDetailedDTO();
                    detailed.setDishesno(prodCodeMap.get(item.getSkuCode() + "_" + item.getSkuVersion()));
                    detailed.setBillid(billDTO.getBillid());
                    detailed.setBillno(billDTO.getBillno());
                    detailed.setPackageitemid(billDetailedId);
                    detailed.setIsnotakeaway(finalIsTakeOut);
                    detailed.setPaydate(billDTO.getPaydate());
                    detailed.setOgnid(billDTO.getOgnid());
                    detailedConvert(detailed, item, orderDetail.getSkuNum());
                    String productCode = prodCodeMap.get(item.getSkuCode() + "_" + item.getSkuVersion());
                    detailed.setDishesno(productCode);

                    // 累加金额
                    BigDecimal num = BigDecimal.valueOf(item.getSkuNum()).multiply(BigDecimal.valueOf(orderDetail.getSkuNum()));
                    accSubDishesAmount = accSubDishesAmount.add(BigDecimal.valueOf(detailed.getDishesamount()));
                    accSubBenefitAmount = accSubBenefitAmount.add(BigDecimal.valueOf(detailed.getBenefitamount()));
                    accSubRealPrice = accSubRealPrice.add(BigDecimal.valueOf(detailed.getRealprice()));
                    accSubFreeAfterPrice = accSubFreeAfterPrice.add(BigDecimal.valueOf(detailed.getFreeafterprice()));

                    demoBillDetailList.add(detailed);
                }

                // 处理最后一个子菜品，使用总金额减去前面累加的金额
                PersonalOrderInfoItemDetailDto lastItem = personalOrderInfoItemDetailDtoList.get(itemSize - 1);
                DemoBillDetailedDTO lastDetailed = new DemoBillDetailedDTO();
                lastDetailed.setDishesno(prodCodeMap.get(lastItem.getSkuCode() + "_" + lastItem.getSkuVersion()));
                lastDetailed.setBillid(billDTO.getBillid());
                lastDetailed.setBillno(billDTO.getBillno());
                lastDetailed.setPackageitemid(billDetailedId);
                lastDetailed.setIsnotakeaway(finalIsTakeOut);
                lastDetailed.setPaydate(billDTO.getPaydate());
                lastDetailed.setOgnid(billDTO.getOgnid());
                detailedConvert(lastDetailed, lastItem, orderDetail.getSkuNum());
                String productCode = prodCodeMap.get(lastItem.getSkuCode() + "_" + lastItem.getSkuVersion());
                lastDetailed.setDishesno(productCode);

                // 计算剩余金额，保证总和一致
                BigDecimal lastDishesAmount = totalSubOriginDishesAmount.subtract(accSubDishesAmount);
                BigDecimal lastBenefitAmount = totalSubBenefitAmount.subtract(accSubBenefitAmount);
                BigDecimal lastRealPrice = totalSubRealPrice.subtract(accSubRealPrice);
                BigDecimal lastFreeAfterPrice = totalSubFreeAfterPrice.subtract(accSubFreeAfterPrice);

                lastDetailed.setDishesamount(lastDishesAmount.doubleValue());
                lastDetailed.setBenefitamount(lastBenefitAmount.doubleValue());
                lastDetailed.setRealprice(lastRealPrice.doubleValue());
                lastDetailed.setFreeafterprice(lastFreeAfterPrice.doubleValue());
                lastDetailed.setReceivableprice(lastDishesAmount.doubleValue());

                demoBillDetailList.add(lastDetailed);
            }
        }
        //这里不能再有逻辑处理了，都在上面处理完
        return demoBillDetailList;
    }

}
