package com.personal.demo.request.trade;

import lombok.Data;

/**
 * @author Li QianQian
 * describe:
 */
@Data
public class EmployeeParam {

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
