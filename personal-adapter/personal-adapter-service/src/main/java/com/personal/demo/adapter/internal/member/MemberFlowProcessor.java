package com.personal.demo.adapter.internal.member;

import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.pojo.dto.member.response.coupon.CouponListRespDto;
import com.personal.demo.request.base.BaseParams;
import com.personal.demo.response.member.*;
import com.personal.demo.rule.flow.UnifiedInternalServiceFlowProcessor;

/**
 * 会员服务
 *
 * @Author: fxs
 * @Date: 2026/1/12 15:34
 */
@SuppressWarnings("rawtypes")
public interface MemberFlowProcessor extends UnifiedInternalServiceFlowProcessor<BaseParams> {


    /**
     * 获取会员活动列表
     *
     * @param params
     * @param node
     * @return
     * @throws Exception
     */
    ActivityGiftListResultDto getUserActivity (BaseParams params, BaseNode node) throws Exception;
    /**
     * 会员注册
     *
     * @param params
     * @param node
     * @return
     * @throws Exception
     */
    MemberOpenCardResultDto userRegister(BaseParams params, BaseNode node) throws Exception;

    /**
     * 绑定手机号
     *
     * @param params
     * @param node
     * @return
     * @throws Exception
     */
    BindPhoneResultDto userBindPhone(BaseParams params, BaseNode node) throws Exception;

    /**
     * 通过openId查询会员信息
     *
     * @param params
     * @param node
     * @return
     * @throws Exception
     */
    MemberInfoResultDto getUserByOpenId(BaseParams params, BaseNode node) throws Exception;

    /**
     * 获取会员账户信息
     *
     * @param params
     * @param node
     * @return
     * @throws Exception
     */
    MemberInfoResultDto getUserAccountInfo(BaseParams params, BaseNode node) throws Exception;

    /**
     * 获取会员基本信息
     *
     * @param params
     * @param node
     * @return
     * @throws Exception
     */
    MemberInfoResultDto getUserBaseInfo(BaseParams params, BaseNode node) throws Exception;

    /**
     * 查询会员全部券(分页)
     *
     * @param params
     * @param node
     * @return
     * @throws Exception
     */
    CouponListRespDto getCouponList(BaseParams params, BaseNode node) throws Exception;


    /**
     * 修改会员信息
     *
     * @param bizData
     * @param node
     * @return
     */
    String editUserInfo(BaseParams bizData, BaseNode node) throws Exception;

    /**
     * 获取会员等级升级任务
     *
     * @param bizData
     * @param node
     * @return
     */
    GradeUpgradeTaskResultDto gradeUpgradeTask(BaseParams bizData, BaseNode node) throws Exception;

    /**
     * 获取会员等级权益
     *
     * @param bizData
     * @param node
     * @return
     */
    MemberGradesResultDto getGradeRights(BaseParams bizData, BaseNode node) throws Exception;

    /**
     * 获取会员条款
     *
     * @param node
     * @return
     */
    PolicyResultDto getPolicy(BaseParams bizData, BaseNode node) throws Exception;


    /**
     * 绑定实体卡
     *
     * @param bizData
     * @param node
     * @return
     */
    ValueCardInfoResultDto bindPhysicalCard(BaseParams bizData, BaseNode node) throws Exception;


    /**
     * 获取储值规则接口
     *
     * @param bizData
     * @param node
     * @return
     */
    ChargeRuleResultDto getChargeRule(BaseParams bizData, BaseNode node) throws Exception;


    /**
     * 储值下单/储值预览
     *
     * @param bizData
     * @param node
     * @return
     */
    ChargePreviewResultDto chargePreview(BaseParams bizData, BaseNode node) throws Exception;

    /**
     * 储值完成/储值提交
     *
     * @param bizData
     * @param node
     * @return
     */
    ChargeCommitResultDto chargeCommit(BaseParams bizData, BaseNode node) throws Exception;

    /**
     * 撤销储值
     *
     * @param bizData
     * @param node
     * @return
     */
    ChargeCancelResultDto chargeCancel(BaseParams bizData, BaseNode node) throws Exception;

    /**
     * 储值使用明细
     *
     * @param bizData
     * @param node
     * @return
     */
    ChargeUsageDetailsResultDto chargeUsageDetails(BaseParams bizData, BaseNode node) throws Exception;


    /**
     * 储值条款
     *
     * @param bizData
     * @param node
     * @return
     */
    ChargePolicyResultDto chargeGetPolicy(BaseParams bizData, BaseNode node) throws Exception;


    /**
     * 获取会员未使用的优惠券列表
     *
     * @param bizData
     * @param node
     * @return
     */
    Object getUnusedCouponList(BaseParams bizData, BaseNode node) throws Exception;


    /**
     * 查看失效优惠券列表
     *
     * @param bizData
     * @param node
     * @return
     */
    Object getInvalidCouponList(BaseParams bizData, BaseNode node) throws Exception;

    /**
     * 查看券信息
     *
     * @param bizData
     * @param node
     * @return
     */
    Object queryCouponData(BaseParams bizData, BaseNode node) throws Exception;

    /**
     * 转增券
     *
     * @param bizData
     * @param node
     * @return
     */
    Object transferCoupon(BaseParams bizData, BaseNode node) throws Exception;

    /**
     * 查询转增的券的信息
     * @param bizData
     * @param node
     * @return
     * @throws Exception
     */
    Object queryTransferCouponDetail(BaseParams bizData, BaseNode node) throws Exception;
    /**
     * 领取券
     *
     * @param bizData
     * @param node
     * @return
     */
    Object receiveCoupon(BaseParams bizData, BaseNode node) throws Exception;

    /**
     * 合并电子卡
     *
     * @param bizData
     * @param node
     * @return
     */
    Object mergeEleCard(BaseParams bizData, BaseNode node) throws Exception;



}
