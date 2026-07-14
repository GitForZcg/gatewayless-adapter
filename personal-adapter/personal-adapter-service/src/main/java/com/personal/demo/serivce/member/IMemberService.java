package com.personal.demo.serivce.member;

import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.pojo.dto.member.response.coupon.CouponDetailRespDto;
import com.personal.demo.pojo.dto.member.response.coupon.CouponListRespDto;
import com.personal.demo.pojo.dto.member.response.coupon.CouponTemplateRespDto;
import com.personal.demo.pojo.dto.member.response.coupon.TransferCouponDetailRespDto;
import com.personal.demo.request.member.*;
import com.personal.demo.response.member.*;
import com.personal.demo.response.member.coupon.*;

import java.util.List;


/**
 * 会员服务
 *
 * @Author: fxs
 * @Date: 2026/1/12 15:47
 */
public interface IMemberService {

    /**
     * 会员注册
     *
     * @param bizData
     * @param node
     * @return
     */
    MemberOpenCardResultDto userRegister(MemberOpenCardParam  bizData, BaseNode node);

    /**
     * 通过openId查询会员信息
     *
     * @param param
     * @param node
     * @return
     */
    MemberInfoResultDto getUserByOpenId(OpenidParamParam  param, BaseNode node);

    /**
     * 获取会员优惠券列表
     *
     * @param bizData
     * @param node
     * @return
     */
    CouponListRespDto getCouponList(ListParam bizData, BaseNode node);

    /**
     * 修改会员信息
     *
     * @param bizData
     * @param node
     * @return
     */
    String editUserInfo(MemberUpdateParam bizData, BaseNode node);


    /**
     * 获取会员等级权益
     *
     * @param bizData
     * @param node
     * @return
     */
    MemberGradesResultDto getGradeRights(MemberBaseParam bizData, BaseNode node);

    /**
     * 获取会员条款
     *
     * @param node
     * @return
     */
    PolicyResultDto getPolicy(EmptyParam bizData, BaseNode node);

    /**
     * 实体卡绑定
     *
     * @param bizData
     * @param node
     * @return
     */
    ValueCardInfoResultDto bindPhysicalCard(ValueCardOpParam bizData, BaseNode node);

    /**
     * 会员等级升级任务
     *
     * @param bizData
     * @param node
     * @return
     */
    GradeUpgradeTaskResultDto gradeUpgradeTask(CnoParam bizData, BaseNode node);

    /**
     * 获取会员充值规则
     *
     * @param bizData
     * @param node
     * @return
     */
    ChargeRuleResultDto getChargeRule(ChargeRuleParam bizData, BaseNode node);

    /**
     * 获取会员充值预览
     *
     * @param bizData
     * @param node
     * @return
     */
    ChargePreviewResultDto chargePreview(ChargePreviewParam bizData, BaseNode node);

    /**
     * 提交会员充值
     *
     * @param bizData
     * @param node
     * @return
     */
    ChargeCommitResultDto chargeCommit(ChargeCommitParam bizData, BaseNode node);

    /**
     * 取消会员充值
     *
     * @param bizData
     * @param node
     * @return
     */
    ChargeCancelResultDto chargeCancel(ChargeCancelParam bizData, BaseNode node);

    /**
     * 获取会员充值详情
     *
     * @param bizData
     * @param node
     * @return
     */
    ChargeUsageDetailsResultDto chargeUsageDetails(ListParam bizData, BaseNode node);

    /**
     * 获取会员充值条款
     *
     * @param bizData
     * @param node
     * @return
     */
    ChargePolicyResultDto chargeGetPolicy(EmptyParam bizData, BaseNode node);


    /**
     * 获取会员未使用的优惠券列表
     *
     * @param bizData
     * @param node
     * @return
     */
    UnUsedCouponListResultDto getUnusedCouponList(CouponListParam bizData, BaseNode node);

    /**
     * 查看失效优惠券列表
     *
     * @param bizData
     * @param node
     * @return
     */
    List<CouponDetailResultDto> getInvalidCouponList(CouponListParam bizData, BaseNode node);


    /**
     * 查询券数据
     * @param bizData
     * @param node
     * @return
     */
    CouponDetailResultDto queryCouponData(CouponDetailParam bizData, BaseNode node);

    /**
     * 查询会员券详情
     * @param bizData
     * @param node
     * @return
     */
    CouponDetailRespDto queryCouponDetail(CouponDetailParam bizData, BaseNode node);

    /**
     * 查询转增的券详情
     * @param bizData
     * @param node
     * @return
     */
    TransferCouponDetailRespDto queryTransferCouponDetail(QueryTransferCouponDetailParam bizData, BaseNode node);


    /**
     * 查询券模版信息
     * @param templateId
     * @param node
     * @return
     */
    CouponTemplateRespDto queryCouponTemPlate(Integer templateId, CouponDetailParam bizData, BaseNode node);

    /**
     * 转增券
     *
     * @param bizData
     * @param node
     * @return
     */
    TransferCouponResultDto transferCoupon(TransferCouponParam bizData, BaseNode node);


    /**
     * 领取券
     *
     * @param bizData
     * @param node
     * @return
     */
    ReceiveCouponResultDto receiveCoupon(ReceiveCouponParam bizData, BaseNode node);


    /**
     * 查询会员开卡礼
     *
     * @param bizData
     * @param node
     * @return
     */
    ActivityGiftListResultDto getUserActivity(ActivityGiftListParam bizData, BaseNode node);

    /**
     * 绑定手机号
     *
     * @param bizData
     * @param node
     * @return
     */
    BindPhoneResultDto userBindPhone(BindPhoneParam bizData, BaseNode node);

    /**
     * 获取会员基础信息
     *
     * @param bizData
     * @param node
     * @return
     */
    MemberInfoResultDto getUserBaseInfo(MemberBaseParam bizData, BaseNode node);

    /**
     * 获取会员账户信息
     *
     * @param bizData
     * @param node
     * @return
     */
    MemberInfoResultDto getUserAccountInfo(MemberBaseParam bizData, BaseNode node);

    /**
     * 合并电子卡
     * @param bizData
     * @param node
     * @return
     */
    MergeEleCardResultDto mergeEleCard(MergeEleCardParam bizData, BaseNode node);
}
