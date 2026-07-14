package com.personal.demo.response.member;

import lombok.Data;

/**
 * 会员信息修改响应DTO
 *
 * @author fxs
 * @date 2026/1/12
 */

@Data
public class MemberUpdateResultDto {

    /**
     * 修改结果 SUCCESS:成功 FAIL:失败"
     */
    private String result;

}
