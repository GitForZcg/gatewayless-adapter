package com.personal.demo.dto.payment;

import com.personal.demo.request.group.ValidationGroups;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class GoodsDetailFileDto {
    /**
     * 文件唯一标识
     */
    @NotEmpty(message = "文件唯一标识不能为空", groups = ValidationGroups.paymentGroup.class)
    private String fileKey;
    /**
     * 文件url
     */
    @NotEmpty(message = "文件url不能为空", groups = ValidationGroups.paymentGroup.class)
    private String url;

}
