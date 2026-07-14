package com.personal.demo.adapter.internal.member.convert;

import com.personal.demo.pojo.dto.member.request.*;
import com.personal.demo.pojo.dto.member.response.*;
import com.personal.demo.pojo.dto.member.response.grade.GradeDto;
import com.personal.demo.request.member.*;
import com.personal.demo.response.member.*;
import com.personal.demo.response.member.grade.GradeResultDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 类转换
 *
 * @Author: fxs
 * @Date: 2025/8/13 10:51
 */
@Mapper(componentModel = "spring")
public interface MemberConvert {

    MemberConvert INSTANCE = Mappers.getMapper(MemberConvert.class);


    /**
     * 会员注册参数转换
     *
     * @param param
     * @return
     */
    MemberOpenCardReqDto userRegisterConvert(MemberOpenCardParam param);

    /**
     * 根据openid查询会员参数转换
     *
     * @param param
     * @return
     */
    OpenidParamReqDto userByOpenIdConvert(OpenidParamParam param);

    /**
     * 获取会员优惠券列表参数转换
     *
     * @param param
     * @return
     */
    ListParamReqDto getCouponListConvert(ListParam param);


    /**
     * 修改会员信息参数转换
     *
     * @param param
     * @return
     */
    MemberUpdateReqDto editUserInfoConvert(MemberUpdateParam param);

    MemberBaseReqDto getGradeRightsConvert(MemberBaseParam param);

    EmptyReqDto emptyReqDtoConvert(EmptyParam param);

    ValueCardOpReqDto bindPhysicalCardConvert(ValueCardOpParam param);

    CnoReqDto gradeUpgradeTaskConvert(CnoParam param);

    ChargeRuleReqDto getChargeRuleConvert(ChargeRuleParam param);

    ChargePreviewReqDto chargePreviewConvert(ChargePreviewParam param);

    ChargeCommitReqDto chargeCommitConvert(ChargeCommitParam param);

    ChargeCancelReqDto chargeCancelConvert(ChargeCancelParam param);

    ListParamReqDto listParamReqDtoConvert(ListParam param);

    ActivityGiftListReqDto getUserActivityConvert(ActivityGiftListParam param);




    /**
     * 获取会员优惠券列表参数转换
     *
     * @param invoke
     * @return
     */
    MemberOpenCardResultDto userRegisterDtoConvert(MemberOpenCardRespDto invoke);

    /**
     * 根据openid查询会员参数转换
     *
     * @param invoke
     * @return
     */
    MemberInfoResultDto userByOpenIdDtoConvert(MemberInfoRespDto invoke);

    /**
     * 获取会员优惠券列表参数转换
     *
     * @param invokes
     * @return
     */
    GradeResultDto gradeRightsDtoConvert(GradeDto invokes);
    /**
     * 获取会员优惠券列表参数转换
     *
     * @param invokes
     * @return
     */
    List<GradeResultDto> gradeRightsDtoListConvert(List<GradeDto> invokes);

    /**
     * 获取会员优惠券列表参数转换
     *
     * @param invoke
     * @return
     */
    PolicyResultDto getPolicyDtoConvert(PolicyRespDto invoke);

    /**
     * 获取会员优惠券列表参数转换
     *
     * @param invoke
     * @return
     */
    ValueCardInfoResultDto bindPhysicalCardDtoConvert(ValueCardInfoRespDto invoke);

    /**
     * 获取会员优惠券列表参数转换
     *
     * @param invoke
     * @return
     */
    GradeUpgradeTaskResultDto gradeUpgradeTaskDtoConvert(GradeUpgradeTaskResDto invoke);

    /**
     * 获取会员优惠券列表参数转换
     *
     * @param invoke
     * @return
     */
    ChargeRuleResultDto getChargeRuleDtoConvert(ChargeRuleResDto invoke);

    /**
     * 获取会员优惠券列表参数转换
     *
     * @param invoke
     * @return
     */
    ChargePreviewResultDto chargePreviewDtoConvert(ChargePreviewResDto invoke);

    /**
     * 获取会员优惠券列表参数转换
     *
     * @param invoke
     * @return
     */
    ChargeCommitResultDto chargeCommitDtoConvert(ChargeCommitResDto invoke);

    /**
     * 获取会员优惠券列表参数转换
     *
     * @param invoke
     * @return
     */
    ChargeCancelResultDto chargeCancelDtoConvert(ChargeCancelResDto invoke);

    /**
     * 获取会员优惠券列表参数转换
     *
     * @param invoke
     * @return
     */
    ChargeUsageDetailsResultDto listParamDtoConvert(ChargeUsageDetailsResDto invoke);

    /**
     * 获取会员优惠券列表参数转换
     *
     * @param invoke
     * @return
     */
    ChargePolicyResultDto chargeGetPolicyDtoConvert(ChargePolicyResDto invoke);

    /**
     * 获取会员优惠券列表参数转换
     *
     * @param invoke
     * @return
     */
    ActivityGiftListResultDto getUserActivityDtoConvert(ActivityGiftListResDto invoke);

    /**
     * 获取会员优惠券列表参数转换
     *
     * @param
     * @return
     */
    BindPhoneReqDto userBindPhoneConvert(BindPhoneParam bizData);

    /**
     * 获取会员优惠券列表参数转换
     *
     * @param invoke
     * @return
     */
    BindPhoneResultDto userBindPhoneDtoConvert(BindPhoneResDto invoke);

    /**
     * 合并电子卡参数转换
     *
     * @param bizData
     * @return
     */
    MergeEleCardReqDto mergeEleCardConvert(MergeEleCardParam bizData);

    /**
     * 获合并电子卡参数转换
     *
     * @param invoke
     * @return
     */
    MergeEleCardResultDto mergeEleCardDtoConvert(MergeEleCardRespDto invoke);


    /**
     * 修改会员信息参数转换
     *
     * @param invoke
     * @return
     */
    OperationResultDto editUserInfoDtoConvert(MemberUpdateRespDto invoke);
}
