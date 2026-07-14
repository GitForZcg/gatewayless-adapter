package com.personal.demo.dto.payment;

import com.personal.demo.request.group.ValidationGroups;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author czs
 */
@Data
public class ImportPaymentClassificationDto {
    /**
     * 应付合计
     */
    @NotNull(message = "应付合计不能为空", groups = ValidationGroups.paymentGroup.class)
    private BigDecimal payableTotalAmount;
}
