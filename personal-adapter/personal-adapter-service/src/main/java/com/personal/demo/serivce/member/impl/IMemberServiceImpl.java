package com.personal.demo.serivce.member.impl;

import com.personal.demo.adapter.internal.member.convert.MemberConvert;
import com.personal.demo.conf.CardConfig;
import com.personal.demo.conf.GsonFactory;
import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.enu.internal.base.CouponType;
import com.personal.demo.enu.internal.node.MemberNode;
import com.personal.demo.pojo.dto.member.request.*;
import com.personal.demo.pojo.dto.member.response.*;
import com.personal.demo.pojo.dto.member.response.coupon.*;
import com.personal.demo.pojo.dto.member.response.grade.GradeDto;
import com.personal.demo.request.member.*;
import com.personal.demo.response.member.*;
import com.personal.demo.response.member.activity.ActivityGiftCouponResultDto;
import com.personal.demo.response.member.coupon.*;
import com.personal.demo.response.member.grade.GradeResultDto;
import com.personal.demo.serivce.member.IMemberService;
import com.personal.demo.serivce.member.Sm2Invoker;
import com.personal.demo.serivce.member.util.CouponSortUtil;
import com.personal.demo.serivce.member.util.DateUtil;
import com.personal.demo.serivce.member.util.PriceUtils;
import com.personal.demo.util.TypeHelperUtil;
import com.common.base.exception.BizException;
import com.common.dto.constant.NumberConstant;
import com.common.dto.handle.exceptions.error.BusinessException;
import com.common.tools.GsonUtils;
import com.google.gson.Gson;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * 会员服务实现类
 *
 * @Author: fxs
 * @Date: 2026/1/12 15:47
 */
@Service
@Slf4j
public class IMemberServiceImpl implements IMemberService {

    private final Sm2Invoker sm2Invoker;

    private final Gson gson = GsonFactory.getInstance();
    private Object QueryTransferCouponDetailParam;

    public IMemberServiceImpl(Sm2Invoker sm2Invoker) {

        this.sm2Invoker = sm2Invoker;
    }

    private final static String CODE = "errcode";
    private final static String MESSAGE = "errmsg";
    private final static String SUCCESS = "0";

    private final static String DATA = "res";
    static ExecutorService executorService = Executors.newFixedThreadPool(1);
    @Override
    public MemberOpenCardResultDto userRegister(MemberOpenCardParam param, BaseNode node) {

        MemberOpenCardReqDto reqDto = MemberConvert.INSTANCE.userRegisterConvert(param);

        MemberOpenCardRespDto invoke = sm2Invoker.invoke(reqDto, node, MemberOpenCardRespDto.class);

        log.info("会员注册返回结果：{}", GsonUtils.beanToJson(invoke));

        MemberOpenCardResultDto resultDto = MemberConvert.INSTANCE.userRegisterDtoConvert(invoke);

        resultDto.setType(invoke.getUType());
        resultDto.setMembersCode(invoke.getUNo());
        resultDto.setGender(invoke.getUGender().equals(1) ? "M" : "F");
        resultDto.setWxNickname(invoke.getUWXNickname());
        resultDto.setAvatar(invoke.getUAvatar());
        resultDto.setRegistered(invoke.getURegistered());
        resultDto.setLastRegistered(invoke.getULastRegistered());
        resultDto.setCardStatus(invoke.getUCardStatus());
        resultDto.setActivityStatus(invoke.getUActivityStatus());
        resultDto.setName(invoke.getUName());
        resultDto.setPhone(invoke.getUPhone());
        resultDto.setBirthDay(invoke.getUBirthDay());
        resultDto.setFromOtherCNo(invoke.getUFromOtherCNo());
        resultDto.setFromOtherCMoney(invoke.getUFromOtherCMoney());
        resultDto.setFromOtherCCredit(invoke.getUFromOtherCCredit());

        return resultDto;
    }

    @Override
    public MemberInfoResultDto getUserByOpenId(OpenidParamParam param, BaseNode node) {

        OpenidParamReqDto reqDto = MemberConvert.INSTANCE.userByOpenIdConvert(param);

        MemberInfoRespDto invoke = sm2Invoker.invoke(reqDto, node, MemberInfoRespDto.class);

        MemberInfoResultDto resultDto = MemberConvert.INSTANCE.userByOpenIdDtoConvert(invoke);

        resultDto.setMembersCode(invoke.getCno());

        resultDto.setGender(invoke.getSex().equals(1) ? "M" : "F");

        return resultDto;
    }

    @Override
    public CouponListRespDto getCouponList(ListParam param, BaseNode node) {

        ListParamReqDto reqDto = MemberConvert.INSTANCE.getCouponListConvert(param);

        return sm2Invoker.invoke(reqDto, node, CouponListRespDto.class);

    }

    @Override
    public String editUserInfo(MemberUpdateParam param, BaseNode node) {

        MemberUpdateReqDto reqDto = MemberConvert.INSTANCE.editUserInfoConvert(param);

        MemberUpdateRespDto invoke = sm2Invoker.invoke(reqDto, node, MemberUpdateRespDto.class);

        return invoke.getResult();
    }

    @Override
    public MemberGradesResultDto getGradeRights(MemberBaseParam param, BaseNode node) {

        MemberBaseReqDto reqDto = MemberConvert.INSTANCE.getGradeRightsConvert(param);

        Type type = TypeHelperUtil.listOf(GradeDto.class);

        List<GradeDto> invokes = sm2Invoker.invoke(reqDto, node, type);

//        invokes.forEach(gradeDto -> {
//            if (gradeDto != null && gradeDto.getProcess() != null) {
//                // 筛选出 ruleType 为 4 或 6 的 ProcessDto
//                List<ProcessDto> filteredProcesses = gradeDto.getProcess().stream().filter(process -> process != null && (process.getRuleType() == 4 || process.getRuleType() == 6)).toList();
//                log.info("筛选后的 ProcessDto 列表：{}", GsonUtils.beanToJson(filteredProcesses));
//                gradeDto.setProcess(filteredProcesses);
//            }
//        });

        List<GradeResultDto> gradeResultDto = MemberConvert.INSTANCE.gradeRightsDtoListConvert(invokes);

        return new MemberGradesResultDto().setGrades(gradeResultDto);
    }

    @Override
    public PolicyResultDto getPolicy(EmptyParam param, BaseNode node) {

        EmptyReqDto reqDto = MemberConvert.INSTANCE.emptyReqDtoConvert(param);

        reqDto.setType(NumberConstant.TWO);

        PolicyRespDto invoke = sm2Invoker.invoke(reqDto, node, PolicyRespDto.class);

        return MemberConvert.INSTANCE.getPolicyDtoConvert(invoke);

    }

    @Override
    public ValueCardInfoResultDto bindPhysicalCard(ValueCardOpParam param, BaseNode node) {

        ValueCardOpReqDto reqDto = MemberConvert.INSTANCE.bindPhysicalCardConvert(param);

        reqDto.setCno(param.getMembersCode());

        ValueCardInfoRespDto invoke = sm2Invoker.invoke(reqDto, node, ValueCardInfoRespDto.class);

        return MemberConvert.INSTANCE.bindPhysicalCardDtoConvert(invoke);
    }

    @Override
    public GradeUpgradeTaskResultDto gradeUpgradeTask(CnoParam param, BaseNode node) {

        CnoReqDto reqDto = MemberConvert.INSTANCE.gradeUpgradeTaskConvert(param);

        GradeUpgradeTaskResDto invoke = sm2Invoker.invoke(reqDto, node, GradeUpgradeTaskResDto.class);

        GradeUpgradeTaskResultDto resultDto = MemberConvert.INSTANCE.gradeUpgradeTaskDtoConvert(invoke);

        // 检查 userCard 和 dValue 是否非空
        if (resultDto.getUserCard() != null) {
            // 设置 levelDifference

            resultDto.getUserCard().setUserId(invoke.getUserCard().getCId());

            resultDto.getUserCard().setLevelDifference(invoke.getUserCard().getDValue());

            // 检查 chargeProgress 是否非空
            if (resultDto.getUserCard().getChargeProgress() != null) {
                resultDto.getUserCard().getChargeProgress().setLevelDifference(invoke.getUserCard().getChargeProgress().getDValue());
            }

            // 检查 consumeProgress 是否非空
            if (resultDto.getUserCard().getConsumeProgress() != null) {
                resultDto.getUserCard().getConsumeProgress().setLevelDifference(invoke.getUserCard().getConsumeProgress().getDValue());
            }
        }


        return resultDto;

    }

    @Override
    public ChargeRuleResultDto getChargeRule(ChargeRuleParam param, BaseNode node) {

        ChargeRuleReqDto reqDto = MemberConvert.INSTANCE.getChargeRuleConvert(param);

        reqDto.setCno(param.getMembersCode());

        ChargeRuleResDto invoke = sm2Invoker.invoke(reqDto, node, ChargeRuleResDto.class);


        ChargeRuleResultDto resultDto = MemberConvert.INSTANCE.getChargeRuleDtoConvert(invoke);

        resultDto.getContent().forEach(ruleContent -> {
            BigDecimal priceInYuan = new BigDecimal(ruleContent.getPriceFen()).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP); // 保留2位小数，四舍五入
            ruleContent.setPrice(priceInYuan); // 设置价格为元
        });
        log.info("获取会员充值规则转换后的数据：{}", GsonUtils.beanToJson(resultDto));

        return resultDto;
    }

    @Override
    public ChargePreviewResultDto chargePreview(ChargePreviewParam param, BaseNode node) {

        ChargePreviewReqDto reqDto = MemberConvert.INSTANCE.chargePreviewConvert(param);

        ChargePreviewResDto invoke = sm2Invoker.invoke(reqDto, node, ChargePreviewResDto.class);

        return MemberConvert.INSTANCE.chargePreviewDtoConvert(invoke);

    }

    @Override
    public ChargeCommitResultDto chargeCommit(ChargeCommitParam param, BaseNode node) {

        ChargeCommitReqDto reqDto = MemberConvert.INSTANCE.chargeCommitConvert(param);

        ChargeCommitResDto invoke = sm2Invoker.invoke(reqDto, node, ChargeCommitResDto.class);

        return MemberConvert.INSTANCE.chargeCommitDtoConvert(invoke);
    }

    @Override
    public ChargeCancelResultDto chargeCancel(ChargeCancelParam param, BaseNode node) {

        ChargeCancelReqDto reqDto = MemberConvert.INSTANCE.chargeCancelConvert(param);

        ChargeCancelResDto invoke = sm2Invoker.invoke(reqDto, node, ChargeCancelResDto.class);

        return MemberConvert.INSTANCE.chargeCancelDtoConvert(invoke);

    }

    @Override
    public ChargeUsageDetailsResultDto chargeUsageDetails(ListParam param, BaseNode node) {

        ListParamReqDto reqDto = MemberConvert.INSTANCE.listParamReqDtoConvert(param);

        ChargeUsageDetailsResDto invoke = sm2Invoker.invoke(reqDto, node, ChargeUsageDetailsResDto.class);

        return MemberConvert.INSTANCE.listParamDtoConvert(invoke);

    }

    @Override
    public ChargePolicyResultDto chargeGetPolicy(EmptyParam param, BaseNode node) {

        EmptyReqDto reqDto = MemberConvert.INSTANCE.emptyReqDtoConvert(param);

        ChargePolicyResDto invoke = sm2Invoker.invoke(reqDto, node, ChargePolicyResDto.class);

        return MemberConvert.INSTANCE.chargeGetPolicyDtoConvert(invoke);
    }

    @Override
    public UnUsedCouponListResultDto getUnusedCouponList(CouponListParam param, BaseNode node) {

        ListParamReqDto reqDto = new ListParamReqDto();
        reqDto.setCno(param.getMembersCode());
        reqDto.setPage(param.getPage());

        log.info("【查询会员可用的券列表】入参：{},node:{}", GsonUtils.beanToJson(reqDto), GsonUtils.beanToJson(node));
        CouponListRespDto invoke = sm2Invoker.invoke(reqDto, node, CouponListRespDto.class);
        log.info("【查询会员可用的券列表】返回结果：{}", GsonUtils.beanToJson(invoke));

        if (invoke == null){
            throw new BusinessException("适配样例会员券-可用的券列表数据 - 转换异常");
        }

        if (CollectionUtils.isEmpty(invoke.getData())) {

            UnUsedCouponListResultDto unUsedCouponListResultDto = new UnUsedCouponListResultDto();
            if (ObjectUtils.isEmpty(invoke.getPageOptions())) {

                unUsedCouponListResultDto.setPageOptions(new PageOptions());
                return unUsedCouponListResultDto;
            }
            unUsedCouponListResultDto.setPageOptions(invoke.getPageOptions());
            return unUsedCouponListResultDto;
        }

        UnUsedCouponListResultDto unUsedCouponListResultDto = new UnUsedCouponListResultDto();
        List<CouponDetailResultDto> couponDetailDtoList = new ArrayList<>();

        invoke.getData().forEach(couponRespDto -> {
            if (CollectionUtils.isEmpty(couponRespDto.getCoupon_ids())) {
                throw new BusinessException("适配样例会员券-券id异常");
            }

            couponRespDto.getCoupon_ids().forEach(couponId -> {
                CouponDetailResultDto couponDetailResultDto = new CouponDetailResultDto();
                couponDetailResultDto.setCouponId(couponId);
                couponDetailResultDto.setCouponName(couponRespDto.getOther_name());
                couponDetailResultDto.setInstruction(couponRespDto.getCSummary());
                if (CollectionUtils.isEmpty(couponRespDto.getCExtend())) {
                    throw new BusinessException("适配样例会员券-扩展信息异常");
                }
                if (ObjectUtils.isEmpty(couponRespDto.getCExtend().get(0))
                        || ObjectUtils.isEmpty(couponRespDto.getCExtend().get(0).getCeType())) {
                    throw new BusinessException("适配样例会员券-扩展信息异常");
                }
                Integer ceType = Integer.valueOf(couponRespDto.getCExtend().get(0).getCeType());
                if (ceType.equals(CouponType.ZHE_KOU_QUAN.ceType)) {
                    couponDetailResultDto.setCouponType(CouponType.ZHE_KOU_QUAN.personalType);  //  折扣券
                    couponDetailResultDto.setCouponPrice(PriceUtils.converBaiFenBi(couponRespDto.getDiscountRate()));
                } else if (ceType.equals(CouponType.LI_JIAN_QUAN.ceType)) {
                    couponDetailResultDto.setCouponType(CouponType.LI_JIAN_QUAN.personalType);  // 满减券
                    //couponDetailResultDto.setCouponPrice(BigDecimal.valueOf(couponRespDto.getDeno()));
                    couponDetailResultDto.setCouponPrice(new BigDecimal(couponRespDto.getDeno()));
                } else if (ceType.equals(CouponType.DAI_JIN_QUAN.ceType)) {
                    couponDetailResultDto.setCouponType(CouponType.DAI_JIN_QUAN.personalType);  // 代金满减券
                    couponDetailResultDto.setCouponPrice(new BigDecimal(couponRespDto.getDeno()));
                    //couponDetailResultDto.setCouponPrice(BigDecimal.valueOf(couponRespDto.getDeno()));
                }
                couponDetailResultDto.setValidityEndTime(DateUtil.convertDateTimeFormatJava8(couponRespDto.getFailure_time()));

                couponDetailResultDto.setIsTransfer(couponRespDto.getGive_friend());
                couponDetailDtoList.add(couponDetailResultDto);
            });


        });
        List<CouponDetailResultDto> couponDetailResultDtoList = CouponSortUtil.processCouponList(couponDetailDtoList);

        unUsedCouponListResultDto.setCouponDetailResultDtoList(couponDetailResultDtoList);
        if (ObjectUtils.isEmpty(invoke.getPageOptions())) {
            throw new BusinessException("适配样例会员券-分页数据异常");
        }
        unUsedCouponListResultDto.setPageOptions(invoke.getPageOptions());
        return unUsedCouponListResultDto;
    }


    @Override
    public List<CouponDetailResultDto> getInvalidCouponList(CouponListParam param, BaseNode node) {

        InvalidCouponListReqDto reqDto = new InvalidCouponListReqDto();
        reqDto.setCno(param.getMembersCode());
        reqDto.setPage(param.getPage());
        reqDto.setPerPage(param.getPerPage());
        log.info("【查询会员失效的券】入参：{},node:{}", GsonUtils.beanToJson(reqDto), GsonUtils.beanToJson(node));
        Type type = TypeHelperUtil.listOf(InvalidCouponListRespDto.class);
        List<InvalidCouponListRespDto> invoke = sm2Invoker.invoke(reqDto, node, type);
        log.info("【查询会员失效的券】返回结果：{}", GsonUtils.beanToJson(invoke));

        if (invoke == null){
            throw new BusinessException("适配样例会员券-失效的券列表数据 - 转换异常");
        }
        if (CollectionUtils.isEmpty(invoke)) {
            return new ArrayList<>();
        }
        ArrayList<CouponDetailResultDto> couponDetailResultDtoList = new ArrayList<>();

        invoke.forEach(couponRespDto -> {
            if (CollectionUtils.isEmpty(couponRespDto.getCoupon_ids())) {
                throw new BusinessException("适配样例会员券-券id异常");
            }

            couponRespDto.getCoupon_ids().forEach(couponId -> {
                CouponDetailResultDto couponDetailResultDto = new CouponDetailResultDto();
                couponDetailResultDto.setCouponId(couponId);
                couponDetailResultDto.setCouponName(couponRespDto.getAlias_name());
                couponDetailResultDto.setInstruction(couponRespDto.getCSummary()); // 富文本描述

                if (ObjectUtils.isEmpty(couponRespDto.getCExtend())) {
                    throw new BusinessException("适配样例会员券-扩展信息异常");
                }
                if (ObjectUtils.isEmpty(couponRespDto.getCExtend())
                        || ObjectUtils.isEmpty(couponRespDto.getCExtend().getCeType())) {
                    throw new BusinessException("适配样例会员券-扩展信息异常");
                }
                Integer ceType = Integer.valueOf(couponRespDto.getCExtend().getCeType());
                if (ceType.equals(CouponType.ZHE_KOU_QUAN.ceType)) {
                    couponDetailResultDto.setCouponType(CouponType.ZHE_KOU_QUAN.personalType);  //  折扣券
                    couponDetailResultDto.setCouponPrice(PriceUtils.converBaiFenBi(couponRespDto.getDiscountRate()));
                } else if (ceType.equals(CouponType.LI_JIAN_QUAN.ceType)) {
                    couponDetailResultDto.setCouponType(CouponType.LI_JIAN_QUAN.personalType);  // 满减券
                    couponDetailResultDto.setCouponPrice(new BigDecimal(couponRespDto.getDeno()));
                } else if (ceType.equals(CouponType.DAI_JIN_QUAN.ceType)) {
                    couponDetailResultDto.setCouponType(CouponType.DAI_JIN_QUAN.personalType);  // 代金满减券
                    couponDetailResultDto.setCouponPrice(new BigDecimal(couponRespDto.getDeno()));
                }
                couponDetailResultDto.setValidityEndTime(DateUtil.convertDateTimeFormatJava8(couponRespDto.getFailure_time()));

                couponDetailResultDtoList.add(couponDetailResultDto);
            });


        });

        return couponDetailResultDtoList;
    }

    @Override
    public CouponDetailResultDto  queryCouponData(CouponDetailParam bizData, BaseNode node) {

        CouponDetailResultDto couponDetailResultDto = new CouponDetailResultDto();


        CouponDetailRespDto couponDetailRespDto = queryCouponDetail(bizData, node);

        couponDetailResultDto.setValidityEndTime(DateUtil.convertDateTimeFormatJava8(couponDetailRespDto.getEnd()));
        couponDetailResultDto.setCouponStatus(couponDetailRespDto.getStatus());
        couponDetailResultDto.setCouponId(bizData.getCouponCode());
        couponDetailResultDto.setTemplateId(Integer.valueOf(couponDetailRespDto.getTemplate_id()));


        CouponTemplateRespDto couponTemplateRespDto = queryCouponTemPlate(Integer.valueOf(couponDetailRespDto.getTemplate_id()), bizData, node);

        couponDetailResultDto.setInstruction(couponTemplateRespDto.getSummary());
        couponDetailResultDto.setCouponName(couponTemplateRespDto.getAlias_name());
        couponDetailResultDto.setSaleMoney(couponDetailRespDto.getSale_money());
        if (couponTemplateRespDto.getType() == 2) {
            if (couponTemplateRespDto.getDiscountRate() == 0){
                couponDetailResultDto.setCouponType(CouponType.LI_JIAN_QUAN.personalType);


                couponDetailResultDto.setCouponPrice(PriceUtils.convertFenToYuan(couponTemplateRespDto.getDeno()));
            }else {
                couponDetailResultDto.setCouponType(CouponType.ZHE_KOU_QUAN.personalType);
                couponDetailResultDto.setCouponPrice(PriceUtils.converBaiFenBi(couponTemplateRespDto.getDiscountRate()));

            }
        } else if(couponTemplateRespDto.getType() == 1){
            // 代金券
            couponDetailResultDto.setCouponType(CouponType.DAI_JIN_QUAN.personalType);
            couponDetailResultDto.setCouponPrice(PriceUtils.convertFenToYuan(couponTemplateRespDto.getDeno()));
        }

        return couponDetailResultDto;
    }

    @Override
    public CouponDetailRespDto queryCouponDetail(CouponDetailParam bizData, BaseNode node) {
        BaseNode memberNode = MemberNode.MEMBER_USER_COUPON_DETAIL;
        CouponDetailReqDto reqDto = new CouponDetailReqDto();
        reqDto.setCno(bizData.getMembersCode());
        reqDto.setC2u_id(bizData.getCouponCode());

        log.info("【查询会员券的信息】入参：{},node:{}", GsonUtils.beanToJson(reqDto), GsonUtils.beanToJson(node));
        CouponDetailRespDto invoke = sm2Invoker.invoke(reqDto, memberNode, CouponDetailRespDto.class);
        log.info("【查询会员券的信息】返回结果：{}", GsonUtils.beanToJson(invoke));

        if (ObjectUtils.isEmpty(invoke)){
            throw new BusinessException("适配样例会员券-券详情数据 - 转换异常");
        }
        return invoke;
    }

    @Override
    public TransferCouponDetailRespDto queryTransferCouponDetail(QueryTransferCouponDetailParam bizData, BaseNode node) {
        BaseNode memberNode = MemberNode.MEMBER_USER_TRANSFER_COUPON_DETAIL;
        QueryTransferCouponDetailReqDto reqDto = new QueryTransferCouponDetailReqDto();
        reqDto.setTransfer_unionid(bizData.getTransferUnionid());
        reqDto.setShare_time(bizData.getShareTime());
        log.info("【查询转赠的会员券的信息】入参：{},node:{}", GsonUtils.beanToJson(reqDto), GsonUtils.beanToJson(node));
        TransferCouponDetailRespDto invoke = sm2Invoker.invoke(reqDto, memberNode, TransferCouponDetailRespDto.class);
        log.info("【查询转赠的会员券的信息】返回结果：{}", GsonUtils.beanToJson(invoke));
        if (ObjectUtils.isEmpty(invoke)){
            throw new BusinessException("适配样例会员券-转增的券详情数据 - 转换异常");
        }        return invoke;
    }


    @Override
    public CouponTemplateRespDto queryCouponTemPlate(Integer templateId, CouponDetailParam bizData, BaseNode node) {
        BaseNode memberNode = MemberNode.MEMBER_USER_COUPON_TEMPLATE;
        CouponTemplateReqDto reqDto = new CouponTemplateReqDto();
        reqDto.setCoupon_id(templateId);

        log.info("【查询会员券模版】入参：{},node:{}", GsonUtils.beanToJson(reqDto), GsonUtils.beanToJson(node));
        CouponTemplateRespDto invoke = sm2Invoker.invoke(reqDto, memberNode, CouponTemplateRespDto.class);
        log.info("【查询会员券模版】返回结果：{}", GsonUtils.beanToJson(invoke));

        if (ObjectUtils.isEmpty(invoke)){
            throw new BusinessException("适配样例会员券-券模版数据 - 转换异常");
        }
        return invoke;
    }

    @Override
    public TransferCouponResultDto transferCoupon(TransferCouponParam bizData, BaseNode node) {

        CouponDetailParam couponDetailParam = new CouponDetailParam();
        couponDetailParam.setMembersCode(bizData.getMembersCode());
        couponDetailParam.setCouponCode(bizData.getCouponCode());
        CouponDetailRespDto couponDetailRespDto = queryCouponDetail(couponDetailParam, node);

/*        if (couponDetailRespDto.getStatus() != 1) {

            TransferCouponResultDto result = new TransferCouponResultDto();
            result.setUnionid(bizData.getUnionid());
            result.setResult("Fail");
            return result;
        }*/


        TransferCouponReqDto reqDto = new TransferCouponReqDto();
        reqDto.setUnionid(bizData.getUnionid());
        reqDto.setCoupon_ids(List.of(bizData.getCouponCode()));
        reqDto.setShare_time(String.valueOf(System.currentTimeMillis()));
        log.info("【转增券】入参：{},node:{}", GsonUtils.beanToJson(reqDto), GsonUtils.beanToJson(node));
        TransferCouponRespDto invoke = sm2Invoker.invoke(reqDto, node, TransferCouponRespDto.class);
        log.info("【转增券】返回结果：{}", GsonUtils.beanToJson(invoke));
        if (ObjectUtils.isEmpty(invoke)){
            throw new BusinessException("适配样例会员券-转增券返回结果 - 转换异常");
        }
        TransferCouponResultDto result = new TransferCouponResultDto();
        result.setUnionid(bizData.getUnionid());
        result.setResult(invoke.getResult());
        result.setShareTime(reqDto.getShare_time());
        return result;
    }


    @Override
    public ReceiveCouponResultDto receiveCoupon(ReceiveCouponParam bizData, BaseNode node) {
        BaseNode memberNode = MemberNode.MEMBER_USER_TRANSFER_COUPON_DETAIL;
        QueryTransferCouponDetailParam queryTransferCouponDetailParam = new QueryTransferCouponDetailParam();
        queryTransferCouponDetailParam.setTransferUnionid(bizData.getTransferUnionid());
        queryTransferCouponDetailParam.setShareTime(bizData.getShareTime());
        TransferCouponDetailRespDto transferCouponDetailRespDto = queryTransferCouponDetail(queryTransferCouponDetailParam, memberNode);
/*        if (transferCouponDetailRespDto.getStatus() != 1) {
            // TODO 和前端约定当券不能转增时,如何处理
            throw new BusinessException("转赠券不存在");
        }*/
        ReceiveCouponReqDto reqDto = new ReceiveCouponReqDto();
        reqDto.setUnionid(bizData.getUnionid());
        reqDto.setTransfer_unionid(bizData.getTransferUnionid());
        reqDto.setShare_time(bizData.getShareTime());
        log.info("【领取券】入参：{},node:{}", GsonUtils.beanToJson(reqDto), GsonUtils.beanToJson(node));
        ReceiveCouponResultDto invoke = sm2Invoker.invoke(reqDto, node, ReceiveCouponResultDto.class);
        log.info("【领取券】返回结果：{}", GsonUtils.beanToJson(invoke));
        if (ObjectUtils.isEmpty(invoke)){
            throw new BusinessException("适配样例会员券-领取券返回结果 - 转换异常");
        }
        return invoke;
    }

    @Override
    public ActivityGiftListResultDto getUserActivity(ActivityGiftListParam bizData, BaseNode node) {

        ActivityGiftListReqDto reqDto  = MemberConvert.INSTANCE.getUserActivityConvert(bizData);
        List<ActivityGiftListResDto> invoke = sm2Invoker.invokeList(reqDto, node, ActivityGiftListResDto.class);
        log.info("会员等级升级任务查询返回结果：{}", GsonUtils.beanToJson(invoke));
        if (CollectionUtils.isEmpty( invoke)){
            log.info("会员等级升级任务查询返回结果，param:{}",GsonUtils.beanToJson(reqDto));
            return null;
        }
        //筛选过滤 会员完善资料的奖励
        ActivityGiftListResDto activityGiftListResDto = invoke.stream().filter(t->Objects.equals(t.getType(),128)).findFirst().orElse(null);
        if (Objects.isNull(activityGiftListResDto)){
            log.info("完善会员资料后无奖励，param:{}",GsonUtils.beanToJson(reqDto));
            return null;
        }
        ActivityGiftListResultDto resultDto = MemberConvert.INSTANCE.getUserActivityDtoConvert(activityGiftListResDto);
        if (!Objects.isNull(resultDto) && !CollectionUtils.isEmpty(resultDto.getCoupons())){
            for (ActivityGiftCouponResultDto coupon : resultDto.getCoupons()) {
                coupon.setType(CouponType.getCouponTypeByCeType(coupon.getCType()).personalType);
                if (Objects.equals(CouponType.ZHE_KOU_QUAN.personalType,coupon.getType())){
                    //如果是折扣券，要查折扣率
                    BaseNode memberNode = MemberNode.MEMBER_USER_COUPON_TEMPLATE;
                    CouponTemplateReqDto req = new CouponTemplateReqDto();
                    req.setCoupon_id(Integer.valueOf(coupon.getCouponid()));
                    log.info("【查询会员券模版】入参：{},node:{}", GsonUtils.beanToJson(req), GsonUtils.beanToJson(node));
                    CouponTemplateRespDto couponDiscountRate = sm2Invoker.invoke(req, memberNode, CouponTemplateRespDto.class);
                    log.info("【查询会员券模版】返回结果：{}", GsonUtils.beanToJson(invoke));
                    if (ObjectUtils.isEmpty(invoke)){
                        throw new BusinessException("适配样例会员券-券模版数据 - 转换异常");
                    }
                    BigDecimal discountRate = BigDecimal.valueOf(couponDiscountRate.getDiscountRate()).divide(BigDecimal.valueOf(10));
                    coupon.setAmount(String.valueOf(discountRate));
                }
            }
        }
        return resultDto;
    }





    @Resource
    private CardConfig cardConfig;

    @Override
    public BindPhoneResultDto userBindPhone(BindPhoneParam bizData, BaseNode node) {

        BindPhoneReqDto reqDto = MemberConvert.INSTANCE.userBindPhoneConvert(bizData);

        reqDto.setCno(bizData.getMembersCode());

        Map<String, Object> response = sm2Invoker.invokeMap(reqDto, node);

        String errorCode = response.get(CODE).toString();


        if (!cardConfig.getIsMerged()) {
            if (!SUCCESS.equals(errorCode)) {
                log.error("会员节点【 {} 】", node.nodeDesc());
                throw new BizException("501", response.get(MESSAGE).toString());
            }
        }

        if ("21004".equals(errorCode)) {

            // 根据手机号查询数据
            MemberBaseReqDto baseReqDto = new MemberBaseReqDto();

            reqDto.setCno(bizData.getPhone());

            UserBaseInfoMapResDto userBaseInfo = sm2Invoker.invoke(baseReqDto, MemberNode.MEMBER_GET_USER_BASE_INFO, UserBaseInfoMapResDto.class);

            log.info("会员绑定手机号查询手机号数据Map：{}", GsonUtils.beanToJson(userBaseInfo));

            List<MemberInfoRespDto> baseInfoList = userBaseInfo.getData().values().stream().toList();

            log.info("会员绑定手机号查询手机号数据List：{}", GsonUtils.beanToJson(baseInfoList));

            if (!CollectionUtils.isEmpty(baseInfoList)) {

                List<MemberInfoRespDto> cardInfoList = baseInfoList.stream().filter(t -> "微信".equals(t.getCardType())).toList();

                if (CollectionUtils.isEmpty(cardInfoList)) {
                    log.error("根据手机号查询无电子卡信息,手机号{}", GsonUtils.beanToJson(bizData.getPhone()));
                }

                if (cardInfoList.size() > NumberConstant.ONE) {
                    log.error("根据手机号查询多张电子卡信息,手机号{}", GsonUtils.beanToJson(bizData.getPhone()));
                }

                if (cardConfig.getIsMerged()) {
                    CompletableFuture.runAsync(() -> {
                        MemberInfoRespDto memberInfoRespDto = cardInfoList.get(NumberConstant.ZERO);
                        MergeEleCardReqDto eleCardReqDto = new MergeEleCardReqDto();
                        eleCardReqDto.setAcCardId(bizData.getMembersCode());
                        eleCardReqDto.setAcUid(bizData.getUid());
                        eleCardReqDto.setAcGrade(bizData.getGrade());
                        eleCardReqDto.setGrade(memberInfoRespDto.getGrade());
                        eleCardReqDto.setUid(memberInfoRespDto.getUid());
                        eleCardReqDto.setMobile(reqDto.getPhone());
                        eleCardReqDto.setSource(2);
                        MergeEleCardRespDto invoke = sm2Invoker.invoke(eleCardReqDto, MemberNode.MEMBER_MERGE_ELE_CARD, MergeEleCardRespDto.class);
                        log.info("会员合并电子卡返回结果：{}", GsonUtils.beanToJson(invoke));
                    }, executorService).exceptionally(ex -> {
                        // 记录异步执行过程中的异常信息
                        log.error("会员合并电子卡-错误信息: {}", GsonUtils.beanToJson(ex), ex);
                        return null;
                    });
                }


            }

        }

        String resData = GsonUtils.beanToJson(response.get(DATA));

        log.info("会员[" + node.nodeDesc() + "]响应报文内容:{}", resData);

        BindPhoneResDto invoke =  GsonUtils.jsonToBean(resData, TypeHelperUtil.getType(BindPhoneResDto.class));

        log.info("会员绑定手机号返回结果：{}", GsonUtils.beanToJson(invoke));

        return MemberConvert.INSTANCE.userBindPhoneDtoConvert(invoke);
    }

    @Override
    public MemberInfoResultDto getUserBaseInfo(MemberBaseParam bizData, BaseNode node) {

        MemberBaseReqDto reqDto = MemberConvert.INSTANCE.getGradeRightsConvert(bizData);

        Type type = TypeHelperUtil.mapOf(String.class, MemberInfoRespDto.class);

        Map<String, MemberInfoRespDto> invoke = sm2Invoker.invoke(reqDto, node, type);

        log.info("会员getUserBaseInfo返回结果：{}", GsonUtils.beanToJson(invoke));

        MemberInfoRespDto memberInfo = invoke.values().stream().toList().get(0);

        return MemberConvert.INSTANCE.userByOpenIdDtoConvert(memberInfo);

    }

    @Override
    public MemberInfoResultDto getUserAccountInfo(MemberBaseParam bizData, BaseNode node) {

        MemberBaseReqDto reqDto = MemberConvert.INSTANCE.getGradeRightsConvert(bizData);

        Type type = TypeHelperUtil.mapOf(String.class, MemberInfoRespDto.class);

        Map<String, MemberInfoRespDto> invoke = sm2Invoker.invoke(reqDto, node, type);

        log.info("会员查询会员账户返回结果：{}", GsonUtils.beanToJson(invoke));

        MemberInfoRespDto memberInfo = invoke.values().stream().toList().get(0);

        log.info("会员查询会员账户返回第一个值结果：{}", GsonUtils.beanToJson(invoke));

        // 如果没有找到相应的会员信息，记录警告并返回
        if (memberInfo == null) {
            log.warn("未能从返回结果中获取有效的会员信息，会员编码: {}", bizData.getCno());
            return null;  // 或者抛出异常，根据需求
        }

        MemberInfoResultDto resultDto = MemberConvert.INSTANCE.userByOpenIdDtoConvert(memberInfo);

        resultDto.setUid(invoke.keySet().stream().toList().get(0));

        resultDto.setMembersCode(memberInfo.getCno());

        return resultDto;
    }

    @Override
    public MergeEleCardResultDto mergeEleCard(MergeEleCardParam bizData, BaseNode node) {

        MergeEleCardReqDto reqDto = MemberConvert.INSTANCE.mergeEleCardConvert(bizData);

        MergeEleCardRespDto invoke = sm2Invoker.invoke(reqDto, node, MergeEleCardRespDto.class);

        log.info("合并电子卡返回：{}", GsonUtils.beanToJson(invoke));

        return MemberConvert.INSTANCE.mergeEleCardDtoConvert(invoke);

    }
}
