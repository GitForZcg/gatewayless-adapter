package com.personal.demo.response.member;

import lombok.Data;

/**
 * 会员基础信息返回
 *
 * @Author: fxs
 * @Date: 2026/1/12 16:07
 */

@Data
public class MemberInfoResultDto {


    /**
     * 用户卡号
     */
    private String cno;

    /**
     * 用户卡号
     */
    private String membersCode;

    /**
     * 微信用户openid
     */
    private String openid;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 用户手机号
     */
    private String phone;

    /**
     * 用户生日 yyyy-MM-dd
     */
//    @JsonFormat(pattern = "yyyy-MM-dd")
    private String birthday;

    /**
     * 用户ID
     */
    private String uid;

    /**
     * 会员的性别 男：M 女：F
     */
    private String gender;

    /**
     * 等级编号
     */
    private Integer grade;

    /**
     * 等级名称
     */
    private String gradeName;

    /**
     * 等级有效期
     */
    private String gradeEndTime;

    /**
     * 成长值
     */
    private Integer userGrowth;

    /**
     * 会员卡类型
     */
    private String cardType;

    /**
     * 储值余额（分）
     */
    private Integer balance;

    /**
     * 用户积分
     */
    private Integer credit;

    /**
     * 会员开卡来源
     */
    private String openSource;

    /**
     * 会员开卡来源的门店id
     */
    private Integer openSourceShopId;

    /**
     * 微信用户unionid
     */
    private String unionid;

    /**
     * 等级规则中顺序，0表示等级不在升降级规则中
     */
    private Integer orderNum;

    /**
     * 用户是否关注公众号 true：关注，false：未关注
     */
    private Boolean follow;

    /**
     * 用户优惠券数量
     */
    private Integer couponNum;

    /**
     * 来源渠道
     */
    private Integer userType;
}
