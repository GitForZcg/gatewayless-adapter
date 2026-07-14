package com.personal.demo.serivce.impl;

import com.personal.demo.adapter.internal.finance.convert.FinanceConverter;
import com.personal.demo.dto.finance.ImportVoucherEntryInfoDto;
import com.personal.demo.enu.internal.base.BaseNode;
import com.personal.demo.pojo.dto.DemoImportVoucherReqDto;
import com.personal.demo.pojo.dto.DemoImportVoucherRespDto;
import com.personal.demo.request.finance.ImportVoucherInfoParams;
import com.personal.demo.response.finance.ImportVoucherResultDto;
import com.personal.demo.serivce.AbstractFinanceMD5Service;
import com.personal.demo.serivce.IFinanceService;
import com.common.tools.GsonUtils;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @date: 2025/9/11 11:15
 */
@Service("financeService")
@Slf4j
public class FinanceServiceImpl implements IFinanceService {
    
    private final AbstractFinanceMD5Service md5Service;

    public FinanceServiceImpl(AbstractFinanceMD5Service md5Service) {
        this.md5Service = md5Service;
    }


    @Override
    public ImportVoucherResultDto importVoucher(ImportVoucherInfoParams bizData, BaseNode node) {
        List<ImportVoucherEntryInfoDto> voucherInfoList = bizData.getVoucherEntryInfoList();
        List<DemoImportVoucherReqDto> reqDtoList = FinanceConverter.INSTANCE.convert(voucherInfoList);
        Map<String, Object> responseMap = md5Service.execute(reqDtoList, node);
        log.info("import voucher response : {}", GsonUtils.beanToJson(responseMap));
        List<DemoImportVoucherRespDto> demoImportVoucherRespDtos = md5Service.checkImportVoucherExecuteResult(responseMap, DemoImportVoucherRespDto.class);
        ImportVoucherResultDto resultDto = new ImportVoucherResultDto();
        demoImportVoucherRespDtos.forEach(demoImportVoucherRespDto -> {
            if (demoImportVoucherRespDto.getStatus().equals("INSERT_SUCCESS")) {
                log.info("import voucher success : {}", GsonUtils.beanToJson(demoImportVoucherRespDto));
                resultDto.getSuccessVoucherNumberList().add(demoImportVoucherRespDto.getVoucherSourceNumber());
            } else {
                log.info("import voucher fail : {}", GsonUtils.beanToJson(demoImportVoucherRespDto));
                resultDto.getFailVoucherNumberList().add(demoImportVoucherRespDto.getVoucherSourceNumber());
            }
        });
        return resultDto;
    }
}
