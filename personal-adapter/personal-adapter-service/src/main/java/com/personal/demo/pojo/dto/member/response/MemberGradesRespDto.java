package com.personal.demo.pojo.dto.member.response;

import com.personal.demo.pojo.dto.member.response.grade.GradeDto;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 会员等级列表业务数据
 *
 * @author fxs
 * @date 2026/1/12
 */
@Data
@Accessors(chain = true)
public class MemberGradesRespDto {

    /**
     * 等级列表
     */
    private List<GradeDto> grades;

}
