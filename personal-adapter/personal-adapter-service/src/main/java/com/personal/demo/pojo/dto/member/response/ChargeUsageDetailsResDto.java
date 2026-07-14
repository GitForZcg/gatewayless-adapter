package com.personal.demo.pojo.dto.member.response;

import com.personal.demo.pojo.dto.member.response.charge.ChargeUsageItemDto;
import com.personal.demo.pojo.dto.member.response.charge.PageOptionsDto;
import lombok.Data;

import java.util.List;

/**
 * 会员储值使用明细业务数据
 * @author fxs
 * @date 2025/01/13
 */
@Data
public class ChargeUsageDetailsResDto {

    /** 明细列表 */
    private List<ChargeUsageItemDto> data;

    /** 分页信息 */
    private PageOptionsDto pageOptions;
}
