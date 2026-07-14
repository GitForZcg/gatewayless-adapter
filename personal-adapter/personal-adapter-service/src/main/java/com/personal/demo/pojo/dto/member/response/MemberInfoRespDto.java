package com.personal.demo.pojo.dto.member.response;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * 会员基础信息返回
 *
 * @Author: fxs
 * @Date: 2026/1/12 16:07
 */

@Data
public class MemberInfoRespDto {

    /**
     * 用户卡号
     */
    private String cno;

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
     * 用户性别 1:男 2:女
     */
    private Integer sex;

    /**
     * 等级编号
     */
    private String grade;

    /**
     * 等级名称
     */
    @SerializedName("grade_name")
    private String gradeName;

    /**
     * 等级有效期
     */
    @SerializedName("grade_end_time")
    private String gradeEndTime;

    /**
     *成长值
     */
    @SerializedName("user_growth")
    private Integer userGrowth;

    /**
     * 会员卡类型
     */
    @SerializedName("card_type")
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
    @SerializedName("coupon_num")
    private Integer couponNum;


    /**
     * 来源渠道
     */
    @SerializedName("user_type")
    private Integer userType;


    /**
     * 用户ID
     */
    private String uid;

}
