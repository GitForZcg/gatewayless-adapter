package com.personal.demo.response.member;

import lombok.Data;

/**
 * 会员条款响应
 *
 * @Author: fxs
 * @Date: 2026/1/13 14:23
 */
@Data
public class PolicyResultDto {

    /**
     * 会员规则内容
     */
    private String policy;
}
