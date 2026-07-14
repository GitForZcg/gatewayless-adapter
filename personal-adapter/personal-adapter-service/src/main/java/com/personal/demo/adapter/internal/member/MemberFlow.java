package com.personal.demo.adapter.internal.member;

import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.enu.internal.node.MemberNode;
import com.personal.demo.pojo.dto.member.response.coupon.CouponListRespDto;
import com.personal.demo.request.base.BaseParams;
import com.personal.demo.request.member.*;
import com.personal.demo.response.member.*;
import com.personal.demo.rule.handler.AbstractFlowProcessor;
import com.personal.demo.serivce.member.IMemberService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Map;

import static java.util.Map.entry;

/**
 * 会员执行流程
 *
 * @Author: fxs
 * @Date: 2026/1/12 14:48
 */
@SuppressWarnings("rawtypes")
@Component
public class MemberFlow extends AbstractFlowProcessor<BaseParams> implements MemberFlowProcessor {

    @Resource
    private IMemberService iMemberService;

    @Override
    public String getProcessorType() {
        return "MEMBER";
    }

    @Override
    protected Map<BaseNode, BaseNodeFunction<BaseParams>> initNodeHandlers() {
        return Map.ofEntries(
                entry(MemberNode.MEMBER_USER_CURRENT_COUPON_LIST, this::getUnusedCouponList),
                entry(MemberNode.MEMBER_USER_INVALID_COUPON_LIST, this::getInvalidCouponList),
                entry(MemberNode.MEMBER_COUPON_TRANSFER, this::transferCoupon),
                entry(MemberNode.MEMBER_COUPON_TRANSFER_GET, this::queryTransferCouponDetail),
                entry(MemberNode.MEMBER_COUPON_RECEIVER, this::receiveCoupon),
                entry(MemberNode.MEMBER_USER_COUPON_DETAIL, this::queryCouponData),
                entry(MemberNode.MEMBER_GET_USER_ACTIVITY, this::getUserActivity),
                entry(MemberNode.MEMBER_GET_USER_BY_OPENID, this::getUserByOpenId),
                entry(MemberNode.MEMBER_GET_USER_ACCOUNT_INFO, this::getUserAccountInfo),
                entry(MemberNode.MEMBER_GET_USER_BASE_INFO, this::getUserBaseInfo),
                entry(MemberNode.MEMBER_GET_COUPON_LIST, this::getCouponList),
                entry(MemberNode.MEMBER_EDIT_USER_INFO, this::editUserInfo),
                entry(MemberNode.MEMBER_GRADE_UPGRADE_TASK, this::gradeUpgradeTask),
                entry(MemberNode.MEMBER_USER_REGISTER, this::userRegister),
                entry(MemberNode.MEMBER_USER_PHONE, this::userBindPhone),
                entry(MemberNode.MEMBER_GET_GRADE_RIGHTS, this::getGradeRights),
                entry(MemberNode.MEMBER_GET_POLICY, this::getPolicy),
                entry(MemberNode.MEMBER_BIND_PHYSICAL_CARD, this::bindPhysicalCard),
                entry(MemberNode.MEMBER_GET_CHARGE_RULE, this::getChargeRule),
                entry(MemberNode.MEMBER_CHARGE_PREVIEW, this::chargePreview),
                entry(MemberNode.MEMBER_CHARGE_COMMIT, this::chargeCommit),
                entry(MemberNode.MEMBER_CHARGE_CANCEL, this::chargeCancel),
                entry(MemberNode.MEMBER_CHARGE_USAGE_DETAILS, this::chargeUsageDetails),
                entry(MemberNode.MEMBER_CHARGE_GET_POLICY, this::chargeGetPolicy),
                entry(MemberNode.MEMBER_MERGE_ELE_CARD, this::mergeEleCard)
        );
    }


    @Override
    public ActivityGiftListResultDto getUserActivity(BaseParams params, BaseNode node) throws Exception {
        return iMemberService.getUserActivity((ActivityGiftListParam) params.getBizData(), node);
    }

    @Override
    public MemberOpenCardResultDto userRegister(BaseParams params, BaseNode node) throws Exception {
        return iMemberService.userRegister((MemberOpenCardParam) params.getBizData(), node);
    }

    @Override
    public BindPhoneResultDto userBindPhone(BaseParams params, BaseNode node) throws Exception {
        return iMemberService.userBindPhone((BindPhoneParam) params.getBizData(), node);
    }

    @Override
    public MemberInfoResultDto getUserByOpenId(BaseParams params, BaseNode node) throws Exception {
        return iMemberService.getUserByOpenId((OpenidParamParam) params.getBizData(), node);
    }

    @Override
    public MemberInfoResultDto getUserAccountInfo(BaseParams params, BaseNode node) throws Exception {
        return iMemberService.getUserAccountInfo((MemberBaseParam) params.getBizData(), node);

    }

    @Override
    public MemberInfoResultDto getUserBaseInfo(BaseParams params, BaseNode node) throws Exception {
        return iMemberService.getUserBaseInfo((MemberBaseParam) params.getBizData(), node);

    }

    @Override
    public CouponListRespDto getCouponList(BaseParams params, BaseNode node) throws Exception {
        return iMemberService.getCouponList((ListParam) params.getBizData(), node);
    }

    @Override
    public String editUserInfo(BaseParams params, BaseNode node) throws Exception {
        return iMemberService.editUserInfo((MemberUpdateParam) params.getBizData(), node);
    }

    @Override
    public GradeUpgradeTaskResultDto gradeUpgradeTask(BaseParams params, BaseNode node) throws Exception {
        return iMemberService.gradeUpgradeTask((CnoParam) params.getBizData(), node);

    }

    @Override
    public MemberGradesResultDto getGradeRights(BaseParams params, BaseNode node) throws Exception {
        return iMemberService.getGradeRights((MemberBaseParam) params.getBizData(), node);
    }

    @Override
    public PolicyResultDto getPolicy(BaseParams params, BaseNode node) throws Exception {
        return iMemberService.getPolicy((EmptyParam) params.getBizData(), node);
    }

    @Override
    public ValueCardInfoResultDto bindPhysicalCard(BaseParams params, BaseNode node) throws Exception {
        return iMemberService.bindPhysicalCard((ValueCardOpParam) params.getBizData(), node);
    }

    @Override
    public ChargeRuleResultDto getChargeRule(BaseParams bizData, BaseNode node) throws Exception {
        return iMemberService.getChargeRule((ChargeRuleParam) bizData.getBizData(), node);
    }

    @Override
    public ChargePreviewResultDto chargePreview(BaseParams bizData, BaseNode node) throws Exception {
        return iMemberService.chargePreview((ChargePreviewParam) bizData.getBizData(), node);
    }

    @Override
    public ChargeCommitResultDto chargeCommit(BaseParams bizData, BaseNode node) throws Exception {
        return iMemberService.chargeCommit((ChargeCommitParam) bizData.getBizData(), node);
    }

    @Override
    public ChargeCancelResultDto chargeCancel(BaseParams bizData, BaseNode node) throws Exception {
        return iMemberService.chargeCancel((ChargeCancelParam) bizData.getBizData(), node);

    }

    @Override
    public ChargeUsageDetailsResultDto chargeUsageDetails(BaseParams bizData, BaseNode node) throws Exception {
        return iMemberService.chargeUsageDetails((ListParam) bizData.getBizData(), node);
    }

    @Override
    public ChargePolicyResultDto chargeGetPolicy(BaseParams bizData, BaseNode node) throws Exception {
        return iMemberService.chargeGetPolicy((EmptyParam) bizData.getBizData(), node);
    }


    @Override
    public Object getUnusedCouponList(BaseParams bizData, BaseNode node) throws Exception {

        return iMemberService.getUnusedCouponList((CouponListParam) bizData.getBizData(), node);
    }

    @Override
    public Object getInvalidCouponList(BaseParams bizData, BaseNode node) throws Exception {
        return iMemberService.getInvalidCouponList((CouponListParam) bizData.getBizData(), node);

    }

    @Override
    public Object queryCouponData(BaseParams bizData, BaseNode node) throws Exception {
        return iMemberService.queryCouponData((CouponDetailParam) bizData.getBizData(), node);
    }


    @Override
    public Object transferCoupon(BaseParams bizData, BaseNode node) throws Exception {

        return iMemberService.transferCoupon((TransferCouponParam) bizData.getBizData(), node);

    }

    @Override
    public Object queryTransferCouponDetail(BaseParams bizData, BaseNode node) throws Exception {

        return iMemberService.queryTransferCouponDetail((QueryTransferCouponDetailParam) bizData.getBizData(), node);

    }

    @Override
    public Object receiveCoupon(BaseParams bizData, BaseNode node) throws Exception {

        return iMemberService.receiveCoupon((ReceiveCouponParam) bizData.getBizData(), node);
    }

    @Override
    public MergeEleCardResultDto mergeEleCard(BaseParams bizData, BaseNode node) throws Exception {
        return iMemberService.mergeEleCard((MergeEleCardParam) bizData.getBizData(), node);

    }
}
