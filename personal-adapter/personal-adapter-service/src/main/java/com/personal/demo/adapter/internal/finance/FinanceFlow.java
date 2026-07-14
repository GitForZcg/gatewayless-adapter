package com.personal.demo.adapter.internal.finance;

import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.enu.internal.node.FinanceNode;
import com.personal.demo.request.base.BaseParams;
import com.personal.demo.request.finance.ImportVoucherInfoParams;
import com.personal.demo.rule.handler.AbstractFlowProcessor;
import com.personal.demo.serivce.IFinanceService;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 * 财务执行流程
 * @date: 2025/9/10 15:47 
 */
@SuppressWarnings("rawtypes")
@Component
public class FinanceFlow extends AbstractFlowProcessor<BaseParams> implements FinanceFlowProcessor {
    
    private final IFinanceService financeService;
    
    public FinanceFlow(IFinanceService financeService) {
        this.financeService = financeService;
    }

    @Override
    public String getProcessorType() {
        return "FINANCE";
    }

    @Override
    protected Map<BaseNode, BaseNodeFunction<BaseParams>> initNodeHandlers() {
        return Map.of(
                FinanceNode.FINANCE_IMPORT_VOUCHER, this::importVoucher
        );
    }

    @Override
    public Object importVoucher(BaseParams params, BaseNode node) throws Exception {
        return financeService.importVoucher((ImportVoucherInfoParams)params.getBizData(), node);
    }
}
