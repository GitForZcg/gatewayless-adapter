package com.personal.demo.pojo.dto.member.request;

import com.personal.demo.pojo.base.BaseMemberPublicParam;
import lombok.Data;

/**
 * 给适配样例的请求参数
 *
 * @Author: fxs
 * @Date: 2026/4/14 13:26
 */
@Data
public class MergeEleCardReqDto implements BaseMemberPublicParam {

    /**
     * 要保留会员卡的cardId
     */
    private String acCardId;

    /**
     * 保留的uid
     */
    private String acUid;

    /**
     * 需要保留的卡等级
     */
    private String acGrade;

    /**
     * 被合并的电子卡等级
     */
    private String grade;

    /**
     * 被合并的uid
     */
    private String uid;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 来源：1-h5，2-小程序
     */
    private Integer source;
}
