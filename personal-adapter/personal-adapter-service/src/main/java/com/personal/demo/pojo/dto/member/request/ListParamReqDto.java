package com.personal.demo.pojo.dto.member.request;

import com.personal.demo.pojo.base.BaseMemberPublicParam;
import com.personal.demo.pojo.base.BaseSM2PublicParam;
import com.personal.demo.request.group.ValidationGroups;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;


/**
 * 获取列表请求参数
 *
 *
 * @Author: fxs
 * @Date: 2026/1/12 15:52
 */
@Data
public class ListParamReqDto implements BaseMemberPublicParam {

    /**
     * 会员编码(对应适配样例卡号)
     */
    @NotEmpty(message = "会员编码不能为空", groups = ValidationGroups.memberGroup.class)
    private String cno;

    /**
     * 页码
     */
    private Integer page;

}
