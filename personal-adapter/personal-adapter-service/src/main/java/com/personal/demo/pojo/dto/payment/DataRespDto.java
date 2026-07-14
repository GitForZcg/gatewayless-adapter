package com.personal.demo.pojo.dto.payment;

import com.personal.demo.response.payment.ErrorDataResultDto;
import com.personal.demo.response.payment.SuccessDataResultDto;
import lombok.Data;

import java.util.List;

@Data
public class DataRespDto {
    /**
     * 财务分类费用失败信息
     */
    private List<ErrorDataResultDto> errorData;

    /**
     * 财务分类费用编码
     */
    private List<SuccessDataResultDto> successData;
}
