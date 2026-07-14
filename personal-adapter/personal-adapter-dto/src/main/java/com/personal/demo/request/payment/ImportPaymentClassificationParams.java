package com.personal.demo.request.payment;

import com.personal.demo.dto.payment.ImportPaymentClassificationDto;
import com.personal.demo.request.group.ValidationGroups;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class ImportPaymentClassificationParams {

    @NotEmpty(message = "付款单员工工号不能为空", groups = ValidationGroups.paymentGroup.class)
    private String payOrderEmployeeNo;

    @Valid
    @NotEmpty(message = "财务分类不能为空", groups = ValidationGroups.paymentGroup.class)
    private List<ImportPaymentClassificationDto> importPaymentClassifications;
}
