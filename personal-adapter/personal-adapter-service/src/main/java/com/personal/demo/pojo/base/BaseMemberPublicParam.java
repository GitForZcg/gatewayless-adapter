package com.personal.demo.pojo.base;

public interface BaseMemberPublicParam {

    /**
     * 获取会员id
     */
    default String memberCode() {
        return "";
    }

    /**
     * 创建会员id
     */
    default String createMemberCode(String memberCode) {
        return memberCode;
    }
}
