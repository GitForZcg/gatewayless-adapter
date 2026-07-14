package com.personal.demo.pojo.dto.member.response;

import com.personal.demo.pojo.base.BaseMemberPublicParam;
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
public class MemberOpenCardRespResultDto implements BaseMemberPublicParam {

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
    private Integer uGender;

    /**
     * 微信昵称
     */
    private String uWXNickname;

    /**
     * 头像
     */
    private String uAvatar;

    /**
     * 口碑ID
     */
    private String koubeiId;

    /**
     * 注册时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime uRegistered;

    /**
     * 最后注册时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime uLastRegistered;

    /**
     * 卡状态
     */
    private Integer uCardStatus;

    /**
     * 活动状态
     */
    private Integer uActivityStatus;

    /**
     * 公司ID
     */
    private Integer ccid;

    /**
     * 姓名
     */
    private String uName;

    /**
     * 手机号
     */
    private String uPhone;

    /**
     * 生日
     */
    private String uBirthDay;

    /**
     * 外卡导入-旧系统卡号
     */
    private String uFromOtherCNo;

    /**
     * 外卡导入-旧系统储值余额（单位：分）
     */
    private Integer uFromOtherCMoney;

    /**
     * 外卡导入-旧系统积分余额
     */
    private Integer uFromOtherCCredit;

    @Override
    public String memberCode() {
        return createMemberCode(this.uid);
    }
}
