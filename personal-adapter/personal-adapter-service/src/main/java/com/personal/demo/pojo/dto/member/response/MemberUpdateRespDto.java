package com.personal.demo.pojo.dto.member.response;

import lombok.Data;

/**
 * 会员信息修改响应DTO
 *
 * @author fxs
 * @date 2026/1/12
 */

@Data
public class MemberUpdateRespDto {

    /**
     * 修改结果 SUCCESS:成功 FAIL:失败"
     */
    private String result;

}
