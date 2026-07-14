package com.personal.demo.response.member.grade;

import lombok.Data;
import java.util.List;

/**
 * 特权信息DTO
 * @author fxs
 * @date 2025/01/13
 */
@Data
public class PrivilegeResultDto {

    /** 等级卡片列表 */
    private List<CardLevelResultDto> cardLevels;
}
