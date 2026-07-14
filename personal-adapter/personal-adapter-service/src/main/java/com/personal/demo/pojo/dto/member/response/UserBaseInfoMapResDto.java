package com.personal.demo.pojo.dto.member.response;

import lombok.Data;

import java.util.Map;

/**
 * 适配样例会员返回
 *
 * @Author: fxs
 * @Date: 2026/4/14 13:38
 */
@Data
public class UserBaseInfoMapResDto {

    /**
     * 返回数据
     */
    private Map<String, MemberInfoRespDto> data;

}
