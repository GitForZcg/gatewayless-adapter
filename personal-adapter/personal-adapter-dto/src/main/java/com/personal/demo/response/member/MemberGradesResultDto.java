package com.personal.demo.response.member;

import com.personal.demo.response.member.grade.GradeResultDto;
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
public class MemberGradesResultDto {

    /**
     * 等级列表
     */
    private List<GradeResultDto> grades;

}
