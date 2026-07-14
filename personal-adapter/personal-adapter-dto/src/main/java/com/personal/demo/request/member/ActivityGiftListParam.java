package com.personal.demo.request.member;

import lombok.Data;

/**
 * 开卡/生日/完善资料赠礼列表请求DTO
 * @author fxs
 * @date 2025/01/13
 */
@Data
public class ActivityGiftListParam {

    /**
     * 门店id；默认999999999
     *
     */
    private String shopId;

      /** 活动类型 open-开卡礼 birthday-生日赠礼 complete-完善资料赠礼 */
    private String type;
}
