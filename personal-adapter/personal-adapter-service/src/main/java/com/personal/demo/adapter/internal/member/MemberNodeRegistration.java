package com.personal.demo.adapter.internal.member;

import com.personal.demo.enu.internal.node.MemberNode;
import com.personal.demo.rule.registry.AbstractInternalServiceNodeRegistry;
import org.springframework.stereotype.Component;

/**
 * 会员初始化
 *
 * @Author: fxs
 * @Date: 2026/1/12 15:40
 */
@Component
public class MemberNodeRegistration extends AbstractInternalServiceNodeRegistry<MemberNode> {

    public MemberNodeRegistration(MemberHandler handler) {
        super(MemberNode.class, handler);
    }
}
