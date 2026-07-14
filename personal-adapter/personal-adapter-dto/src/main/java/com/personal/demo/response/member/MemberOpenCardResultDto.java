package com.personal.demo.response.member;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 开卡业务数据
 *
 * @author fxs
 * @date 2026/1/12
 */
@Data
public class MemberOpenCardResultDto {

    /**
     * 用户ID
     */
    private String uid;

    /**
     * 用户类型
     */
    private Integer type;

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
    private String membersCode;

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
    private String gender;

    /**
     * 微信昵称
     */

    private String wxNickname;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 口碑ID
     */
    private String koubeiId;

    /**
     * 注册时间
     */
    private String registered;

    /**
     * 最后注册时间
     */
    private String lastRegistered;

    /**
     * 卡状态
     */
    private Integer cardStatus;

    /**
     * 活动状态
     */
    private Integer activityStatus;

    /**
     * 公司ID
     */
    private Integer ccid;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 生日
     */
    private String birthDay;

    /**
     * 外卡导入-旧系统卡号
     */
    private String fromOtherCNo;

    /**
     * 外卡导入-旧系统储值余额（单位：分）
     */
    private String fromOtherCMoney;

    /**
     * 外卡导入-旧系统积分余额
     */
    private String fromOtherCCredit;
}
