package com.personal.demo.request.member;

import com.personal.demo.request.group.ValidationGroups;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * openid参数
 * @Author: fxs
 * @Date: 2026/1/12 16:02
 */
@Data
public class OpenidParamParam {

    private int code = 200;
    private String msg = "SUCCESS";

    @NotEmpty(message = "openid不能为空", groups = ValidationGroups.memberGroup.class)
    private String openid;
}
