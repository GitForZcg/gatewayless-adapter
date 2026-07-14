package com.personal.demo.request.finance;

import com.personal.demo.dto.finance.ImportVoucherEntryInfoDto;
import com.personal.demo.request.group.ValidationGroups;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * 导入的凭证信息参数
 *
 * @date: 2025/9/11 11:35
 */
@Data
public class ImportVoucherInfoParams implements Serializable {
    @Serial
    private static final long serialVersionUID = -5550900058358344745L;
    
    @Valid
    @NotEmpty(message = "凭证分录信息列表为空", groups = ValidationGroups.financeGroup.class)
    private List<ImportVoucherEntryInfoDto> voucherEntryInfoList;
}
