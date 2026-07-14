package com.personal.demo.enu.internal.node;

import com.personal.demo.enu.internal.base.BaseChannel;
import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.enu.internal.base.NodeFactory;

/**
 * 会员内部流程节点
 *
 * @Author: fxs
 * @Date: 2025/8/13 14:55
 */
public enum MemberNode implements BaseNode {

    MEMBER_GET_USER_ACTIVITY("查询会员开卡礼", "/brand/activity", BaseChannel.DEMO),
    MEMBER_USER_REGISTER("注册接口", "/user/openfullcard", BaseChannel.DEMO),
    MEMBER_USER_PHONE("绑定/修改手机号", "/user/bindphone", BaseChannel.DEMO),
    MEMBER_GET_USER_BASE_INFO("获取用户基本信息", "/user/accountBasicsInfo", BaseChannel.DEMO),
    MEMBER_GET_USER_BY_OPENID("openid查询用户", "/user/getinfobyopenid", BaseChannel.DEMO),
    MEMBER_GET_USER_ACCOUNT_INFO("获取用户的账户信息", "/user/accountSimple", BaseChannel.DEMO),
    MEMBER_GET_COUPON_LIST("查询会员全部券(分页)", "/user/couponlist", BaseChannel.DEMO),
    MEMBER_EDIT_USER_INFO("修改会员的基础信息", "/user/edit", BaseChannel.DEMO),
    MEMBER_GRADE_UPGRADE_TASK("卡等级查询接口/等级进度", "/user/gradeUpgradeTask", BaseChannel.DEMO),
    MEMBER_GET_GRADE_RIGHTS("会员等级权益", "/grade/rights", BaseChannel.DEMO),
    MEMBER_GET_POLICY("会员条款", "/user/getPolicy", BaseChannel.DEMO),
    MEMBER_BIND_PHYSICAL_CARD("实体卡绑定", "/rechargeCard/submit", BaseChannel.DEMO),
    MEMBER_GET_CHARGE_RULE("查询储值规则接口", "/charge/rule", BaseChannel.DEMO),
    MEMBER_CHARGE_PREVIEW("储值下单/储值预览", "/charge/preview", BaseChannel.DEMO),
    MEMBER_CHARGE_COMMIT("储值完成/储值提交", "/charge/commit", BaseChannel.DEMO),
    MEMBER_CHARGE_CANCEL("撤销储值", "/charge/cancel", BaseChannel.DEMO),
    MEMBER_CHARGE_USAGE_DETAILS("储值使用明细", "/charge/usageDetails", BaseChannel.DEMO),
    MEMBER_CHARGE_GET_POLICY("储值政策", "/charge/getPolicy", BaseChannel.DEMO),
    MEMBER_CHARGE_LIST("查询充值记录", "/charge/list", BaseChannel.DEMO),
    MEMBER_COUPON_TRANSFER("券转增", "/coupon/transfer", BaseChannel.DEMO),
    MEMBER_COUPON_RECEIVER("券领取", "/coupon/receive", BaseChannel.DEMO),
    MEMBER_USER_UNUSE_COUPON_LIST("查询未使用的券", "/user/unusecouponlist", BaseChannel.DEMO),
    MEMBER_USER_CURRENT_COUPON_LIST("查询会员可用的券", "/user/currentcouponlist", BaseChannel.DEMO),
    MEMBER_COUPON_TRANSFER_GET("领取券信息查询", "/coupon/transfer/get", BaseChannel.DEMO),
    MEMBER_USER_INVALID_COUPON_LIST("查询失效的券", "/user/getoverduecoupons", BaseChannel.DEMO),
    MEMBER_USER__COUPON_DATA("查询券信息", "/user/coupon/detail", BaseChannel.DEMO),
    MEMBER_USER_COUPON_TEMPLATE("查询券模版", "/coupon/detail", BaseChannel.DEMO),
    MEMBER_USER_COUPON_DETAIL("查询券详情", "/coupon/c2uinfo", BaseChannel.DEMO),
    MEMBER_USER_TRANSFER_COUPON_DETAIL("查询转增的券详情", "/coupon/transfer/get", BaseChannel.DEMO),
    MEMBER_MERGE_ELE_CARD("合并电子卡", "/user/mergeEleCard", BaseChannel.DEMO)


    ;

    public final String desc;

    public final String url;
    public final BaseChannel channel;

    MemberNode(String desc, String url, BaseChannel channel) {
        this.desc = desc;
        this.url = url;
        this.channel = channel;
    }

    @Override
    public String url() {
        return this.url;
    }

    @Override
    public String channel() {
        return String.valueOf(this.channel);
    }

    @Override
    public String nodeDesc() {
        return this.desc;
    }

    static {
        NodeFactory.register("MEMBER_", MemberNode::valueOf);
    }
}
