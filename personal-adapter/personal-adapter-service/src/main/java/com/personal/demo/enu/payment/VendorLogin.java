package com.personal.demo.enu.payment;

/**
 * @author zhangjunyi
 * @version 1.0
 * @description:
 * @date 2025/10/9 10:26
 */
public enum VendorLogin {

    LOGIN("登陆", "/api/openapi/auth/login"),
    ;

    VendorLogin(String desc, String url) {
        this.desc = desc;
        this.url = url;
    }

    final public String desc;
    final public String url;
}
