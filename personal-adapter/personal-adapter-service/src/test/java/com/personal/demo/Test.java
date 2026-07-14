package com.personal.demo;

import com.personal.demo.adapter.internal.order.OrderFlow;
import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.enu.internal.base.NodeFactory;
import com.personal.demo.enu.internal.node.OrderNode;
import com.personal.demo.request.base.BaseParams;
import com.personal.demo.request.order.PushOrderParams;
import com.personal.demo.serivce.impl.OrderServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author sulu
 * @date 2025年08月14日 4:27 PM
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
class Test {

    @Resource
    private OrderFlow orderFlow;

    @DisplayName("测试流程")
    @org.junit.jupiter.api.Test
    void testPush() {
        PushOrderParams params = new PushOrderParams();
        params.setOrderNo("DD253650447837589504");
        params.setMemberCode("MR233447378056380416");
        BaseParams base = new BaseParams<>();
        base.setBizData(params);
        BaseNode node = OrderNode.ORDER_PUSH;



        try {
            orderFlow.pushOrder(base, node);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    @DisplayName("测试pull")
    @org.junit.jupiter.api.Test
    void testPull() {
        PushOrderParams params = new PushOrderParams();
        params.setOrderNo("DD194289504719630336");
        params.setMemberCode("MR120684733359325184");
        BaseParams base = new BaseParams<>();
        base.setBizData(params);

        try {
            orderFlow.pushOrder(base, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }


}
