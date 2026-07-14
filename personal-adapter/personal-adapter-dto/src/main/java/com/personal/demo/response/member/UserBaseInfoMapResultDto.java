package com.personal.demo.response.member;

import lombok.Data;

import java.util.Map;

/**
 * 会员基础信息范湖
 *
 * @Author: fxs
 * @Date: 2026/4/14 13:32
 */
@Data
public class UserBaseInfoMapResultDto {

    /**
     * 返回数据（key为用户ID）
     */
    private Map<String, MemberInfoResultDto> data;
}
