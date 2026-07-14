package com.personal.demo.pojo.dto.member.request;

import com.personal.demo.pojo.base.BaseMemberPublicParam;
import com.personal.demo.pojo.base.BaseSM2PublicParam;
import lombok.Data;

/**
 * 用来结束没有参数的请求空类
 *
 * @Author: fxs
 * @Date: 2026/1/13 14:41
 */
@Data
public class EmptyReqDto implements BaseMemberPublicParam {

    /**
     *条款类型
     */
    private Integer type;
}
