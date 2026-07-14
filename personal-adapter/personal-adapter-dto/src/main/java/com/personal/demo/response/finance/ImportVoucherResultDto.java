package com.personal.demo.response.finance;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

/**
 * 导入凭证响应
 *
 * @date: 2025/9/15 11:53
 */
@Data
public class ImportVoucherResultDto implements Serializable {
    @Serial
    private static final long serialVersionUID = -2454606322894493332L;
    
    /**
     * 导入成功的凭证号
     */
    private List<String> successVoucherNumberList = new ArrayList<>();

    /**
     * 导入失败的凭证号
     */
    private List<String> failVoucherNumberList = new ArrayList<>();
}
