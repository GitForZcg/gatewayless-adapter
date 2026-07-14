package com.personal.demo.serivce;

import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.request.finance.ImportVoucherInfoParams;
import com.personal.demo.response.finance.ImportVoucherResultDto;

/**
 * 财务系统业务处理service
 *
 * @date: 2025/9/11 11:14
 */
public interface IFinanceService {
    /**
     * 导入凭证
     *
     * @param bizData
     * @param node
     * @return 导入成功或失败的凭证号
     */
    ImportVoucherResultDto importVoucher(ImportVoucherInfoParams bizData, BaseNode node);
}
