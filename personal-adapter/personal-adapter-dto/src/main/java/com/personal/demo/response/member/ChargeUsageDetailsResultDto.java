package com.personal.demo.response.member;

import com.personal.demo.response.member.charge.ChargeUsageItemResultDto;
import com.personal.demo.response.member.charge.PageOptionsResultDto;
import lombok.Data;

import java.util.List;

/**
 * 会员储值使用明细业务数据
 * @author fxs
 * @date 2025/01/13
 */
@Data
public class ChargeUsageDetailsResultDto {

    /** 明细列表 */
    private List<ChargeUsageItemResultDto> data;

    /** 分页信息 */
    private PageOptionsResultDto pageOptions;
}
