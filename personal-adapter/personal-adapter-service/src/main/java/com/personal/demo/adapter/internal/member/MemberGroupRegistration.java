package com.personal.demo.adapter.internal.member;

import com.personal.demo.enu.internal.node.MemberNode;
import com.personal.demo.request.group.ValidationGroups;
import com.personal.demo.rule.registry.AbstractValidationGroupRegistry;
import org.springframework.stereotype.Component;


/**
 * 会员验证组注册注册
 *
 * @Author: fxs
 * @Date: 2026/1/12 15:40
 */
@Component
public class MemberGroupRegistration extends AbstractValidationGroupRegistry<MemberNode> {

    public MemberGroupRegistration() {
        super(MemberNode.class, ValidationGroups.memberGroup.class);
    }

}
