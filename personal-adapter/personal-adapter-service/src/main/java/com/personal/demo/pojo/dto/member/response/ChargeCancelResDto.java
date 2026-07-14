package com.personal.demo.pojo.dto.member.response;

import lombok.Data;

/**
 * 储值撤销业务数据
 * @author fxs
 * @date 2025/01/13
 */
@Data
public class ChargeCancelResDto  {

    /** 撤销结果 SUCCESS成功 FAIL失败 */
    private String result;

    /** 被撤销储值记录id */
    private String repealDealId;

    /** 储值撤销记录id */
    private String dealId;

    /** 是否可以撤销 1可以 0不能；仅onlyCheck=1时返回 */
    private Integer canCancelStatus;
}
