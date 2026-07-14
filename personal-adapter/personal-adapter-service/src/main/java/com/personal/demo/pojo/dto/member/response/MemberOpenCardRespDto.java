package com.personal.demo.pojo.dto.member.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 开卡业务数据
 *
 * @author fxs
 * @date 2026/1/12
 */
@Data
public class MemberOpenCardRespDto {

    /**
     * 用户ID
     */
    private String uid;

    /**
     * 用户类型
     */
    private Integer uType;

    /**
     * 品牌ID
     */
    private String bid;

    /**
     * 二维码ID
     */
    private String qrid;

    /**
     * 二维码源值
     */
    private String qridSrc;

    /**
     * 会员卡号
     */
    @JsonProperty("uNo")
    private String uNo;

    /**
     * 微信openid
     */
    private String openid;

    /**
     * 微信unionid
     */
    private String unionid;

    /**
     * 性别 0未知
     */
    @JsonProperty("uGender")
    private String uGender;

    /**
     * 微信昵称
     */
    @JsonProperty("uWXNickname")
    private String uWXNickname;

    /**
     * 头像
     */
    @JsonProperty("uAvatar")
    private String uAvatar;

    /**
     * 口碑ID
     */
    private String koubeiId;

    /**
     * 注册时间
     */
    private String uRegistered;

    /**
     * 最后注册时间
     */
    private String uLastRegistered;

    /**
     * 卡状态
     */
    @JsonProperty("uCardStatus")
    private Integer uCardStatus;

    /**
     * 活动状态
     */
    @JsonProperty("uActivityStatus")
    private Integer uActivityStatus;

    /**
     * 公司ID
     */
    private Integer ccid;

    /**
     * 姓名
     */
    @JsonProperty("uName")
    private String uName;

    /**
     * 手机号
     */
    @JsonProperty("uPhone")
    private String uPhone;

    /**
     * 生日
     */
    @JsonProperty("uBirthDay")
    private String uBirthDay;

    /**
     * 外卡导入-旧系统卡号
     */
    @JsonProperty("uFromOtherCNo")
    private String uFromOtherCNo;

    /**
     * 外卡导入-旧系统储值余额（单位：分）
     */
    @JsonProperty("uFromOtherCMoney")
    private String uFromOtherCMoney;

    /**
     * 外卡导入-旧系统积分余额
     */
    @JsonProperty("uFromOtherCCredit")
    private String uFromOtherCCredit;
}
