package com.personal.demo.pojo.dto.trade.request;

import com.personal.demo.pojo.base.BaseSM2PublicParam;
import lombok.Data;

/**
 * @author Li QianQian
 * describe:
 */
@Data
public class EmployeeReqDto implements BaseSM2PublicParam {

    /**
     * 员工编码
     */
    private String empCode;

    /**
     * 员工姓名
     */
    private String empName;

    /**
     * 员工头像URL
     */
    private String empAvatar;

    /**
     * 员工角色
     */
    private Integer empRole;
}
