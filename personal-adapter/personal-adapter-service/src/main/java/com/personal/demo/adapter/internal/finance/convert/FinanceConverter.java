package com.personal.demo.adapter.internal.finance.convert;

import com.personal.demo.dto.finance.ImportVoucherEntryInfoDto;
import com.personal.demo.pojo.dto.DemoImportVoucherReqDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @date: 2025/9/11 15:09
 */
@Mapper
public interface FinanceConverter {
    
    FinanceConverter INSTANCE = Mappers.getMapper(FinanceConverter.class);
    
    List<DemoImportVoucherReqDto> convert(List<ImportVoucherEntryInfoDto> dto);
}
