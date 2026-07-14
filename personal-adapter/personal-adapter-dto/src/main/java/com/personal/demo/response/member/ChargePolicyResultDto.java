package com.personal.demo.response.member;

import lombok.Data;

/**
 * 储值政策业务数据
 * @author fxs
 * @date 2025/01/13
 */
@Data
public class ChargePolicyResultDto {

    /** 储值政策标题 */
    private String policyTitle;

    /** 储值政策内容 */
    private String policy;
}
