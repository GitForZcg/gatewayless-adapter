package com.personal.demo.adapter.internal.finance;

import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.request.base.BaseParams;
import com.personal.demo.rule.flow.UnifiedInternalServiceFlowProcessor;

/**
 * 财务流程实现 
 * @date: 2025/9/10 15:44 
 */
@SuppressWarnings("rawtypes")
public interface FinanceFlowProcessor extends UnifiedInternalServiceFlowProcessor<BaseParams> {

    /**
     * 导入凭证 
     * @param params 导入凭证参数
     * @param node 导入凭证节点
     * @return java.lang.Object 
     * @date: 2025/9/10 17:38 
     */
    Object importVoucher(BaseParams params, BaseNode node) throws Exception;

}
